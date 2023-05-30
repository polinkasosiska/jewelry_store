from datetime import datetime, timedelta
from typing import Optional
from fastapi import HTTPException, status, Depends
from fastapi.security import OAuth2PasswordBearer
from passlib.hash import bcrypt
from pydantic import ValidationError
from database import make_query
from models.auth import User, UserCreate, PrivateUser
from models.client import PrivateClient, ClientCreate, Client
from models.tables import User
from settings import settings
from jose import jwt, JWTError
from models import tables

oauth2_scheme = OAuth2PasswordBearer(tokenUrl='/auth/sign-in')


def get_current_user(token: str = Depends(oauth2_scheme)) -> User:
    return AuthService.validate_token(token)


class AuthService:
    @classmethod
    def verify_password(cls, plain_password: str, hashed_password: str) -> bool:
        return bcrypt.verify(plain_password, hashed_password)

    @classmethod
    def hash_password(cls, password: str) -> str:
        return bcrypt.hash(password)

    @classmethod
    def validate_token(cls, token: str) -> User:
        exception = HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail='Could not validate credentials',
            headers={
                'WWW-Authenticate': 'Bearer'
            }
        )
        try:
            payload = jwt.decode(token, settings.jwt_sercret, algorithms=[settings.jwt_algorithm])
        except JWTError:
            raise exception from None

        user_data = payload.get('user')

        try:
            user = User.parse_obj(user_data)
        except ValidationError:
            raise exception from None
        return user

    @classmethod
    def create_token(cls, user: tables.User) -> str:
        user_data = User.from_orm(user)
        now = datetime.utcnow()
        payload = {
            'iat': now,
            'nbf': now,
            'exp': now + timedelta(seconds=settings.jwt_expiration),
            'sub': str(user_data.id),
            'user': user_data.dict()
        }
        token = jwt.encode(payload, settings.jwt_sercret, algorithm=settings.jwt_algorithm)
        return token

    def get_user_by_email(self, email: str) -> Optional[User]:
        return make_query("SELECT * FROM users where email=%s LIMIT 1", tables.User, (email, ))

    def reg(self, user_data: UserCreate, is_moderator: bool):
        if self.get_user_by_email(user_data.email):
            raise HTTPException(status_code=418, detail="User with this email already exists")
        password_hash = self.hash_password(user_data.password)
        query = "INSERT INTO users (email, full_name, is_moderator, password_hash) " \
                "VALUES ("+f'"{user_data.email}"'+","\
                + f'"{user_data.full_name}"'+"," + f"{is_moderator}"+"," + f'"{password_hash}"'
        query += ");"
        make_query(query)

    def auth(self, email: str, password: str):
        exception = HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail='Incorrect username or password',
            headers={
                'WWW-Authenticate': 'Bearer'
            }
        )
        user = self.get_user_by_email(email)
        if not user:
            raise exception
        if not self.verify_password(password, user.password_hash):
            raise exception
        token = self.create_token(user)
        if user.is_moderator:
            return PrivateUser(email=user.email,
                               full_name=user.full_name,
                               is_moderator=user.is_moderator,
                               id=user.id,
                               access_token=token)
        else:
            client = self.get_client(user.id)
            return PrivateClient(
                email=client.email,
                full_name=client.full_name,
                is_moderator=client.is_moderator,
                user_id=client.user_id,
                address=client.address,
                phone=client.phone,
                access_token=token
            )

    def reg_client(self, user_id: int, client_data: ClientCreate):
        query = "INSERT INTO clients (user_id, address, phone) " \
                "VALUES (" + f'"{user_id}"' + "," \
                + f'"{client_data.address}"' + "," + f'"{client_data.phone}"'
        query += ");"
        make_query(query)

    def get_client(self, user_id: int) -> Optional[Client]:
        client = make_query("SELECT user_id, email, full_name, is_moderator, address, phone "
                         "FROM users INNER JOIN clients c on users.id = c.user_id"
                         + f" WHERE c.user_id = {user_id}", Client)
        return client
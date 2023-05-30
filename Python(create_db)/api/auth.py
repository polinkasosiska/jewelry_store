from fastapi import APIRouter, Depends
from models.auth import UserCreate, Token, User, PrivateUser
from fastapi.security import OAuth2PasswordRequestForm

from models.client import PrivateClient, ClientCreate, Client
from services.auth import AuthService, get_current_user

router = APIRouter(prefix='/auth')


@router.post('/sign-up', response_model=PrivateClient)
def sign_up(user_data: UserCreate, client_data: ClientCreate, service: AuthService = Depends()):
    service.reg(user_data, False)
    created_user = service.get_user_by_email(user_data.email)
    token = service.create_token(created_user)
    service.reg_client(created_user.id, client_data)
    client = service.get_client(created_user.id)
    return PrivateClient(email=client.email,
                         full_name=client.full_name,
                         is_moderator=client.is_moderator,
                         user_id=client.user_id,
                         address=client.address,
                         phone=client.phone,
                         access_token=token)


@router.post('/sign-up-moderator', response_model=PrivateUser)
def sign_up_moderator(user_data: UserCreate, service: AuthService = Depends()):
    service.reg(user_data, True)
    created_user = service.get_user_by_email(user_data.email)
    token = service.create_token(created_user)
    return PrivateUser(email=created_user.email,
                       full_name=created_user.full_name,
                       is_moderator=created_user.is_moderator,
                       id=created_user.id,
                       access_token=token)


@router.post('/sign-in', response_model=PrivateClient | PrivateUser)
def sign_in(form_data: OAuth2PasswordRequestForm = Depends(), service: AuthService = Depends()):
    return service.auth(form_data.username, form_data.password)


@router.get('/user', response_model=Client | User)
def get_user(user: User = Depends(get_current_user), service: AuthService = Depends()):
    if user.is_moderator:
        return user
    else:
        return service.get_client(user.id)

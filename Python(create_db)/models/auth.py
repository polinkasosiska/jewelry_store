from pydantic import BaseModel

class BaseUser(BaseModel):
    email: str
    full_name: str


class UserCreate(BaseUser):
    password: str


class User(BaseUser):
    id: int
    is_moderator: bool

    class Config:
        orm_mode = True


class Token(BaseModel):
    access_token: str
    token_type: str = 'bearer'


class PrivateUser(User):
    access_token: str
    token_type: str = 'bearer'

    class Config:
        orm_mode = True
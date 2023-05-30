from pydantic import BaseModel

from models.auth import BaseUser


class ClientCreate(BaseModel):
    address: str
    phone: str


class Client(BaseUser):
    user_id: int
    is_moderator: bool
    address: str
    phone: str

    class Config:
        orm_mode = True


class PrivateClient(Client):
    access_token: str
    token_type: str = 'bearer'

    class Config:
        orm_mode = True
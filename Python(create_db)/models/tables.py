from sqlite3 import Date

from pydantic import BaseModel
from enums.order_status import OrderStatus
from models.product import BaseProduct


class User(BaseModel):
    id: int
    email: str
    full_name: str
    is_moderator: bool
    password_hash: str

    class Config:
        orm_mode = True


class Client(BaseModel):
    user_id: int
    address: str
    phone: str

    class Config:
        orm_mode = True


class ProductType(BaseModel):
    id: int
    name: str

    class Config:
        orm_mode = True


class Product(BaseProduct):
    id: int

    class Config:
        orm_mode = True


class WishList(BaseModel):
    user_id: int
    product_id: int

    class Config:
        orm_mode = True


class Cart(WishList):
    count: int

    class Config:
        orm_mode = True


class Orders(BaseModel):
    id: int
    user_id: int
    order_date: Date
    order_price: int
    status: OrderStatus

    class Config:
        orm_mode = True

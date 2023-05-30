from pydantic import BaseModel
from sqlite3 import Date


class BaseProduct(BaseModel):
    product_type_id: int
    name: str
    price: int
    manufacture_date: Date
    material: str
    color: str
    country: str
    weight: int

    class Config:
        orm_mode = True


class ProductInOrder(BaseProduct):
    id: int
    count: int

    class Config:
        orm_mode = True


class ProductWithWish(BaseProduct):
    id: int
    is_wished: bool = False

    class Config:
        orm_mode = True


class PriceAndCount(BaseModel):
    price: int
    count: int

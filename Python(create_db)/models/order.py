from typing import List

from pydantic import BaseModel
from sqlite3 import Date

from enums.order_status import OrderStatus
from models.product import ProductInOrder
from models.tables import Product


class Order(BaseModel):
    id: int
    user_id: int
    user_email: str
    user_name: str
    order_date: Date
    order_price: int
    status: OrderStatus
    products: List[ProductInOrder] = []

    class Config:
        orm_mode = True
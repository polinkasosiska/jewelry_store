from typing import List

from pydantic import BaseModel
from sqlite3 import Date
from models import tables
from models.product import ProductWithWish


class ProductsWithType(BaseModel):
    product_type_id: int
    product_type: str
    products: List[ProductWithWish] = []

    class Config:
        orm_mode = True

from typing import List, Optional

from fastapi import HTTPException

from database import get_list, insert_and_get_id, make_query, make_delete_query
from models import tables
from models.product import ProductInOrder


class CartService:

    def get(self, user_id: int, product_id: int) -> Optional[tables.Cart]:
        return make_query("SELECT * FROM cart WHERE user_id=%s "
                          "AND product_id=%s LIMIT 1", tables.Cart, (user_id, product_id,))

    def get_list(self, user_id: int) -> List[ProductInOrder]:
        return get_list("SELECT *, count FROM products"
                        " INNER JOIN cart c on products.id = c.product_id"
                        " WHERE c.user_id = %s", ProductInOrder, user_id)

    def add(self, user_id: int, product_id: int, count: int) -> int:
        if self.get(user_id, product_id):
            raise HTTPException(status_code=418, detail="This product has already been added")
        query = "INSERT INTO cart (user_id, product_id, count) " \
                "VALUES (%s, %s, %s)"
        return insert_and_get_id(query, (user_id, product_id, count))

    def delete(self, user_id: int, product_id: int) -> tables.WishList:  # WishList it's not mistake
        make_delete_query("DELETE FROM cart WHERE user_id=%s AND product_id=%s",
                          (user_id, product_id,))
        return tables.WishList(user_id=user_id, product_id=product_id)

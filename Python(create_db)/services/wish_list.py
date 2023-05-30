from typing import List, Optional

from fastapi import HTTPException

from database import get_list, insert_and_get_id, make_query, make_delete_query
from models import tables


class WishListService:

    def get(self, user_id: int, product_id: int) -> Optional[tables.WishList]:
        return make_query("SELECT * FROM wish_list WHERE user_id=%s "
                          "AND product_id=%s LIMIT 1", tables.WishList, (user_id, product_id,))

    def get_list(self, user_id: int) -> List[tables.Product]:
        return get_list("SELECT * FROM products"
                        " INNER JOIN wish_list w on products.id = w.product_id"
                        " WHERE w.user_id = %s", tables.Product, user_id)

    def add(self, user_id: int, product_id: int) -> int:
        if self.get(user_id, product_id):
            raise HTTPException(status_code=418, detail="This product has already been added")
        query = "INSERT INTO wish_list (user_id, product_id) " \
                "VALUES (%s, %s)"
        return insert_and_get_id(query, (user_id, product_id))

    def delete(self, user_id: int, product_id: int) -> tables.WishList:
        make_delete_query("DELETE FROM wish_list WHERE user_id=%s AND product_id=%s",
                          (user_id, product_id,))
        return tables.WishList(user_id=user_id, product_id=product_id)

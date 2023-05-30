from typing import List

from database import get_list, insert_and_get_id, make_delete_query
from models import tables


class ProductTypeService:

    def get_product_types(self,) -> List[tables.ProductType]:
        return get_list("SELECT * FROM product_type", tables.ProductType)

    def add_product_type(self, name) -> int:
        query = "INSERT INTO product_type (name) " \
                "VALUES (" + f'"{name}"'
        query += ");"
        return insert_and_get_id(query)

    def delete(self, product_type_id: int) -> int:
        make_delete_query("DELETE FROM product_type WHERE id = %s", (product_type_id,))
        return product_type_id

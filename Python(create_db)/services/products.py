from typing import List
from database import get_list, insert_and_get_id, make_query, make_delete_query
from models import tables
from models.product import BaseProduct, ProductWithWish
from models.products_with_type import ProductsWithType


class ProductService:

    def get(self, product_id: int) -> tables.Product:
        return make_query("SELECT * FROM products WHERE id=%s LIMIT 1", tables.Product, (product_id,))

    def get_product_types(self,) -> List[ProductsWithType]:
        return get_list("SELECT id as product_type_id, name as product_type"
                        " FROM product_type", ProductsWithType)

    def _get_products(self, product_type_id) -> List[ProductWithWish]:
        return get_list("SELECT * FROM products WHERE product_type_id=%s", ProductWithWish, (product_type_id,))

    def get_products(self,) -> List[ProductsWithType]:
        product_types = self.get_product_types()
        products_with_type = []
        for pt in product_types:
            pt.products = self._get_products(pt.product_type_id)
            products_with_type.append(pt)
        return products_with_type

    def get_wish_list(self, user_id: int) -> List[tables.WishList]:
        return get_list("SELECT * FROM wish_list WHERE user_id=%s ", tables.WishList, (user_id,))

    def get_products_client(self, user_id: int) -> List[ProductsWithType]:
        product_types = self.get_product_types()
        products_with_type = []
        for pt in product_types:
            pt.products = self._get_products(pt.product_type_id)
            products_with_type.append(pt)
        wish_list = self.get_wish_list(user_id)
        for product_with_type in products_with_type:
            for product in product_with_type.products:
                if tables.WishList(user_id=user_id, product_id=product.id) in wish_list:
                    product.is_wished = True
        return products_with_type

    def add_product(self, product: BaseProduct) -> int:
        query = "INSERT INTO products (product_type_id, name, price, manufacture_date," \
                " material, color, country, weight) " \
                "VALUES (%s, %s, %s, %s, %s, %s, %s, %s)" # cur date неправильно
        return insert_and_get_id(query, (
            product.product_type_id, product.name, product.price, product.manufacture_date,
            product.material, product.color,
            product.country, product.weight
        ))

    def delete(self, product_id: int) -> int:
        make_delete_query("DELETE FROM products WHERE id = %s", (product_id,))
        return product_id

    def update(self, product_id: int, product: BaseProduct) -> tables.Product:
        make_query("UPDATE products SET product_type_id=%s, name=%s, price=%s, manufacture_date=%s,"
                   " material=%s, color=%s, country=%s, weight=%s"
                   " WHERE id = %s", None, (product.product_type_id, product.name, product.price,
                                            product.manufacture_date, product.material, product.color,
                                            product.country, product.weight, product_id,))
        return self.get(product_id)

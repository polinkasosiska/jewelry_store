from typing import List, Optional
from database import get_list, insert_and_get_id, make_query
from enums.order_status import OrderStatus
from models import tables
from models.order import Order
from models.product import PriceAndCount, ProductInOrder


class OrdersService:

    def get(self, order_id: int) -> Order:
        order = make_query("SELECT * FROM order_view WHERE id=%s LIMIT 1", Order, order_id)
        order.products = self.get_products(order.id)
        return order

    def get_list(self, user_id: int) -> List[Order]:
        orders_dict_arr = get_list("SELECT * FROM order_view WHERE user_id=%s", Order, user_id)
        orders = []
        for order in orders_dict_arr:
            order.products = self.get_products(order.id)
            orders.append(order)
        return orders

    def get_all_orders(self, ) -> List[Order]:
        orders_dict_arr = get_list("SELECT * FROM order_view", Order)
        orders = []
        for order in orders_dict_arr:
            order.products = self.get_products(order.id)
            orders.append(order)
        return orders

    def get_products(self, order_id: int) -> List[ProductInOrder]:
        return get_list("SELECT *, count FROM products"
                         " INNER JOIN ordered_products op on products.id = op.product_id"
                         " WHERE op.order_id = %s", ProductInOrder, order_id)

    def get_cart_list(self, user_id: int) -> List[tables.Cart]:
        return get_list("SELECT * FROM cart WHERE user_id=%s", tables.Cart, user_id)

    def get_cart_products(self, product_ids: List[int]) -> List[tables.Product]:
        l = tuple(product_ids)
        params = {'l': l}
        return get_list("SELECT * FROM products WHERE id IN %(l)s ", tables.Product, params)

    def get_cart_products_after_order(self, user_id: int) -> List[ProductInOrder]:
        return get_list("SELECT *, count FROM products"
                        " INNER JOIN cart c on products.id = c.product_id"
                        " WHERE c.user_id = %s", ProductInOrder, user_id)

    def add(self, user_id: int) -> List[ProductInOrder]:
        price_and_count_list = get_list("SELECT price, count "
                                        "FROM products INNER JOIN cart c on products.id = c.product_id"
                                        " WHERE c.user_id = %s", PriceAndCount, user_id)
        order_price = 0
        for cart_product in price_and_count_list:
            order_price += cart_product.price * cart_product.count
        query = "INSERT INTO orders (user_id, order_date, order_price, status) " \
                "VALUES (%s, CURDATE(), %s, %s)"
        order_id = insert_and_get_id(query, (user_id, order_price, OrderStatus.IN_PROGRESS.value))

        for cart_product in self.get_cart_list(user_id):
            query = "INSERT INTO ordered_products (order_id, product_id, count) " \
                    "VALUES (%s, %s, %s)"
            insert_and_get_id(query, (order_id, cart_product.product_id, cart_product.count))
            # TRIGGER AFTER INSERT DELETE FROM CART
        return self.get_cart_products_after_order(user_id)

    def update_status(self, order_id: int, status: OrderStatus) -> List[Order]:
        make_query("UPDATE orders SET status = %s WHERE id = %s", None, (status.value, order_id,))
        return self.get_all_orders()

    def cancel(self, order_id: int, user_id: int) -> List[Order]:
        make_query("UPDATE orders SET status = %s WHERE id = %s", None, (OrderStatus.CANCELLED.value, order_id,))
        return self.get_list(user_id)

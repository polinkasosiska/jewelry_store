from database import make_query


def main():
    make_query(
        """\
        CREATE TABLE users (
        id int not null AUTO_INCREMENT,
        email varchar(50),
        full_name varchar(50),
        is_moderator boolean,
        password_hash text,
        primary key (id),
        unique (email)
        )
        """
    )

    make_query(
        """\
        CREATE TABLE clients (
        user_id  INTEGER NOT NULL,
        address  VARCHAR(20),
        phone  VARCHAR(20),
        CONSTRAINT fk1 foreign key (user_id) references users(id) on delete cascade,
        primary key (user_id)
        )
        """
    )

    make_query(
        """\
        CREATE TABLE product_type (
        id int not null AUTO_INCREMENT,
        name varchar(20),
        primary key (id)
        )
        """
    )

    make_query(
        """\
        CREATE TABLE products (
        id  INTEGER NOT NULL AUTO_INCREMENT,
        product_type_id  INTEGER NOT NULL,
        name  VARCHAR(20),
        price INTEGER,
        manufacture_date  DATE,
        material  VARCHAR(20),
        color  VARCHAR(20),
        country  VARCHAR(20),
        weight  INTEGER,
        CONSTRAINT fk2 foreign key (product_type_id) references product_type(id) on delete cascade,
        primary key (id)
        )
        """
    )

    make_query(
        """\
        CREATE TABLE wish_list (
        user_id  INTEGER NOT NULL,
        product_id  INTEGER NOT NULL,
        CONSTRAINT fk3 foreign key (user_id) references users(id) on delete cascade,
        CONSTRAINT fk4 foreign key (product_id) references products(id) on delete cascade,
        primary key (user_id, product_id)
        )
        """
    )

    make_query(
        """\
        CREATE TABLE cart (
        user_id  INTEGER NOT NULL,
        product_id  INTEGER NOT NULL,
        count  INTEGER,
        CONSTRAINT fk5 foreign key (user_id) references users(id) on delete cascade,
        CONSTRAINT fk6 foreign key (product_id) references products(id) on delete cascade,
        primary key (user_id, product_id)
        )
        """
    )

    make_query(
        """\
        CREATE TABLE orders (
        id  INTEGER NOT NULL AUTO_INCREMENT,
        user_id  INTEGER NOT NULL,
        order_date  DATE,
        order_price INTEGER,
        status VARCHAR(20),
        CONSTRAINT fk7 foreign key (user_id) references users(id) on delete cascade,
        primary key (id)
        )
        """
    )

    make_query(
        """\
        CREATE TABLE ordered_products (
        order_id  INTEGER NOT NULL,
        product_id  INTEGER NOT NULL,
        count  INTEGER,
        CONSTRAINT fk8 foreign key (order_id) references orders(id) on delete cascade,
        CONSTRAINT fk9 foreign key (product_id) references products(id) on delete cascade,
        primary key (order_id, product_id)
        )
        """
    )

    make_query(
        """\
        CREATE TRIGGER on_product_ordered AFTER INSERT on ordered_products
        FOR EACH ROW
        BEGIN
            DECLARE userId INT;
            SET userId = (SELECT user_id FROM orders WHERE id=NEW.order_id);
            DELETE FROM cart WHERE cart.product_id = NEW.product_id AND cart.user_id = userId;
        END;
        """
    )

    make_query(
        """\
        CREATE VIEW order_view
        AS SELECT orders.id, order_date, order_price, status, user_id,
        email as user_email, full_name as user_name
        FROM orders, users
        WHERE orders.user_id = users.id;
        """
    )


if __name__ == "__main__":
    main()

from contextlib import closing
import pymysql.cursors
from settings import settings

con = pymysql.connect(
    host=settings.db_host, port=settings.db_port,
    user=settings.db_user, password=settings.db_password
)

def create_db():
    with closing(con.cursor()) as cursor:
        cursor.execute(f"CREATE DATABASE {settings.db_name}")
        con.commit()


def main():
    create_db()


if __name__ == "__main__":
    main()

from contextlib import closing
from typing import TypeVar, Type
from pymysql.err import InterfaceError

from settings import settings
import pymysql.cursors

T = TypeVar("T")

con = pymysql.connect(
    host=settings.db_host, port=settings.db_port,
    user=settings.db_user, password=settings.db_password, database=settings.db_name
)


def _get_item(cursor, row, data_class: Type[T] = None):
    data = dict((column[0], row[index]) for index, column in enumerate(cursor.description))
    if len(data.keys()) == 1:
        item = data[next(iter(data))]
    else:
        item = data_class(**data)
    return item


def insert_and_get_id(sql, args=None):
    with closing(con.cursor()) as cursor:
        cursor.execute(sql, args)
        con.commit()
        return cursor.lastrowid


def make_delete_query(sql, args=None):
    with closing(con.cursor()) as cursor:
        cursor.execute(sql, args)
        con.commit()


def make_query(sql, data_class: Type[T] = None, args=None):
    #con.execute("PRAGMA foreign_keys = ON")
    with closing(con.cursor()) as cursor:
        cursor.execute(sql, args)
        if con.open:
            con.commit()
        for row in cursor:
            item = _get_item(cursor, row, data_class)
            if item is None:
                return None
            return item
        return None


def get_list(sql, data_class: Type[T], args=None):
    with closing(con.cursor()) as cursor:
        cursor.execute(sql, args)
        data_list = []
        for row in cursor:
            item = _get_item(cursor, row, data_class)
            data_list.append(item)
        return data_list


def reconnect():
    global con
    con = pymysql.connect(
        host=settings.db_host, port=settings.db_port,
        user=settings.db_user, password=settings.db_password, database=settings.db_name
    )
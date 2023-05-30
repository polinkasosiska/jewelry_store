from typing import List

from fastapi import APIRouter, Depends, HTTPException
from starlette import status
import constants
from models import tables
from models.auth import User
from models.product import BaseProduct
from models.products_with_type import ProductsWithType
from services.auth import get_current_user
from services.products import ProductService

router = APIRouter(prefix='/product')


@router.post('/add', response_model=List[ProductsWithType])
def add_product(
        product: BaseProduct, user: User = Depends(get_current_user), service: ProductService = Depends()
):
    if not user.is_moderator:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=constants.ACCESS_ERROR)
    service.add_product(product)
    return service.get_products()


@router.get('/get-list', response_model=List[ProductsWithType])
def get_products(service: ProductService = Depends()):
    return service.get_products()


@router.get('/get-products-client', response_model=List[ProductsWithType])
def get_products_client(user: User = Depends(get_current_user), service: ProductService = Depends()):
    return service.get_products_client(user.id)


@router.get('/{product_id}', response_model=tables.Product)
def get_product(product_id: int, service: ProductService = Depends()):
    return service.get(product_id)


@router.delete('/{product_id}', response_model=int)
def delete(product_id: int, user: User = Depends(get_current_user),
           service: ProductService = Depends()):
    if not user.is_moderator:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=constants.ACCESS_ERROR)
    return service.delete(product_id)


@router.put('/{product_id}', response_model=tables.Product)
def update_product(
        product_id: int,
        product: BaseProduct,
        service: ProductService = Depends(),
        user: User = Depends(get_current_user)):
    if not user.is_moderator:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=constants.ACCESS_ERROR)
    return service.update(product_id, product)

from typing import List
from fastapi import APIRouter, Depends
from models import tables
from models.auth import User
from models.products_with_type import ProductsWithType
from services.auth import get_current_user
from services.products import ProductService
from services.wish_list import WishListService

router = APIRouter(prefix='/wish-list')


@router.post('/add', response_model=List[ProductsWithType])
def add_wish_item(
        product_id: int,
        user: User = Depends(get_current_user),
        service: WishListService = Depends(),
        product_service: ProductService = Depends()
):
    service.add(user_id=user.id, product_id=product_id)
    return product_service.get_products_client(user.id)


@router.get('/get-list', response_model=List[tables.Product])
def get_wish_list(user: User = Depends(get_current_user), service: WishListService = Depends()):
    return service.get_list(user.id)


@router.delete('/delete', response_model=tables.WishList)
def delete(product_id: int, user: User = Depends(get_current_user),
           service: WishListService = Depends()):
    return service.delete(user_id=user.id, product_id=product_id)


@router.delete('/delete-in-products', response_model=List[ProductsWithType])
def delete_in_products(product_id: int, user: User = Depends(get_current_user),
                       service: WishListService = Depends(),
                       product_service: ProductService = Depends()):
    service.delete(user_id=user.id, product_id=product_id)
    return product_service.get_products_client(user.id)

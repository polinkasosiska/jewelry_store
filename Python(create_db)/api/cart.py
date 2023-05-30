from typing import List
from fastapi import APIRouter, Depends
from models import tables
from models.auth import User
from models.product import ProductInOrder
from services.auth import get_current_user
from services.cart import CartService

router = APIRouter(prefix='/cart')


@router.post('/add', response_model=List[ProductInOrder])
def add_cart_item(
        product_id: int,
        count: int,
        user: User = Depends(get_current_user),
        service: CartService = Depends()
):
    service.add(user_id=user.id, product_id=product_id, count=count)
    return service.get_list(user.id)


@router.get('/get-list', response_model=List[ProductInOrder])
def get_cart_list(user: User = Depends(get_current_user), service: CartService = Depends()):
    return service.get_list(user.id)


@router.delete('/delete', response_model=tables.WishList)  # WishList it's not mistake
def delete(product_id: int, user: User = Depends(get_current_user),
           service: CartService = Depends()):
    return service.delete(user_id=user.id, product_id=product_id)

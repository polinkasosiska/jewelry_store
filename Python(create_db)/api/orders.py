from typing import List
from fastapi import APIRouter, Depends, HTTPException
from starlette import status
import constants
from enums.order_status import OrderStatus
from models import tables
from models.auth import User
from models.order import Order
from models.product import ProductInOrder
from services.auth import get_current_user
from services.orders import OrdersService

router = APIRouter(prefix='/order')


@router.post('/add', response_model=List[ProductInOrder])
def add_order(
        user: User = Depends(get_current_user),
        service: OrdersService = Depends()
):
    return service.add(user_id=user.id)


@router.get('/get-list', response_model=List[Order])
def get_orders(user: User = Depends(get_current_user), service: OrdersService = Depends()):
    return service.get_list(user.id)


@router.get('/all-orders', response_model=List[Order])
def get_all_orders(user: User = Depends(get_current_user), service: OrdersService = Depends()):
    if not user.is_moderator:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=constants.ACCESS_ERROR)
    return service.get_all_orders()


@router.get('/{order_id}', response_model=Order)
def get_order(order_id: int, user: User = Depends(get_current_user), service: OrdersService = Depends()):
    order = service.get(order_id)
    if (order.user_id != user.id) and (not user.is_moderator):
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=constants.ACCESS_ERROR)
    return order


@router.put('/{order_id}', response_model=List[Order])
def update_status(
        order_id: int,
        order_status: OrderStatus,
        service: OrdersService = Depends(),
        user: User = Depends(get_current_user)):
    if not user.is_moderator:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=constants.ACCESS_ERROR)
    return service.update_status(order_id, order_status)


@router.put('/cancel/', response_model=List[Order])
def cancel(
        order_id: int,
        service: OrdersService = Depends(),
        user: User = Depends(get_current_user)):
    order = service.get(order_id)
    if order.user_id != user.id:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=constants.ACCESS_ERROR)
    return service.cancel(order_id, user.id)

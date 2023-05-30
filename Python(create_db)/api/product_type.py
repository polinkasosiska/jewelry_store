from typing import List
from fastapi import APIRouter, Depends, HTTPException
from starlette import status
import constants
from models import tables
from models.auth import User
from services.auth import get_current_user
from services.product_type import ProductTypeService

router = APIRouter(prefix='/product-type')


@router.post('/add', response_model=List[tables.ProductType])
def add(name: str, user: User = Depends(get_current_user),
                     service: ProductTypeService = Depends()
                     ):
    if not user.is_moderator:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=constants.ACCESS_ERROR)
    service.add_product_type(name)
    return service.get_product_types()


@router.get('/get-list', response_model=List[tables.ProductType])
def get_list(service: ProductTypeService = Depends()):
    return service.get_product_types()


@router.delete('/{product_type_id}', response_model=int)
def delete(product_type_id: int, user: User = Depends(get_current_user),
                  service: ProductTypeService = Depends()):
    if not user.is_moderator:
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail=constants.ACCESS_ERROR)
    return service.delete(product_type_id)

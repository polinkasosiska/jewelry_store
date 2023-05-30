from fastapi import APIRouter
from .auth import router as auth_router
from .product_type import router as product_type_router
from .product import router as product_router
from .wish_list import router as wish_list_router
from .cart import router as cart_router
from .orders import router as orders_router

router = APIRouter()
router.include_router(auth_router)
router.include_router(product_type_router)
router.include_router(product_router)
router.include_router(wish_list_router)
router.include_router(cart_router)
router.include_router(orders_router)

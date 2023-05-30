package com.sysoliatina.jewelrystore.models.enums

enum class OrderStatus {
    IN_PROGRESS, COMPLETE, CANCELLED;

    companion object {
        fun getStatuses(): MutableList<OrderStatus> {
            return mutableListOf(IN_PROGRESS, COMPLETE, CANCELLED)
        }
    }
}
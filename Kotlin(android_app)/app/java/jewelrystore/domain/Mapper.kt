package com.sysoliatina.jewelrystore.domain

interface Mapper<T, R> {

    fun map(input: T): R
}
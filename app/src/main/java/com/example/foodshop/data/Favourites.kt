package com.example.foodshop.data

data class Favourites(
    val product: Product,
){
    constructor():this(Product())
}

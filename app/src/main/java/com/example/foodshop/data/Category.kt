package com.example.foodshop.data

sealed class Category(val category: String) {
    object Lunch : Category("lunch")
    object Dinner : Category("Dinner")
    object Breakfast : Category("Breakfast")
}
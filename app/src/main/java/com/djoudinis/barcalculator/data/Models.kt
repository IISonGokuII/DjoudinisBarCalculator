package com.djoudinis.barcalculator.data

import java.io.Serializable

data class Drink(
    val id: Int,
    val name: String,
    val category: String,
    val avgPrice: Double,
    val ingredients: List<String> = emptyList(),
    val abv: Double,
    val color: String,
    val emoji: String,
    val description: String,
    val brewery: String? = null,
    val region: String? = null,
    val type: DrinkType
) : Serializable

enum class DrinkType {
    COCKTAIL, BEER, SHOT, SOFTDRINK
}

data class BillItem(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val price: Double,
    val emoji: String,
    val abv: Double,
    val time: String,
) : Serializable

data class BacDrink(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val abv: Double,
    val ml: Int
) : Serializable

data class BarVisit(
    val barName: String,
    val billItems: MutableList<BillItem> = mutableListOf(),
    val startTime: Long = System.currentTimeMillis()
) : Serializable

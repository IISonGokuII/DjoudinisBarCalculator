package com.djoudinis.barcalculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.djoudinis.barcalculator.data.BacDrink
import com.djoudinis.barcalculator.data.BillItem
import com.djoudinis.barcalculator.data.Drink
import com.djoudinis.barcalculator.data.DrinkDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {
    // Bill State
    var billItems = mutableStateListOf<BillItem>()
        private set

    // BAC State
    var bacDrinks = mutableStateListOf<BacDrink>()
        private set
    var weight by mutableStateOf(80f)
    var hoursSinceFirstDrink by mutableStateOf(0f)
    var isMale by mutableStateOf(true)

    // UI State
    var isDrunkMode by mutableStateOf(false)
    var currentDrinkFilter by mutableStateOf("Alle")

    // Calculations
    val billTotal: Double
        get() = billItems.sumOf { it.price }

    val bacValue: Double
        get() {
            if (bacDrinks.isEmpty()) return 0.0
            val r = if (isMale) 0.68 else 0.55
            var totalAlcoholGrams = 0.0
            bacDrinks.forEach { d ->
                totalAlcoholGrams += d.ml * (d.abv / 100.0) * 0.789
            }
            val bac = (totalAlcoholGrams / (weight * r)) - (0.15 * hoursSinceFirstDrink)
            return maxOf(0.0, bac)
        }

    fun addToBill(drink: Drink, customPrice: Double? = null) {
        val price = customPrice ?: drink.avgPrice
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        billItems.add(BillItem(name = drink.name, price = price, emoji = drink.emoji, abv = drink.abv, time = time))
    }

    fun removeBillItem(item: BillItem) {
        billItems.remove(item)
    }

    fun clearBill() {
        billItems.clear()
    }

    fun addBacDrink(name: String, abv: Double, ml: Int) {
        bacDrinks.add(BacDrink(name = name, abv = abv, ml = ml))
    }

    fun removeBacDrink(drink: BacDrink) {
        bacDrinks.remove(drink)
    }

    fun calculateTip(amount: Double, percent: Int): Double {
        return amount * (percent / 100.0)
    }

    fun calculateSplit(amount: Double, people: Int, tipPercent: Int): Double {
        val total = amount * (1 + tipPercent / 100.0)
        return if (people > 0) total / people else total
    }
}

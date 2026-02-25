package com.djoudinis.barcalculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.djoudinis.barcalculator.data.BacDrink
import com.djoudinis.barcalculator.data.BillItem
import com.djoudinis.barcalculator.data.Drink
import com.djoudinis.barcalculator.data.DrinkDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {
    val billItems = mutableStateListOf<BillItem>()
    val bacDrinks = mutableStateListOf<BacDrink>()
    
    var weight by mutableStateOf(80f)
    var hoursSinceFirstDrink by mutableStateOf(0f)
    var isMale by mutableStateOf(true)
    var isDrunkMode by mutableStateOf(false)

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
            return if (bac > 0) bac else 0.0
        }

    val djoudiniWisdom: String
        get() = when {
            bacValue == 0.0 -> "Nüchtern? Zeit für einen Drink! 💡"
            bacValue < 0.5 -> "Alles im grünen Bereich. 😊"
            bacValue < 1.0 -> "Lustig, aber trink mal ein Wasser. 💧"
            else -> "Taxi-Zeit! 🚕"
        }

    val partyColor: Color
        get() = when {
            bacValue < 0.5 -> Color(0xFFD4AF37)
            bacValue < 1.0 -> Color(0xFF00E676)
            else -> Color(0xFFFF1744)
        }

    fun addToBill(drink: Drink) {
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        billItems.add(BillItem(name = drink.name, price = drink.avgPrice, emoji = drink.emoji, abv = drink.abv, time = time))
    }

    fun removeBillItem(item: BillItem) {
        billItems.remove(item)
    }

    fun addBacDrink(abv: Double, ml: Int) {
        bacDrinks.add(BacDrink(name = "Drink", abv = abv, ml = ml))
    }
}

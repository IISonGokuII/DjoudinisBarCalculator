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
    
    // Innovation Features State
    var showSobrietyCheck by mutableStateOf(false)
    var sobrietyAction by mutableStateOf<(() -> Unit)?>(null)
    var mathProblem by mutableStateOf("")
    var mathAnswer by mutableStateOf(0)
    var lastSuggestedDrink by mutableStateOf<Drink?>(null)
    var taxiNumber by mutableStateOf("112") // Default emergency

    val djoudiniWisdom: String
        get() = when {
            bacValue == 0.0 -> "Nüchtern? Langweilig. Schüttle das Handy für eine Idee! 💡"
            bacValue < 0.3 -> "Der Abend ist jung. Ein Mojito würde jetzt gut passen. 🍃"
            bacValue < 0.5 -> "Du bist in der 'Sweet Spot' Zone. Genieße es! 😊"
            bacValue < 0.8 -> "Achtung: Die Witze werden schlechter, der Durst größer. 🍻"
            bacValue < 1.2 -> "Zeit für ein Glas Wasser zwischen den Drinks! 💧"
            else -> "STOPP! Handy weg, Taxi rufen, Döner kaufen. 🚕"
        }

    val partyColor: Color
        get() = when {
            bacValue < 0.5 -> Color(0xFFD4AF37) // Gold
            bacValue < 1.0 -> Color(0xFF00E676) // Neon Green
            bacValue < 1.5 -> Color(0xFFFFD600) // Amber
            else -> Color(0xFFFF1744) // Neon Red
        }

    // Calculations
    val billTotal: Double
        get() = billItems.sumOf { it.price }

    val swayIntensity: Float
        get() = (bacValue.toFloat() * 10f).coerceIn(0f, 15f)

    fun generateMathProblem() {
        val a = (5..15).random()
        val b = (5..15).random()
        mathProblem = "$a + $b = ?"
        mathAnswer = a + b
    }

    fun checkSobrietyAndRun(action: () -> Unit) {
        if (bacValue > 0.5) {
            generateMathProblem()
            sobrietyAction = action
            showSobrietyCheck = true
        } else {
            action()
        }
    }

    fun getRandomSuggestion(): Drink {
        val suggestion = DrinkDatabase.allDrinks.random()
        lastSuggestedDrink = suggestion
        return suggestion
    }

    val bacValue: Double
        get() {
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

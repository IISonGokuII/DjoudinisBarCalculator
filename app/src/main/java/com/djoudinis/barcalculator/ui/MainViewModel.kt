package com.djoudinis.barcalculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.djoudinis.barcalculator.data.*
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
    
    var lastSuggestedDrink by mutableStateOf<Drink?>(null)
    var selectedDrinkForPrice by mutableStateOf<Drink?>(null)
    
    var peakBac by mutableStateOf(0.0)
    
    // Innovation: Water Guardian & Splitter
    var waterCount by mutableStateOf(0)
    var splitPeopleCount by mutableIntStateOf(2)
    var tipPercent by mutableIntStateOf(10)

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
            val finalBac = if (bac > 0) bac else 0.0
            if (finalBac > peakBac) peakBac = finalBac
            return finalBac
        }

    val hangoverForecast: Int
        get() {
            val alcFactor = (bacValue * 40).toInt()
            val waterBonus = (waterCount * 15)
            return (alcFactor - waterBonus).coerceIn(0, 100)
        }

    val djoudiniWisdom: String
        get() = when {
            bacValue == 0.0 -> "Nüchtern? Zeit für einen Signature-Cocktail! 💡"
            bacValue < 0.3 -> "Der Abend beginnt perfekt. Schüttel dein Handy! 🎲"
            bacValue < 0.5 -> "Sweet Spot erreicht. Trink jetzt ein Wasser! 💧"
            bacValue < 0.8 -> "Achtung: Deine Witze werden schlechter. 🍻"
            bacValue < 1.2 -> "Zeit für Djoudinis Geheimtipp: Ein großes Glas Wasser. 🌊"
            else -> "Handy weg, Taxi rufen, ab ins Bett. 🚕"
        }

    val partyColor: Color
        get() = when {
            bacValue < 0.4 -> Color(0xFFD4AF37) // Gold
            bacValue < 0.8 -> Color(0xFF00E676) // Neon Green
            bacValue < 1.2 -> Color(0xFFFFD600) // Amber
            else -> Color(0xFFFF1744) // Neon Red
        }

    fun addToBill(drink: Drink, customPrice: Double? = null) {
        val price = customPrice ?: drink.avgPrice
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        billItems.add(BillItem(name = drink.name, price = price, emoji = drink.emoji, abv = drink.abv, time = time))
        
        if (drink.abv > 0) {
            val ml = if (drink.type == DrinkType.COCKTAIL) 250 else if (drink.name.contains("0.5")) 500 else 330
            addBacDrink(drink.name, drink.abv, ml)
        }
    }

    fun addWater() {
        waterCount++
    }

    fun removeBillItem(item: BillItem) {
        billItems.remove(item)
    }

    fun addBacDrink(name: String, abv: Double, ml: Int) {
        bacDrinks.add(BacDrink(name = name, abv = abv, ml = ml))
    }
    
    fun getRandomSuggestion() {
        lastSuggestedDrink = DrinkDatabase.allDrinks.random()
    }
    
    fun getPriceAnalysis(drink: Drink, inputPrice: Double): String {
        val diff = ((inputPrice - drink.avgPrice) / drink.avgPrice) * 100
        return when {
            diff <= 10 -> "✅ Top Preis!"
            diff <= 30 -> "⚠️ Etwas teuer."
            else -> "🚨 ABZOCKE! (+${diff.toInt()}% über Schnitt)"
        }
    }
}

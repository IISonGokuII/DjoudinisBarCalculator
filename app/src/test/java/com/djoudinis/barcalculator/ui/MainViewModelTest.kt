package com.djoudinis.barcalculator.ui

import com.djoudinis.barcalculator.data.DrinkDatabase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun `calculateBac_forSingleBeer_isCorrect`() {
        // Setup the scenario
        viewModel.isMale = true
        viewModel.weight = 80f
        viewModel.hoursSinceFirstDrink = 1f

        // Action: Add a beer (500ml, 5% ABV)
        val beerMl = 500
        val beerAbv = 5.0
        viewModel.addBacDrink("Test Beer", beerAbv, beerMl)

        // Calculation
        // Alcohol in grams = 500ml * (5/100) * 0.789 = 19.725g
        // BAC = (19.725g / (80kg * 0.68)) - (0.15 * 1h) = 0.36259 - 0.15 = 0.21259
        val expectedBac = 0.21

        // Assertion
        assertEquals(expectedBac, viewModel.bacValue, 0.01)
    }

    @Test
    fun `billTotal_isCalculatedCorrectly`() {
        // Setup with some drinks from the database
        val cocktail = DrinkDatabase.cocktails.first()
        val beer = DrinkDatabase.beers.first()

        // Action
        viewModel.addToBill(cocktail)
        viewModel.addToBill(beer)
        
        // Calculation
        val expectedTotal = viewModel.standardCocktailPrice + viewModel.standardBeerPrice

        // Assertion
        assertEquals(expectedTotal, viewModel.billTotal, 0.001)
    }
}

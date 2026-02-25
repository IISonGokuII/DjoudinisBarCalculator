package com.djoudinis.barcalculator.data

object DrinkDatabase {
    val cocktails = listOf(
        Drink(1, "Mojito", "Klassiker", 10.50, listOf("Weißer Rum", "Limette", "Zucker", "Minze", "Sodawasser"), 10.0, "#a8e6a3", "🍃", "Kubanischer Klassiker mit frischer Minze", type = DrinkType.COCKTAIL),
        Drink(2, "Margarita", "Klassiker", 11.00, listOf("Tequila", "Triple Sec", "Limettensaft"), 13.0, "#f0e68c", "🌮", "Mexikanischer Tequila-Klassiker mit Salzrand", type = DrinkType.COCKTAIL),
        Drink(3, "Old Fashioned", "Klassiker", 12.50, listOf("Bourbon", "Zucker", "Angostura Bitter", "Orangenzeste"), 32.0, "#d4760a", "🥃", "Der Ur-Cocktail seit 1806", type = DrinkType.COCKTAIL),
        Drink(4, "Negroni", "Klassiker", 11.50, listOf("Gin", "Campari", "Roter Wermut"), 24.0, "#cc3333", "🔴", "Italienischer Bitter-Klassiker", type = DrinkType.COCKTAIL),
        Drink(11, "Piña Colada", "Tiki", 11.00, listOf("Weißer Rum", "Kokosmilch", "Ananassaft"), 12.0, "#fffdd0", "🍍", "Karibischer Kokos-Ananas-Traum", type = DrinkType.COCKTAIL),
        Drink(13, "Moscow Mule", "Longdrink", 10.50, listOf("Vodka", "Ginger Beer", "Limette"), 10.0, "#f5deb3", "🫏", "Serviert im Kupferbecher", type = DrinkType.COCKTAIL),
        Drink(15, "Aperol Spritz", "Spritz", 9.50, listOf("Aperol", "Prosecco", "Sodawasser"), 8.0, "#ff6347", "🧡", "Italiens Sommer-Hit", type = DrinkType.COCKTAIL),
        Drink(41, "Espresso Martini", "Modern", 12.50, listOf("Vodka", "Kaffeelikör", "Espresso", "Zuckersirup"), 18.0, "#2f1b14", "☕", "Koffein trifft Alkohol", type = DrinkType.COCKTAIL),
        Drink(81, "Long Island Iced Tea", "Longdrink", 12.00, listOf("Vodka", "Gin", "Rum", "Tequila", "Triple Sec", "Zitrone", "Cola"), 22.0, "#d2691e", "🏝️", "5 Spirituosen in einem Glas", type = DrinkType.COCKTAIL),
        Drink(100, "Gin Basil Smash", "Modern", 11.50, listOf("Gin", "Basilikum", "Zitronensaft", "Zucker"), 16.0, "#3cb371", "🌿", "Hamburger Gin-Revolution", type = DrinkType.COCKTAIL)
    )

    val beers = listOf(
        Drink(1, "Krombacher Pils", "Pilsener", 4.20, emptyList(), 4.8, "#f4c430", "🍺", "Eine Perle der Natur", "Krombacher Brauerei", "Nordrhein-Westfalen", DrinkType.BEER),
        Drink(2, "Bitburger Premium Pils", "Pilsener", 4.20, emptyList(), 4.8, "#f0c300", "🍺", "Bitte ein Bit", "Bitburger Brauerei", "Rheinland-Pfalz", DrinkType.BEER),
        Drink(13, "Erdinger Weißbier", "Weizenbier", 4.50, emptyList(), 5.3, "#ffb347", "🌾", "Bayerns Weißbier Nr. 1", "Erdinger Weißbräu", "Bayern", DrinkType.BEER),
        Drink(19, "Augustiner Helles", "Helles", 4.50, emptyList(), 5.2, "#f5d442", "☀️", "Münchens Liebling", "Augustiner Bräu", "Bayern", DrinkType.BEER),
        Drink(20, "Tegernseer Hell", "Helles", 4.50, emptyList(), 4.8, "#f4c430", "☀️", "Vom Tegernsee", "Herzogl. Bayerisches Brauhaus", "Bayern", DrinkType.BEER),
        Drink(47, "Rothaus Tannenzäpfle", "Pilsener", 4.20, emptyList(), 5.1, "#f0c300", "🌲", "Schwarzwald-Kult", "Badische Staatsbrauerei Rothaus", "Baden-Württemberg", DrinkType.BEER)
    )

    val allDrinks = cocktails + beers
}

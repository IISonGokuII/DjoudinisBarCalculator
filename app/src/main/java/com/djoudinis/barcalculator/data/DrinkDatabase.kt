package com.djoudinis.barcalculator.data

object DrinkDatabase {
    val cocktails = listOf(
        Drink(1, "Mojito", "Klassiker", 10.50, listOf("Weißer Rum", "Minze", "Limette"), 10.0, "#a8e6a3", "🍃", "Kubanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(2, "Margarita", "Klassiker", 11.00, listOf("Tequila", "Triple Sec", "Limette"), 13.0, "#f0e68c", "🌮", "Mexikanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(3, "Old Fashioned", "Klassiker", 12.50, listOf("Bourbon", "Zucker", "Bitter"), 32.0, "#d4760a", "🥃", "Der Ur-Cocktail", type = DrinkType.COCKTAIL),
        Drink(4, "Negroni", "Klassiker", 11.50, listOf("Gin", "Campari", "Wermut"), 24.0, "#cc3333", "🔴", "Italienischer Bitter-Drink", type = DrinkType.COCKTAIL),
        Drink(5, "Daiquiri", "Klassiker", 10.00, listOf("Rum", "Limette", "Zucker"), 20.0, "#fffacd", "🍋", "Hemingways Favorit", type = DrinkType.COCKTAIL),
        Drink(6, "Martini", "Klassiker", 12.00, listOf("Gin", "Wermut", "Olive"), 30.0, "#e8e8e8", "🍸", "Elegant & trocken", type = DrinkType.COCKTAIL),
        Drink(8, "Whiskey Sour", "Sour", 11.00, listOf("Bourbon", "Zitrone", "Eiweiß"), 15.0, "#fff3b0", "🍋", "Süß-sauer Balance", type = DrinkType.COCKTAIL),
        Drink(11, "Piña Colada", "Tiki", 11.00, listOf("Rum", "Kokos", "Ananas"), 12.0, "#fffdd0", "🍍", "Tropischer Traum", type = DrinkType.COCKTAIL),
        Drink(13, "Moscow Mule", "Longdrink", 10.50, listOf("Vodka", "Ginger Beer", "Limette"), 10.0, "#f5deb3", "🫏", "Im Kupferbecher serviert", type = DrinkType.COCKTAIL),
        Drink(14, "Gin Tonic", "Longdrink", 10.00, listOf("Gin", "Tonic", "Zitrone"), 12.0, "#e0ffff", "🫧", "Der zeitlose Klassiker", type = DrinkType.COCKTAIL),
        Drink(15, "Aperol Spritz", "Spritz", 9.50, listOf("Aperol", "Prosecco", "Soda"), 8.0, "#ff6347", "🧡", "Sommer im Glas", type = DrinkType.COCKTAIL),
        Drink(41, "Espresso Martini", "Modern", 12.50, listOf("Vodka", "Kaffee", "Kaffeelikör"), 18.0, "#2f1b14", "☕", "Wachmacher mit Kick", type = DrinkType.COCKTAIL),
        Drink(46, "Pornstar Martini", "Modern", 13.00, listOf("Vanilla Vodka", "Passionsfrucht"), 14.0, "#ffb347", "⭐", "Fruchtiger Party-Drink", type = DrinkType.COCKTAIL),
        Drink(52, "Cuba Libre", "Longdrink", 9.50, listOf("Rum", "Cola", "Limette"), 10.0, "#3b1e08", "🇨🇺", "Einfach & Ehrlich", type = DrinkType.COCKTAIL),
        Drink(81, "Long Island Iced Tea", "Longdrink", 12.00, listOf("Vodka", "Gin", "Rum", "Tequila", "Cola"), 22.0, "#d2691e", "🏝️", "Mächtiger Mix", type = DrinkType.COCKTAIL),
        Drink(91, "Virgin Mojito", "Mocktail", 7.50, listOf("Minze", "Limette", "Zucker", "Soda"), 0.0, "#a8e6cf", "🍃", "Alkoholfreier Mojito", type = DrinkType.COCKTAIL),
        Drink(100, "Gin Basil Smash", "Modern", 11.50, listOf("Gin", "Basilikum", "Zitrone"), 16.0, "#3cb371", "🌿", "Grüne Frische", type = DrinkType.COCKTAIL)
    )

    val beers = listOf(
        Drink(1, "Pilsener (0.33l)", "Bier", 4.00, emptyList(), 4.8, "#f4c430", "🍺", "Frisch gezapft", type = DrinkType.BEER),
        Drink(2, "Großes Pils (0.5l)", "Bier", 5.20, emptyList(), 4.8, "#f4c430", "🍺", "Für den großen Durst", type = DrinkType.BEER),
        Drink(3, "Weizen (0.5l)", "Bier", 5.50, emptyList(), 5.3, "#ffb347", "🌾", "Helles Hefeweizen", type = DrinkType.BEER),
        Drink(4, "Helles (0.5l)", "Bier", 4.80, emptyList(), 5.1, "#f5d442", "☀️", "Mild & Süffig", type = DrinkType.BEER),
        Drink(5, "Kölsch", "Bier", 2.80, emptyList(), 4.8, "#f5e642", "⛪", "Traditionell im Stängchen", type = DrinkType.BEER),
        Drink(6, "Radler", "Bier", 4.50, emptyList(), 2.5, "#fff44f", "🍋", "Bier mit Limo", type = DrinkType.BEER)
    )

    val allDrinks = cocktails + beers
}

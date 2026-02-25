package com.djoudinis.barcalculator.data

object DrinkDatabase {
    // Standardpreise (werden im ViewModel über Settings überschrieben)
    const val DEFAULT_COCKTAIL_PRICE = 5.00
    const val DEFAULT_BEER_PRICE = 2.80

    val cocktails = listOf(
        Drink(1, "Mojito", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Weißer Rum", "Minze", "Limette", "Zucker", "Soda"), 10.0, "#a8e6a3", "🍃", "Kubanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(2, "Margarita", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "Triple Sec", "Limettensaft"), 13.0, "#f0e68c", "🌮", "Mexikanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(3, "Old Fashioned", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Zucker", "Bitter"), 32.0, "#d4760a", "🥃", "Der Ur-Cocktail", type = DrinkType.COCKTAIL),
        Drink(4, "Negroni", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Campari", "Roter Wermut"), 24.0, "#cc3333", "🔴", "Italienischer Bitter-Drink", type = DrinkType.COCKTAIL),
        Drink(5, "Daiquiri", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Weißer Rum", "Limette", "Zucker"), 20.0, "#fffacd", "🍋", "Hemingways Favorit", type = DrinkType.COCKTAIL),
        Drink(6, "Martini", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Trockener Wermut", "Olive"), 30.0, "#e8e8e8", "🍸", "Elegant & trocken", type = DrinkType.COCKTAIL),
        Drink(7, "Manhattan", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Rye Whiskey", "Süßer Wermut", "Bitter"), 28.0, "#8b4513", "🏙️", "New York Style", type = DrinkType.COCKTAIL),
        Drink(8, "Whiskey Sour", "Sour", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Zitrone", "Eiweiß"), 15.0, "#fff3b0", "🍋", "Süß-sauer Balance", type = DrinkType.COCKTAIL),
        Drink(9, "Cosmopolitan", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka Citron", "Triple Sec", "Cranberry"), 18.0, "#ff69b4", "💗", "Sex and the City Drink", type = DrinkType.COCKTAIL),
        Drink(10, "Mai Tai", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Curaçao", "Orgeat", "Limette"), 17.0, "#ff8c00", "🌺", "Tiki-König", type = DrinkType.COCKTAIL),
        Drink(11, "Piña Colada", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Kokosmilch", "Ananas"), 12.0, "#fffdd0", "🍍", "Tropischer Traum", type = DrinkType.COCKTAIL),
        Drink(12, "Caipirinha", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Cachaça", "Limette", "Rohrzucker"), 18.0, "#c5e384", "🇧🇷", "Brasiliens Stolz", type = DrinkType.COCKTAIL),
        Drink(13, "Moscow Mule", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Ginger Beer", "Limette"), 10.0, "#f5deb3", "🫏", "Im Kupferbecher", type = DrinkType.COCKTAIL),
        Drink(14, "Gin Tonic", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Tonic Water", "Gurke"), 12.0, "#e0ffff", "🫧", "Der Zeitlose", type = DrinkType.COCKTAIL),
        Drink(15, "Aperol Spritz", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Aperol", "Prosecco", "Soda"), 8.0, "#ff6347", "🧡", "Sommer-Hit", type = DrinkType.COCKTAIL),
        Drink(16, "Hugo", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Holunder", "Prosecco", "Minze"), 6.0, "#98fb98", "🌿", "Erfrischend leicht", type = DrinkType.COCKTAIL),
        Drink(17, "Tequila Sunrise", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "O-Saft", "Grenadine"), 11.0, "#ff4500", "🌅", "Urlaubsfeeling", type = DrinkType.COCKTAIL),
        Drink(18, "Sex on the Beach", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Pfirsich", "O-Saft"), 10.0, "#ff6b6b", "🏖️", "Party-Klassiker", type = DrinkType.COCKTAIL),
        Drink(19, "Blue Lagoon", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Blue Curaçao", "Zitrone"), 10.0, "#00bfff", "💎", "Leuchtend blau", type = DrinkType.COCKTAIL),
        Drink(20, "Zombie", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("3 Sorten Rum", "Limette", "Grenadine"), 22.0, "#9acd32", "🧟", "Vorsicht: Stark!", type = DrinkType.COCKTAIL),
        Drink(41, "Espresso Martini", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Kaffee", "Kaffeelikör"), 18.0, "#2f1b14", "☕", "Wachmacher", type = DrinkType.COCKTAIL),
        Drink(46, "Pornstar Martini", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vanilla Vodka", "Passoa", "Prosecco"), 14.0, "#ffb347", "⭐", "Fruchtig & Sexy", type = DrinkType.COCKTAIL),
        Drink(51, "Dark 'n' Stormy", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Dark Rum", "Ginger Beer", "Limette"), 12.0, "#654321", "⛈️", "Bermuda Drink", type = DrinkType.COCKTAIL),
        Drink(52, "Cuba Libre", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Cola", "Limette"), 10.0, "#3b1e08", "🇨🇺", "Freiheit im Glas", type = DrinkType.COCKTAIL),
        Drink(71, "Amaretto Sour", "Sour", DEFAULT_COCKTAIL_PRICE, listOf("Amaretto", "Zitrone", "Zucker"), 12.0, "#daa520", "🍒", "Mandel-Genuss", type = DrinkType.COCKTAIL),
        Drink(81, "Long Island Iced Tea", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Gin", "Rum", "Tequila", "Triple Sec", "Cola"), 22.0, "#d2691e", "🏝️", "5-Spirituosen Mix", type = DrinkType.COCKTAIL),
        Drink(82, "B-52", "Shooter", DEFAULT_COCKTAIL_PRICE, listOf("Kaffeelikör", "Baileys", "Grand Marnier"), 20.0, "#8b6508", "✈️", "Geschichteter Shot", type = DrinkType.COCKTAIL),
        Drink(86, "Vesper Martini", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Vodka", "Lillet Blanc"), 28.0, "#f0e68c", "🔫", "James Bond Style", type = DrinkType.COCKTAIL),
        Drink(91, "Virgin Mojito", "Mocktail", DEFAULT_COCKTAIL_PRICE, listOf("Minze", "Limette", "Zucker", "Soda"), 0.0, "#a8e6cf", "🍃", "Alkoholfrei", type = DrinkType.COCKTAIL),
        Drink(92, "Ipanema", "Mocktail", DEFAULT_COCKTAIL_PRICE, listOf("Limette", "Zucker", "Ginger Ale"), 0.0, "#fdd835", "🏖️", "Fruchtiger Mocktail", type = DrinkType.COCKTAIL),
        Drink(100, "Gin Basil Smash", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Basilikum", "Zitrone"), 16.0, "#3cb371", "🌿", "Grüne Frische", type = DrinkType.COCKTAIL)
    )

    val beers = listOf(
        Drink(201, "Pilsener (0.33l)", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f4c430", "🍺", "Klassisch herb", type = DrinkType.BEER),
        Drink(202, "Großes Pils (0.5l)", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f4c430", "🍺", "Gezapfte Frische", type = DrinkType.BEER),
        Drink(203, "Weizen (0.5l)", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.3, "#ffb347", "🌾", "Hefeweizen hell", type = DrinkType.BEER),
        Drink(204, "Helles (0.5l)", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.1, "#f5d442", "☀️", "Mild & süffig", type = DrinkType.BEER),
        Drink(205, "Kölsch", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f5e642", "⛪", "Kölner Tradition", type = DrinkType.BEER),
        Drink(206, "Radler", "Bier", DEFAULT_BEER_PRICE, emptyList(), 2.5, "#fff44f", "🍋", "Bier mit Limo", type = DrinkType.BEER),
        Drink(207, "Altbier", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#8b4513", "🏰", "Düsseldorfer Art", type = DrinkType.BEER),
        Drink(208, "Schwarzbier", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.9, "#1a1a1a", "🌑", "Dunkles Aroma", type = DrinkType.BEER),
        Drink(209, "Hefeweizen Alkoholfrei", "Bier", DEFAULT_BEER_PRICE, emptyList(), 0.5, "#ffcc80", "🌾", "Isotonisch", type = DrinkType.BEER),
        Drink(210, "Craft IPA", "Bier", DEFAULT_BEER_PRICE, emptyList(), 6.5, "#ffb300", "🚀", "Hopfen-Power", type = DrinkType.BEER)
    )

    val allDrinks = cocktails + beers
}

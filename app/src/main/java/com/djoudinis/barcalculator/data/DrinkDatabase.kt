package com.djoudinis.barcalculator.data

object DrinkDatabase {
    const val DEFAULT_COCKTAIL_PRICE = 5.00
    const val DEFAULT_BEER_PRICE = 2.80
    const val DEFAULT_SHOT_PRICE = 2.00

    val cocktails = listOf(
        // Klassiker
        Drink(1, "Mojito", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Minze", "Limette"), 10.0, "#a8e6a3", "🍃", "Kubanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(2, "Margarita", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "Triple Sec", "Limette"), 13.0, "#f0e68c", "🌮", "Mexikanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(3, "Old Fashioned", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Zucker", "Bitter"), 32.0, "#d4760a", "🥃", "Der Ur-Cocktail", type = DrinkType.COCKTAIL),
        Drink(4, "Negroni", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Campari", "Wermut"), 24.0, "#cc3333", "🔴", "Italienischer Bitter-Drink", type = DrinkType.COCKTAIL),
        Drink(5, "Daiquiri", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Limette", "Zucker"), 20.0, "#fffacd", "🍋", "Hemingways Favorit", type = DrinkType.COCKTAIL),
        Drink(6, "Martini", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Wermut", "Olive"), 30.0, "#e8e8e8", "🍸", "Elegant & trocken", type = DrinkType.COCKTAIL),
        Drink(7, "Manhattan", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Whiskey", "Wermut", "Bitter"), 28.0, "#8b4513", "🏙️", "New York Style", type = DrinkType.COCKTAIL),
        Drink(8, "Whiskey Sour", "Sour", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Zitronensaft", "Eiweiß"), 15.0, "#fff3b0", "🍋", "Süß-sauer Balance", type = DrinkType.COCKTAIL),
        Drink(9, "Cosmopolitan", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka Citron", "Triple Sec", "Cranberry"), 18.0, "#ff69b4", "💗", "Pinker Klassiker", type = DrinkType.COCKTAIL),
        Drink(13, "Moscow Mule", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Ginger Beer", "Limette"), 10.0, "#f5deb3", "🫏", "Im Kupferbecher", type = DrinkType.COCKTAIL),
        Drink(14, "Gin Tonic", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Tonic Water", "Gurke"), 12.0, "#e0ffff", "🫧", "Der Zeitlose", type = DrinkType.COCKTAIL),
        Drink(15, "Aperol Spritz", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Aperol", "Prosecco", "Soda"), 8.0, "#ff6347", "🧡", "Sommer-Hit", type = DrinkType.COCKTAIL),
        Drink(16, "Hugo", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Holunder", "Prosecco", "Minze"), 6.0, "#98fb98", "🌿", "Erfrischend leicht", type = DrinkType.COCKTAIL),
        Drink(41, "Espresso Martini", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Kaffee", "Kaffeelikör"), 18.0, "#2f1b14", "☕", "Wachmacher", type = DrinkType.COCKTAIL),
        Drink(46, "Pornstar Martini", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vanilla Vodka", "Passoa", "Prosecco"), 14.0, "#ffb347", "⭐", "Fruchtig & Sexy", type = DrinkType.COCKTAIL),
        Drink(81, "Long Island Iced Tea", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Gin", "Rum", "Tequila", "Cola"), 22.0, "#d2691e", "🏝️", "5-Spirituosen Mix", type = DrinkType.COCKTAIL),
        Drink(100, "Gin Basil Smash", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Basilikum", "Zitrone"), 16.0, "#3cb371", "🌿", "Grüne Frische", type = DrinkType.COCKTAIL),
        Drink(101, "Swimming Pool", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Rum", "Blue Curaçao", "Kokos"), 12.0, "#87ceeb", "🏊", "Karibisches Blau", type = DrinkType.COCKTAIL),
        Drink(102, "Caipiroska", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Limette", "Zucker"), 18.0, "#f5f5f5", "🧊", "Vodka-Caipi", type = DrinkType.COCKTAIL),
        Drink(103, "Tequila Sunrise", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "O-Saft", "Grenadine"), 11.0, "#ff4500", "🌅", "Urlaubsfeeling", type = DrinkType.COCKTAIL),
        Drink(104, "Sex on the Beach", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Pfirsich", "O-Saft"), 10.0, "#ff6b6b", "🏖️", "Party-Klassiker", type = DrinkType.COCKTAIL),
        Drink(105, "Pina Colada", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Kokos", "Ananas"), 12.0, "#fffdd0", "🍍", "Cremiger Traum", type = DrinkType.COCKTAIL),
        Drink(106, "Mai Tai", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Orangenlikör", "Mandel"), 17.0, "#ff8c00", "🌺", "Tiki-Legende", type = DrinkType.COCKTAIL),
        Drink(107, "Bramble", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Zitrone", "Brombeere"), 15.0, "#800080", "🫐", "Fruchtiger Gin-Mix", type = DrinkType.COCKTAIL),
        Drink(108, "Dark 'n' Stormy", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Ginger Beer", "Limette"), 12.0, "#654321", "⛈️", "Bermuda Original", type = DrinkType.COCKTAIL),
        Drink(109, "Aviation", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Maraschino", "Zitrone"), 20.0, "#b0c4de", "✈️", "Himmelblau & Edel", type = DrinkType.COCKTAIL),
        Drink(110, "Sidecar", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Cognac", "Triple Sec", "Zitrone"), 22.0, "#f4a460", "🏍️", "Pariser Klassiker", type = DrinkType.COCKTAIL),
        Drink(111, "Bloody Mary", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Tomate", "Gewürze"), 10.0, "#dc143c", "🍅", "Herzhafter Drink", type = DrinkType.COCKTAIL),
        Drink(112, "White Russian", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Kaffeelikör", "Sahne"), 15.0, "#f5deb3", "🥛", "The Big Lebowski Style", type = DrinkType.COCKTAIL),
        Drink(113, "Black Russian", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Kaffeelikör"), 22.0, "#1a1a2e", "🖤", "Stark & Pur", type = DrinkType.COCKTAIL),
        Drink(115, "Tom Collins", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Zitrone", "Soda"), 11.0, "#fafad2", "🍋", "Prickelnde Frische", type = DrinkType.COCKTAIL),
        Drink(116, "French 75", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Champagner", "Zitrone"), 14.0, "#fffacd", "🥂", "Prickelnd & Stark", type = DrinkType.COCKTAIL),
        Drink(117, "Sazerac", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Rye Whiskey", "Absinth", "Bitter"), 30.0, "#d4a574", "⚜️", "New Orleans Tradition", type = DrinkType.COCKTAIL),
        Drink(118, "Clover Club", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Himbeere", "Zitrone"), 16.0, "#db7093", "🍀", "Rosa Finesse", type = DrinkType.COCKTAIL),
        Drink(119, "Paloma", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "Grapefruit", "Limette"), 12.0, "#ffb6c1", "🕊️", "Mexikos Favorit", type = DrinkType.COCKTAIL),
        Drink(120, "Americano", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Campari", "Wermut", "Soda"), 9.0, "#cc3333", "🇮🇹", "Leichter Aperitif", type = DrinkType.COCKTAIL)
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
        Drink(210, "Craft IPA", "Bier", DEFAULT_BEER_PRICE, emptyList(), 6.5, "#ffb300", "🚀", "Hopfen-Power", type = DrinkType.BEER),
        Drink(211, "Berliner Weisse", "Bier", DEFAULT_BEER_PRICE, emptyList(), 3.0, "#fffacd", "🧸", "Mit Schuss", type = DrinkType.BEER),
        Drink(212, "Guinness", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.2, "#000000", "🇮🇪", "Irisches Stout", type = DrinkType.BEER),
        Drink(213, "Lager", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.0, "#f5d442", "🍺", "Internationaler Stil", type = DrinkType.BEER),
        Drink(214, "Kellerbier", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.2, "#daa520", "🏺", "Naturtrüb", type = DrinkType.BEER),
        Drink(215, "Export", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.5, "#f4c430", "🏭", "Kräftiges Lager", type = DrinkType.BEER)
    )

    val shots = listOf(
        Drink(301, "Jägermeister", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 35.0, "#2f4f4f", "🦌", "Kräuterlikör", type = DrinkType.SHOT),
        Drink(302, "Tequila Silver", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#f5f5f5", "🌵", "Klarer Tequila", type = DrinkType.SHOT),
        Drink(303, "Tequila Gold", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#ffd700", "🏜️", "Gereifter Tequila", type = DrinkType.SHOT),
        Drink(304, "Vodka Shot", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#f5f5f5", "🧊", "Eisgekühlt", type = DrinkType.SHOT),
        Drink(305, "Sambuca", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#ffffff", "✨", "Anis-Likör mit Bohne", type = DrinkType.SHOT),
        Drink(306, "Berliner Luft", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 18.0, "#e0ffff", "🌬️", "Pfefferminzlikör", type = DrinkType.SHOT),
        Drink(307, "B-52", "Shot", DEFAULT_SHOT_PRICE, listOf("Kaffeelikör", "Baileys", "Grand Marnier"), 25.0, "#8b6508", "✈️", "Schicht-Shot", type = DrinkType.SHOT),
        Drink(308, "Mexikaner", "Shot", DEFAULT_SHOT_PRICE, listOf("Korn", "Tomate", "Tabasco"), 15.0, "#cc0000", "🌶️", "Scharfer Tomaten-Shot", type = DrinkType.SHOT),
        Drink(309, "Kamikaze", "Shot", DEFAULT_SHOT_PRICE, listOf("Vodka", "Triple Sec", "Limette"), 28.0, "#e0ffff", "⚡", "Scharfer Zitrus-Shot", type = DrinkType.SHOT),
        Drink(310, "Erdbeerlimes", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 15.0, "#ff4d4d", "🍓", "Fruchtig & Süß", type = DrinkType.SHOT),
        Drink(311, "Ouzo", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#ffffff", "🇬🇷", "Griechischer Anis", type = DrinkType.SHOT),
        Drink(312, "Grappa", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#f5f5f5", "🇮🇹", "Italienischer Trester", type = DrinkType.SHOT),
        Drink(313, "Absinth", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 60.0, "#00ff00", "🧚", "Die grüne Fee", type = DrinkType.SHOT),
        Drink(314, "Whiskey Shot", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#daa520", "🥃", "Pur & Stark", type = DrinkType.SHOT),
        Drink(315, "Gin Shot", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#f5f5f5", "🍸", "Wacholder Pur", type = DrinkType.SHOT)
    )

    val allDrinks = cocktails + beers + shots
}

package com.djoudinis.barcalculator.data

object DrinkDatabase {
    const val DEFAULT_COCKTAIL_PRICE = 5.00
    const val DEFAULT_BEER_PRICE = 2.80
    const val DEFAULT_SHOT_PRICE = 2.00

    val cocktails = listOf(
        // === KLASSIKER ===
        Drink(1, "Mojito", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Minze", "Limette", "Zucker", "Soda"), 10.0, "#a8e6a3", "🍃", "Kubanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(2, "Margarita", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "Triple Sec", "Limette"), 13.0, "#f0e68c", "🌮", "Mexikanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(3, "Old Fashioned", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Zucker", "Bitter"), 32.0, "#d4760a", "🥃", "Der Ur-Cocktail", type = DrinkType.COCKTAIL),
        Drink(4, "Negroni", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Campari", "Wermut"), 24.0, "#cc3333", "🔴", "Italienischer Bitter-Drink", type = DrinkType.COCKTAIL),
        Drink(5, "Daiquiri", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Limette", "Zucker"), 20.0, "#fffacd", "🍋", "Hemingways Favorit", type = DrinkType.COCKTAIL),
        Drink(6, "Martini", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Wermut", "Olive"), 30.0, "#e8e8e8", "🍸", "Elegant & trocken", type = DrinkType.COCKTAIL),
        Drink(7, "Manhattan", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Whiskey", "Wermut", "Bitter"), 28.0, "#8b4513", "🏙️", "New York Style", type = DrinkType.COCKTAIL),
        Drink(8, "Whiskey Sour", "Sour", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Zitrone", "Eiweiß"), 15.0, "#fff3b0", "🍋", "Süß-sauer Balance", type = DrinkType.COCKTAIL),
        Drink(12, "Caipirinha", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Cachaça", "Limette", "Rohrzucker"), 18.0, "#c5e384", "🇧🇷", "Brasiliens Stolz", type = DrinkType.COCKTAIL),
        Drink(31, "Gin Fizz", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Zitrone", "Zucker", "Soda"), 12.0, "#f5f5dc", "🫧", "Spritziger Gin-Klassiker", type = DrinkType.COCKTAIL),
        Drink(32, "Tom Collins", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Zitrone", "Soda"), 11.0, "#fafad2", "🍋", "Klassischer Highball", type = DrinkType.COCKTAIL),
        Drink(33, "Aviation", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Maraschino", "Zitrone"), 20.0, "#b0c4de", "✈️", "Edler Veilchen-Drink", type = DrinkType.COCKTAIL),
        Drink(34, "Sidecar", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Cognac", "Triple Sec", "Zitrone"), 22.0, "#f4a460", "🏍️", "Eleganter Cognac-Mix", type = DrinkType.COCKTAIL),
        Drink(35, "Gimlet", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Lime Juice"), 25.0, "#c1ffc1", "🍸", "Simpel & Stark", type = DrinkType.COCKTAIL),
        Drink(36, "Bloody Mary", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Tomate", "Gewürze"), 10.0, "#dc143c", "🍅", "Der Kater-Drink", type = DrinkType.COCKTAIL),
        Drink(37, "Moscow Mule", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Ginger Beer", "Limette"), 11.0, "#f5deb3", "🫏", "Im Kupferbecher", type = DrinkType.COCKTAIL),
        Drink(38, "Gin Basil Smash", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Basilikum", "Zitrone"), 16.0, "#3cb371", "🌿", "Moderne Frische", type = DrinkType.COCKTAIL),
        Drink(39, "Espresso Martini", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Espresso", "Kaffeelikör"), 18.0, "#2f1b14", "☕", "Wachmacher", type = DrinkType.COCKTAIL),
        Drink(40, "Pornstar Martini", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Passionsfrucht", "Vanille"), 14.0, "#ffb347", "⭐", "Fruchtiger Bestseller", type = DrinkType.COCKTAIL),

        // === TIKI & TROPISCH ===
        Drink(10, "Mai Tai", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Orangenlikör", "Mandel"), 17.0, "#ff8c00", "🌺", "Tiki-Legende", type = DrinkType.COCKTAIL),
        Drink(11, "Piña Colada", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Kokos", "Ananas"), 12.0, "#fffdd0", "🍍", "Cremiger Traum", type = DrinkType.COCKTAIL),
        Drink(41, "Swimming Pool", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Rum", "Blue Curaçao", "Kokos"), 12.0, "#87ceeb", "🏊", "Karibisches Blau", type = DrinkType.COCKTAIL),
        Drink(42, "Zombie", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("3x Rum", "Grenadine", "Limette"), 22.0, "#9acd32", "🧟", "Vorsicht: Extrem stark!", type = DrinkType.COCKTAIL),
        Drink(43, "Hurricane", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Maracuja", "Zitrone"), 15.0, "#ff4500", "🌀", "New Orleans Favorit", type = DrinkType.COCKTAIL),
        Drink(44, "Painkiller", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Ananas", "Kokos", "Orange"), 14.0, "#ffe4b5", "💊", "Karibische Medizin", type = DrinkType.COCKTAIL),
        Drink(45, "Planter's Punch", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "O-Saft", "Zitrone", "Grenadine"), 13.0, "#ff6b35", "🌴", "Fruchtiger Rum-Punch", type = DrinkType.COCKTAIL),
        Drink(46, "Bahama Mama", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Kokoslikör", "Kaffeelikör", "Ananas"), 14.0, "#ff7f50", "🥥", "Insel-Feeling", type = DrinkType.COCKTAIL),
        Drink(47, "Singapore Sling", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Kirschlikör", "Benedictine", "Ananas"), 12.0, "#ff7f7f", "🇸🇬", "Asiatischer Klassiker", type = DrinkType.COCKTAIL),

        // === LONGDRINKS ===
        Drink(17, "Tequila Sunrise", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "O-Saft", "Grenadine"), 11.0, "#ff4500", "🌅", "Fruchtiger Klassiker", type = DrinkType.COCKTAIL),
        Drink(18, "Sex on the Beach", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Pfirsich", "O-Saft", "Cranberry"), 10.0, "#ff6b6b", "🏖️", "Party-Dauerbrenner", type = DrinkType.COCKTAIL),
        Drink(52, "Cuba Libre", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Cola", "Limette"), 10.0, "#3b1e08", "🇨🇺", "Einfach & Ehrlich", type = DrinkType.COCKTAIL),
        Drink(81, "Long Island Iced Tea", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("5 Spirituosen", "Zitrone", "Cola"), 22.0, "#d2691e", "🏝️", "Der Alleskönner", type = DrinkType.COCKTAIL),
        Drink(60, "Gin Tonic", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Tonic"), 12.0, "#e0ffff", "🫧", "Immer eine gute Wahl", type = DrinkType.COCKTAIL),
        Drink(61, "Vodka Lemon", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Bitter Lemon"), 11.0, "#fffacd", "🍋", "Erfrischend herb", type = DrinkType.COCKTAIL),
        Drink(62, "Whiskey Cola", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Whiskey", "Cola"), 12.0, "#3b1e08", "🥃", "Der Standard", type = DrinkType.COCKTAIL),
        Drink(63, "Horse's Neck", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Ginger Ale", "Bitter"), 11.0, "#daa520", "🐎", "Würzig & Edel", type = DrinkType.COCKTAIL),
        Drink(64, "Dark 'n' Stormy", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Dark Rum", "Ginger Beer"), 12.0, "#654321", "⛈️", "Kräftig & Würzig", type = DrinkType.COCKTAIL),

        // === SPRITZ & APERITIF ===
        Drink(15, "Aperol Spritz", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Aperol", "Prosecco", "Soda"), 8.0, "#ff6347", "🧡", "Italiens Nummer 1", type = DrinkType.COCKTAIL),
        Drink(16, "Hugo", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Holunder", "Prosecco", "Minze"), 6.0, "#98fb98", "🌿", "Leicht & Blumig", type = DrinkType.COCKTAIL),
        Drink(70, "Lillet Wild Berry", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Lillet Blanc", "Wild Berry Soda"), 7.0, "#ffb6c1", "🍓", "Fruchtiger Trend", type = DrinkType.COCKTAIL),
        Drink(71, "Campari Soda", "Aperitif", DEFAULT_COCKTAIL_PRICE, listOf("Campari", "Soda"), 12.0, "#cc0000", "🇮🇹", "Bitter-Klassiker", type = DrinkType.COCKTAIL),
        Drink(72, "Americano", "Aperitif", DEFAULT_COCKTAIL_PRICE, listOf("Campari", "Wermut", "Soda"), 9.0, "#8b0000", "🕶️", "Pre-Dinner Drink", type = DrinkType.COCKTAIL),

        // === MOCKTAILS (ALKOHOLFREI) ===
        Drink(91, "Virgin Mojito", "Mocktail", DEFAULT_COCKTAIL_PRICE, listOf("Minze", "Limette", "Zucker", "Soda"), 0.0, "#a8e6cf", "🍃", "Frische ohne Alkohol", type = DrinkType.COCKTAIL),
        Drink(92, "Ipanema", "Mocktail", DEFAULT_COCKTAIL_PRICE, listOf("Limette", "Maracuja", "Ginger Ale"), 0.0, "#fdd835", "🏖️", "Fruchtiger Mocktail", type = DrinkType.COCKTAIL),
        Drink(93, "Coconut Kiss", "Mocktail", DEFAULT_COCKTAIL_PRICE, listOf("Ananas", "Kokos", "Sahne", "Grenadine"), 0.0, "#ffc0cb", "🥥", "Süß & Cremig", type = DrinkType.COCKTAIL),
        Drink(94, "Sportsman", "Mocktail", DEFAULT_COCKTAIL_PRICE, listOf("Ananas", "Zitrone", "Grapefruit", "Grenadine"), 0.0, "#ffa500", "🏃", "Vitalisierend", type = DrinkType.COCKTAIL)
    )

    val beers = listOf(
        // === DEUTSCHE BIERE ===
        Drink(201, "Pilsener", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f4c430", "🍺", "Klassisch & Herb", type = DrinkType.BEER),
        Drink(202, "Helles", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.1, "#f5d442", "☀️", "Mild & Süffig", type = DrinkType.BEER),
        Drink(203, "Export", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.4, "#f4c430", "🏭", "Kräftiges Lager", type = DrinkType.BEER),
        Drink(204, "Weizen / Weißbier", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.3, "#ffcc00", "🌾", "Bayrische Tradition", type = DrinkType.BEER),
        Drink(205, "Dunkelweizen", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.4, "#8b4513", "🍂", "Malzig & Vollmundig", type = DrinkType.BEER),
        Drink(206, "Kristallweizen", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.2, "#fffacd", "💎", "Klares Weizen", type = DrinkType.BEER),
        Drink(207, "Kölsch", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f5e642", "⛪", "Kölner Spezialität", type = DrinkType.BEER),
        Drink(208, "Altbier", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#8b4513", "🏰", "Düsseldorfer Art", type = DrinkType.BEER),
        Drink(209, "Schwarzbier", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.9, "#1a1a1a", "🌑", "Dunkle Röstnoten", type = DrinkType.BEER),
        Drink(210, "Kellerbier / Zwickl", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.2, "#daa520", "🏺", "Naturtrüb & Frisch", type = DrinkType.BEER),
        Drink(211, "Bockbier", "Bier", DEFAULT_BEER_PRICE, emptyList(), 6.5, "#cc8400", "🐐", "Stark & Malzig", type = DrinkType.BEER),
        Drink(212, "Doppelbock", "Bier", DEFAULT_BEER_PRICE, emptyList(), 7.5, "#5c3317", "💪", "Besonders kräftig", type = DrinkType.BEER),
        Drink(213, "Rauchbier", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.1, "#654321", "🔥", "Bamberger Spezialität", type = DrinkType.BEER),
        Drink(214, "Berliner Weisse", "Bier", DEFAULT_BEER_PRICE, emptyList(), 3.0, "#fffacd", "🧸", "Erfrischend Sauer", type = DrinkType.BEER),
        
        // === INTERNATIONALE BIERE ===
        Drink(250, "Stout (Guinness Style)", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.2, "#000000", "🇮🇪", "Tiefschwarz & Cremig", type = DrinkType.BEER),
        Drink(251, "Pale Ale", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.2, "#ffd700", "🇬🇧", "Hopfig & Fruchtig", type = DrinkType.BEER),
        Drink(252, "IPA (India Pale Ale)", "Bier", DEFAULT_BEER_PRICE, emptyList(), 6.5, "#ffb300", "🚀", "Extreme Hopfen-Power", type = DrinkType.BEER),
        Drink(253, "Lager (International)", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.0, "#f5d442", "🌏", "Leicht & Spritzig", type = DrinkType.BEER),
        Drink(254, "Witbier (Belgisch)", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#fffacd", "🇧🇪", "Mit Koriander & Orange", type = DrinkType.BEER),
        Drink(255, "Red Ale", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.5, "#a52a2a", "🏮", "Bernsteinfarben", type = DrinkType.BEER),
        
        // === MIX-BIERE & ALKOHOLFREI ===
        Drink(280, "Radler", "Bier", DEFAULT_BEER_PRICE, emptyList(), 2.5, "#fff44f", "🍋", "Bier mit Limo", type = DrinkType.BEER),
        Drink(281, "Alsterwasser", "Bier", DEFAULT_BEER_PRICE, emptyList(), 2.5, "#fff44f", "⛵", "Norddt. Radler", type = DrinkType.BEER),
        Drink(282, "Cola-Bier", "Bier", DEFAULT_BEER_PRICE, emptyList(), 2.5, "#3b1e08", "🥤", "Mix mit Cola", type = DrinkType.BEER),
        Drink(283, "Pils Alkoholfrei", "Bier", DEFAULT_BEER_PRICE, emptyList(), 0.5, "#ffcc80", "🚫", "Voller Pilsgeschmack", type = DrinkType.BEER),
        Drink(284, "Weizen Alkoholfrei", "Bier", DEFAULT_BEER_PRICE, emptyList(), 0.5, "#ffcc80", "🌾", "Isotonischer Durstlöscher", type = DrinkType.BEER)
    )

    val shots = listOf(
        // === KLASSISCHE SHOTS ===
        Drink(301, "Jägermeister", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 35.0, "#2f4f4f", "🦌", "Kräuterlikör", type = DrinkType.SHOT),
        Drink(302, "Tequila Silver", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#f5f5f5", "🌵", "Mit Salz & Zitrone", type = DrinkType.SHOT),
        Drink(303, "Tequila Gold", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#ffd700", "🏜️", "Mit Zimt & Orange", type = DrinkType.SHOT),
        Drink(304, "Vodka Shot", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#f5f5f5", "🧊", "Eisgekühlt", type = DrinkType.SHOT),
        Drink(305, "Sambuca", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#ffffff", "✨", "Mit Kaffeebohne", type = DrinkType.SHOT),
        Drink(306, "Ouzo", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#ffffff", "🇬🇷", "Anis-Spezialität", type = DrinkType.SHOT),
        Drink(307, "Grappa", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#f5f5f5", "🇮🇹", "Edler Tresterbrand", type = DrinkType.SHOT),
        Drink(308, "Whiskey Shot", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#daa520", "🥃", "Pur & Kräftig", type = DrinkType.SHOT),
        Drink(309, "Rum Shot", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#8b4513", "🏴‍☠️", "Kräftiges Aroma", type = DrinkType.SHOT),
        Drink(310, "Absinth", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 60.0, "#00ff00", "🧚", "Die grüne Fee", type = DrinkType.SHOT),

        // === MIX-SHOTS ===
        Drink(311, "B-52", "Shot", DEFAULT_SHOT_PRICE, listOf("Kaffeelikör", "Baileys", "Grand Marnier"), 25.0, "#8b6508", "✈️", "Schicht-Klassiker", type = DrinkType.SHOT),
        Drink(312, "Mexikaner", "Shot", DEFAULT_SHOT_PRICE, listOf("Korn", "Tomate", "Tabasco"), 15.0, "#cc0000", "🌶️", "Scharf & Würzig", type = DrinkType.SHOT),
        Drink(313, "Kamikaze", "Shot", DEFAULT_SHOT_PRICE, listOf("Vodka", "Triple Sec", "Limette"), 28.0, "#e0ffff", "⚡", "Saurer Kick", type = DrinkType.SHOT),
        Drink(314, "Orgasmus", "Shot", DEFAULT_SHOT_PRICE, listOf("Sambuca", "Baileys"), 25.0, "#f5deb3", "🔥", "Süß & Cremig", type = DrinkType.SHOT),
        Drink(315, "Flatliner", "Shot", DEFAULT_SHOT_PRICE, listOf("Sambuca", "Tequila", "Tabasco"), 35.0, "#ffffff", "📏", "Nichts für Schwache", type = DrinkType.SHOT),
        Drink(316, "Lemon Drop", "Shot", DEFAULT_SHOT_PRICE, listOf("Vodka", "Zitrone", "Zucker"), 22.0, "#fff44f", "🍋", "Süß-Sauer", type = DrinkType.SHOT),
        Drink(317, "Slippery Nipple", "Shot", DEFAULT_SHOT_PRICE, listOf("Sambuca", "Baileys", "Grenadine"), 25.0, "#ffc0cb", "🍒", "Cremiger Mix", type = DrinkType.SHOT),

        // === LIKÖRE & SPEZIAL ===
        Drink(318, "Berliner Luft", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 18.0, "#e0ffff", "🌬️", "Pfefferminz-Kult", type = DrinkType.SHOT),
        Drink(319, "Erdbeerlimes", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 15.0, "#ff4d4d", "🍓", "Fruchtige Süße", type = DrinkType.SHOT),
        Drink(320, "Flimm", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 17.0, "#32cd32", "🍏", "Waldmeister-Kult", type = DrinkType.SHOT),
        Drink(321, "Ramazzotti", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 30.0, "#4b0082", "🇮🇹", "Kräuter auf Eis", type = DrinkType.SHOT),
        Drink(322, "Baileys", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 17.0, "#d2b48c", "🍦", "Sahne-Likör", type = DrinkType.SHOT)
    )

    val allDrinks = cocktails + beers + shots
}

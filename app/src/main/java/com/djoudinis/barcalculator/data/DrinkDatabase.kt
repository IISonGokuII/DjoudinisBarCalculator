package com.djoudinis.barcalculator.data

object DrinkDatabase {
    const val DEFAULT_COCKTAIL_PRICE = 5.00
    const val DEFAULT_BEER_PRICE = 2.80
    const val DEFAULT_SHOT_PRICE = 2.00

    val cocktails = listOf(
        // === KLASSIKER & UNVERZICHTBAR ===
        Drink(1, "Mojito", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Minze", "Limette", "Zucker", "Soda"), 10.0, "#a8e6a3", "🍃", "Kubanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(2, "Margarita", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "Triple Sec", "Limette"), 13.0, "#f0e68c", "🌮", "Mexikanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(3, "Old Fashioned", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Zucker", "Bitter"), 32.0, "#d4760a", "🥃", "Der Ur-Cocktail", type = DrinkType.COCKTAIL),
        Drink(4, "Negroni", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Campari", "Wermut"), 24.0, "#cc3333", "🔴", "Italienischer Bitter-Drink", type = DrinkType.COCKTAIL),
        Drink(5, "Daiquiri", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Limette", "Zucker"), 20.0, "#fffacd", "🍋", "Hemingways Favorit", type = DrinkType.COCKTAIL),
        Drink(6, "Martini", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Wermut", "Olive"), 30.0, "#e8e8e8", "🍸", "Elegant & trocken", type = DrinkType.COCKTAIL),
        Drink(7, "Manhattan", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Whiskey", "Wermut", "Bitter"), 28.0, "#8b4513", "🏙️", "New York Style", type = DrinkType.COCKTAIL),
        Drink(8, "Whiskey Sour", "Sour", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Zitrone", "Eiweiß"), 15.0, "#fff3b0", "🍋", "Süß-sauer Balance", type = DrinkType.COCKTAIL),
        Drink(9, "Cosmopolitan", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka Citron", "Triple Sec", "Cranberry"), 18.0, "#ff69b4", "💗", "Pinker Klassiker", type = DrinkType.COCKTAIL),
        Drink(10, "Moscow Mule", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Ginger Beer", "Limette"), 11.0, "#f5deb3", "🫏", "Im Kupferbecher", type = DrinkType.COCKTAIL),

        // === TROPISCH & EXOTISCH ===
        Drink(11, "Piña Colada", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Kokos", "Ananas"), 12.0, "#fffdd0", "🍍", "Cremiger Traum", type = DrinkType.COCKTAIL),
        Drink(12, "Mai Tai", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Orangenlikör", "Mandel"), 17.0, "#ff8c00", "🌺", "Tiki-Legende", type = DrinkType.COCKTAIL),
        Drink(13, "Zombie", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("3x Rum", "Grenadine", "Limette"), 22.0, "#9acd32", "🧟", "Vorsicht: Extrem stark!", type = DrinkType.COCKTAIL),
        Drink(14, "Swimming Pool", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Rum", "Blue Curaçao", "Kokos"), 12.0, "#87ceeb", "🏊", "Karibisches Blau", type = DrinkType.COCKTAIL),
        Drink(15, "Hurricane", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Maracuja", "Zitrone"), 15.0, "#ff4500", "🌀", "New Orleans Favorit", type = DrinkType.COCKTAIL),
        Drink(16, "Planter's Punch", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "O-Saft", "Grenadine"), 13.0, "#ff6b35", "🌴", "Fruchtiger Punch", type = DrinkType.COCKTAIL),
        Drink(17, "Bahama Mama", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Kokoslikör", "Kaffeelikör"), 14.0, "#ff7f50", "🥥", "Insel-Feeling", type = DrinkType.COCKTAIL),
        Drink(18, "Singapore Sling", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Kirschlikör", "Ananas"), 12.0, "#ff7f7f", "🇸🇬", "Asiatischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(19, "Sex on the Beach", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Pfirsich", "O-Saft"), 10.0, "#ff6b6b", "🏖️", "Party-Dauerbrenner", type = DrinkType.COCKTAIL),
        Drink(20, "Tequila Sunrise", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "O-Saft", "Grenadine"), 11.0, "#ff4500", "🌅", "Urlaubsfeeling", type = DrinkType.COCKTAIL),

        // === GIN SPECIALS ===
        Drink(21, "Gin Tonic", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Tonic Water"), 12.0, "#e0ffff", "🫧", "Der Zeitlose", type = DrinkType.COCKTAIL),
        Drink(22, "Gin Basil Smash", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Basilikum", "Zitrone"), 16.0, "#3cb371", "🌿", "Grüne Frische", type = DrinkType.COCKTAIL),
        Drink(23, "Bramble", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Zitrone", "Brombeere"), 15.0, "#800080", "🫐", "Fruchtiger Gin-Mix", type = DrinkType.COCKTAIL),
        Drink(24, "Aviation", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Maraschino", "Zitrone"), 20.0, "#b0c4de", "✈️", "Edler Veilchen-Drink", type = DrinkType.COCKTAIL),
        Drink(25, "Gimlet", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Lime Juice"), 25.0, "#c1ffc1", "🍸", "Simpel & Stark", type = DrinkType.COCKTAIL),
        Drink(26, "Tom Collins", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Zitrone", "Soda"), 11.0, "#fafad2", "🍋", "Klassischer Highball", type = DrinkType.COCKTAIL),
        Drink(27, "French 75", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Champagner", "Zitrone"), 14.0, "#fffacd", "🥂", "Prickelnd & Stark", type = DrinkType.COCKTAIL),
        Drink(28, "Clover Club", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Himbeere", "Zitrone"), 16.0, "#db7093", "🍀", "Rosa Finesse", type = DrinkType.COCKTAIL),
        Drink(29, "White Lady", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Triple Sec", "Zitrone"), 20.0, "#ffffff", "👰", "Säuerlich-Elegant", type = DrinkType.COCKTAIL),
        Drink(30, "The Last Word", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Chartreuse", "Zitrone"), 22.0, "#7fff00", "🤐", "Grüne Legende", type = DrinkType.COCKTAIL),

        // === VODKA & WHISKEY ===
        Drink(31, "Espresso Martini", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Espresso", "Kaffeelikör"), 18.0, "#2f1b14", "☕", "Wachmacher", type = DrinkType.COCKTAIL),
        Drink(32, "Pornstar Martini", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Passionsfrucht", "Vanille"), 14.0, "#ffb347", "⭐", "Fruchtiger Bestseller", type = DrinkType.COCKTAIL),
        Drink(33, "White Russian", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Kaffeelikör", "Sahne"), 15.0, "#f5deb3", "🥛", "The Dude Style", type = DrinkType.COCKTAIL),
        Drink(34, "Black Russian", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Kaffeelikör"), 22.0, "#1a1a2e", "🖤", "Stark & Pur", type = DrinkType.COCKTAIL),
        Drink(35, "Bloody Mary", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Tomate", "Gewürze"), 10.0, "#dc143c", "🍅", "Der Kater-Drink", type = DrinkType.COCKTAIL),
        Drink(36, "Moscow Mule", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Ginger Beer", "Limette"), 11.0, "#f5deb3", "🫏", "Im Kupferbecher", type = DrinkType.COCKTAIL),
        Drink(37, "Sazerac", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Rye Whiskey", "Absinth", "Bitter"), 30.0, "#d4a574", "⚜️", "New Orleans Tradition", type = DrinkType.COCKTAIL),
        Drink(38, "Boulevardier", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Campari", "Wermut"), 25.0, "#c41e3a", "🎩", "Negroni mit Whiskey", type = DrinkType.COCKTAIL),
        Drink(39, "Penicillin", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Scotch", "Zitrone", "Ingwer", "Honig"), 20.0, "#daa520", "💊", "Rauchig & Heilsam", type = DrinkType.COCKTAIL),
        Drink(40, "Rusty Nail", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Scotch", "Drambuie"), 30.0, "#cd853f", "🔩", "Whiskey-Süße", type = DrinkType.COCKTAIL),

        // === LIFESTYLE & SPRITZ ===
        Drink(41, "Aperol Spritz", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Aperol", "Prosecco", "Soda"), 8.0, "#ff6347", "🧡", "Italiens Sommer", type = DrinkType.COCKTAIL),
        Drink(42, "Hugo", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Holunder", "Prosecco", "Minze"), 6.0, "#98fb98", "🌿", "Leicht & Blumig", type = DrinkType.COCKTAIL),
        Drink(43, "Lillet Wild Berry", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Lillet Blanc", "Wild Berry Soda"), 7.0, "#ffb6c1", "🍓", "Fruchtiger Trend", type = DrinkType.COCKTAIL),
        Drink(44, "Campari Soda", "Aperitif", DEFAULT_COCKTAIL_PRICE, listOf("Campari", "Soda"), 12.0, "#cc0000", "🇮🇹", "Bitter-Klassiker", type = DrinkType.COCKTAIL),
        Drink(45, "Americano", "Aperitif", DEFAULT_COCKTAIL_PRICE, listOf("Campari", "Wermut", "Soda"), 9.0, "#8b0000", "🕶️", "Leichter Genuss", type = DrinkType.COCKTAIL),
        Drink(46, "Caipirinha", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Cachaça", "Limette", "Zucker"), 18.0, "#c5e384", "🇧🇷", "Brasiliens Stolz", type = DrinkType.COCKTAIL),
        Drink(47, "Paloma", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "Grapefruit", "Limette"), 12.0, "#ffb6c1", "🕊️", "Mexikos Favorit", type = DrinkType.COCKTAIL),
        Drink(48, "Cuba Libre", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Cola", "Limette"), 10.0, "#3b1e08", "🇨🇺", "Freiheit im Glas", type = DrinkType.COCKTAIL),
        Drink(49, "Long Island Iced Tea", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("5 Spirituosen", "Zitrone", "Cola"), 22.0, "#d2691e", "🏝️", "Der Alleskönner", type = DrinkType.COCKTAIL),
        Drink(50, "Pisco Sour", "Sour", DEFAULT_COCKTAIL_PRICE, listOf("Pisco", "Zitrone", "Eiweiß"), 16.0, "#fdf5e6", "🇵🇪", "Perus Nationaldrink", type = DrinkType.COCKTAIL)
    )

    val beers = listOf(
        // === MARKEN BIERE ===
        Drink(201, "Krombacher Pils", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f4c430", "🍺", "Eine Perle der Natur", type = DrinkType.BEER),
        Drink(202, "Bitburger Premium", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f4c430", "🍺", "Bitte ein Bit", type = DrinkType.BEER),
        Drink(203, "Warsteiner", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f5d442", "🍺", "Das einzig Wahre", type = DrinkType.BEER),
        Drink(204, "Veltins", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f0c300", "🍺", "Aus dem Sauerland", type = DrinkType.BEER),
        Drink(205, "Becks Pils", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.9, "#e8c300", "🍺", "Bremer Braukunst", type = DrinkType.BEER),
        Drink(206, "Jever Pils", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.9, "#e8c300", "🍺", "Friesisch herb", type = DrinkType.BEER),
        Drink(207, "Augustiner Helles", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.2, "#f5d442", "☀️", "Münchner Kult-Bier", type = DrinkType.BEER),
        Drink(208, "Tegernseer Hell", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f4c430", "☀️", "Brauhaus Kult", type = DrinkType.BEER),
        Drink(209, "Paulaner Weizen", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.5, "#ffcc00", "🌾", "Münchner Weizen", type = DrinkType.BEER),
        Drink(210, "Erdinger Weizen", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.3, "#ffcc00", "🌾", "Weltberühmtes Weizen", type = DrinkType.BEER),
        Drink(211, "Astra Urtyp", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.9, "#f4c430", "⚓", "Hamburger Kiez-Kult", type = DrinkType.BEER),
        Drink(212, "Rothaus Tannenzäpfle", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.1, "#f0c300", "🌲", "Schwarzwald-Kult", type = DrinkType.BEER),
        Drink(213, "Heineken", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.0, "#00ff00", "🇳🇱", "Internationales Lager", type = DrinkType.BEER),
        Drink(214, "Corona Extra", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.5, "#fffacd", "🇲🇽", "Mit Limette", type = DrinkType.BEER),
        Drink(215, "Guinness Stout", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.2, "#000000", "🇮🇪", "Irischer Klassiker", type = DrinkType.BEER),
        Drink(216, "Desperados", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.9, "#ffff00", "🌵", "Lager mit Tequila", type = DrinkType.BEER),
        Drink(217, "Flensburger Pils", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f4c430", "🍾", "Plop! Das Original", type = DrinkType.BEER),
        Drink(218, "Früh Kölsch", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f5e642", "⛪", "Kölner Kult", type = DrinkType.BEER),
        Drink(219, "Gaffel Kölsch", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f5e642", "⛪", "Echt Kölsch", type = DrinkType.BEER),
        Drink(220, "Gösser Radler", "Bier", DEFAULT_BEER_PRICE, emptyList(), 2.0, "#fff44f", "🍋", "Der Radler-König", type = DrinkType.BEER)
    )

    val shots = listOf(
        // === BRAND SHOTS ===
        Drink(301, "Jägermeister", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 35.0, "#2f4f4f", "🦌", "Eiskalter Kräuter", type = DrinkType.SHOT),
        Drink(302, "Jack Daniel's", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#000000", "🥃", "Tennessee Whiskey", type = DrinkType.SHOT),
        Drink(303, "Jameson Irish", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#013220", "☘️", "Milder Ire", type = DrinkType.SHOT),
        Drink(304, "Jim Beam", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#ffffff", "🤠", "Bourbon Whiskey", type = DrinkType.SHOT),
        Drink(305, "Absolut Vodka", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#00a1e1", "🧊", "Schwedischer Vodka", type = DrinkType.SHOT),
        Drink(306, "Grey Goose", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#ffffff", "🦢", "Premium Vodka", type = DrinkType.SHOT),
        Drink(307, "Hendrick's Gin", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 44.0, "#000000", "🥒", "Premium Gin", type = DrinkType.SHOT),
        Drink(308, "Tanqueray Gin", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 47.0, "#004b23", "🍸", "London Dry Gin", type = DrinkType.SHOT),
        Drink(309, "Havana Club 3", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#ffff00", "🇨🇺", "Kubanischer Rum", type = DrinkType.SHOT),
        Drink(310, "Bacardi Carta Blanca", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 37.5, "#ffffff", "🦇", "Weißer Rum", type = DrinkType.SHOT),
        Drink(311, "Tequila Silver (Brand)", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#f5f5f5", "🌵", "Klarer Tequila", type = DrinkType.SHOT),
        Drink(312, "Tequila Gold (Brand)", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#ffd700", "🏜️", "Gereifter Tequila", type = DrinkType.SHOT),
        
        // === MIX-SHOTS ===
        Drink(313, "B-52", "Shot", DEFAULT_SHOT_PRICE, listOf("Kaffeelikör", "Baileys", "Grand Marnier"), 25.0, "#8b6508", "✈️", "Schicht-Klassiker", type = DrinkType.SHOT),
        Drink(314, "Mexikaner", "Shot", DEFAULT_SHOT_PRICE, listOf("Korn", "Tomate", "Tabasco"), 15.0, "#cc0000", "🌶️", "Scharf & Würzig", type = DrinkType.SHOT),
        Drink(315, "Kamikaze", "Shot", DEFAULT_SHOT_PRICE, listOf("Vodka", "Triple Sec", "Limette"), 28.0, "#e0ffff", "⚡", "Saurer Kick", type = DrinkType.SHOT),
        Drink(316, "Berliner Luft", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 18.0, "#e0ffff", "🌬️", "Pfefferminz-Kult", type = DrinkType.SHOT),
        Drink(317, "Flimm", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 17.0, "#32cd32", "🍏", "Waldmeister-Kult", type = DrinkType.SHOT),
        Drink(318, "Slippery Nipple", "Shot", DEFAULT_SHOT_PRICE, listOf("Sambuca", "Baileys"), 25.0, "#ffc0cb", "🍒", "Cremiger Mix", type = DrinkType.SHOT),
        Drink(319, "Lemon Drop", "Shot", DEFAULT_SHOT_PRICE, listOf("Vodka", "Zitrone"), 22.0, "#fff44f", "🍋", "Süß-Sauer", type = DrinkType.SHOT),
        Drink(320, "Ouzo 12", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#ffffff", "🇬🇷", "Griechischer Klassiker", type = DrinkType.SHOT)
    )

    val allDrinks = cocktails + beers + shots
}

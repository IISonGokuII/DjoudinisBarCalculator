package com.djoudinis.barcalculator.data

object DrinkDatabase {
    const val DEFAULT_COCKTAIL_PRICE = 5.00
    const val DEFAULT_BEER_PRICE = 2.80
    const val DEFAULT_SHOT_PRICE = 2.00

    val cocktails = listOf(
        // === KLASSIKER ===
        Drink(1, "Mojito", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Minze", "Limette"), 10.0, "#a8e6a3", "🍃", "Kubanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(2, "Margarita", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "Triple Sec", "Limette"), 13.0, "#f0e68c", "🌮", "Mexikanischer Klassiker", type = DrinkType.COCKTAIL),
        Drink(3, "Old Fashioned", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Zucker", "Bitter"), 32.0, "#d4760a", "🥃", "Der Ur-Cocktail", type = DrinkType.COCKTAIL),
        Drink(4, "Negroni", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Campari", "Wermut"), 24.0, "#cc3333", "🔴", "Italienischer Bitter-Drink", type = DrinkType.COCKTAIL),
        Drink(5, "Daiquiri", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Limette", "Zucker"), 20.0, "#fffacd", "🍋", "Hemingways Favorit", type = DrinkType.COCKTAIL),
        Drink(6, "Martini", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Wermut", "Olive"), 30.0, "#e8e8e8", "🍸", "Elegant & trocken", type = DrinkType.COCKTAIL),
         Drink(7, "Manhattan", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Whiskey", "Wermut", "Bitter"), 28.0, "#8b4513", "🏙️", "New York Style", type = DrinkType.COCKTAIL),
        Drink(8, "Whiskey Sour", "Sour", DEFAULT_COCKTAIL_PRICE, listOf("Bourbon", "Zitrone", "Eiweiß"), 15.0, "#fff3b0", "🍋", "Süß-sauer Balance", type = DrinkType.COCKTAIL),
        Drink(12, "Caipirinha", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Cachaça", "Limette", "Rohrzucker"), 18.0, "#c5e384", "🇧🇷", "Brasiliens Stolz", type = DrinkType.COCKTAIL),
        Drink(31, "Gin Fizz", "Klassiker", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Zitrone", "Zucker", "Soda"), 12.0, "#f5f5dc", "🫧", "Spritziger Gin-Klassiker", type = DrinkType.COCKTAIL),
        Drink(38, "Gin Basil Smash", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Basilikum", "Zitrone"), 16.0, "#3cb371", "🌿", "Moderne Frische", type = DrinkType.COCKTAIL),
        Drink(39, "Espresso Martini", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Espresso", "Kaffeelikör"), 18.0, "#2f1b14", "☕", "Wachmacher", type = DrinkType.COCKTAIL),
        Drink(40, "Pornstar Martini", "Modern", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Passionsfrucht", "Vanille"), 14.0, "#ffb347", "⭐", "Fruchtiger Bestseller", type = DrinkType.COCKTAIL),

        // === TIKI & TROPISCH ===
        Drink(10, "Mai Tai", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Orangenlikör", "Mandel"), 17.0, "#ff8c00", "🌺", "Tiki-Legende", type = DrinkType.COCKTAIL),
        Drink(11, "Piña Colada", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Kokos", "Ananas"), 12.0, "#fffdd0", "🍍", "Cremiger Traum", type = DrinkType.COCKTAIL),
        Drink(42, "Zombie", "Tiki", DEFAULT_COCKTAIL_PRICE, listOf("3x Rum", "Grenadine", "Limette"), 22.0, "#9acd32", "🧟", "Vorsicht: Extrem stark!", type = DrinkType.COCKTAIL),

        // === LONGDRINKS ===
        Drink(17, "Tequila Sunrise", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Tequila", "O-Saft", "Grenadine"), 11.0, "#ff4500", "🌅", "Fruchtiger Klassiker", type = DrinkType.COCKTAIL),
        Drink(18, "Sex on the Beach", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Vodka", "Pfirsich", "O-Saft", "Cranberry"), 10.0, "#ff6b6b", "🏖️", "Party-Dauerbrenner", type = DrinkType.COCKTAIL),
        Drink(52, "Cuba Libre", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Rum", "Cola", "Limette"), 10.0, "#3b1e08", "🇨🇺", "Einfach & Ehrlich", type = DrinkType.COCKTAIL),
        Drink(81, "Long Island Iced Tea", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("5 Spirituosen", "Zitrone", "Cola"), 22.0, "#d2691e", "🏝️", "Der Alleskönner", type = DrinkType.COCKTAIL),
        Drink(60, "Gin Tonic", "Longdrink", DEFAULT_COCKTAIL_PRICE, listOf("Gin", "Tonic"), 12.0, "#e0ffff", "🫧", "Immer eine gute Wahl", type = DrinkType.COCKTAIL),

        // === SPRITZ & APERITIF ===
        Drink(15, "Aperol Spritz", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Aperol", "Prosecco", "Soda"), 8.0, "#ff6347", "🧡", "Italiens Nummer 1", type = DrinkType.COCKTAIL),
        Drink(16, "Hugo", "Spritz", DEFAULT_COCKTAIL_PRICE, listOf("Holunder", "Prosecco", "Minze"), 6.0, "#98fb98", "🌿", "Leicht & Blumig", type = DrinkType.COCKTAIL)
    )

    val beers = listOf(
        // === TV-BIERE (DEUTSCHLAND) ===
        Drink(201, "Krombacher Pils", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f4c430", "🍺", "Eine Perle der Natur", type = DrinkType.BEER),
        Drink(202, "Bitburger Premium Pils", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f4c430", "🍺", "Bitte ein Bit", type = DrinkType.BEER),
        Drink(203, "Warsteiner Premium Verum", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f5d442", "🍺", "Das einzig Wahre", type = DrinkType.BEER),
        Drink(204, "Veltins Pilsener", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f0c300", "🍺", "Aus dem Sauerland", type = DrinkType.BEER),
        Drink(205, "Becks Pils", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.9, "#e8c300", "🍺", "Bremer Braukunst", type = DrinkType.BEER),
        Drink(206, "Jever Pilsener", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.9, "#e8c300", "🍺", "Friesisch herb", type = DrinkType.BEER),
        Drink(207, "Hasseröder Pils", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.9, "#f0c300", "🍺", "Männer sind so", type = DrinkType.BEER),
        Drink(208, "König Pilsener", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.9, "#f5d442", "🍺", "Das König unter den Bieren", type = DrinkType.BEER),
        
        // === BAYERISCHE KULT-MARKEN ===
        Drink(210, "Augustiner Helles", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.2, "#f5d442", "☀️", "Münchner Kult-Bier", type = DrinkType.BEER),
        Drink(211, "Tegernseer Hell", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f4c430", "☀️", "Vom Herzoglichen Brauhaus", type = DrinkType.BEER),
        Drink(212, "Paulaner Hefe-Weißbier", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.5, "#ffcc00", "🌾", "Münchner Traditions-Weizen", type = DrinkType.BEER),
        Drink(213, "Erdinger Weißbier", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.3, "#ffcc00", "🌾", "Weltberühmtes Weizen", type = DrinkType.BEER),
        Drink(214, "Hofbräu Original", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.1, "#f5d442", "🦁", "Aus dem Hofbräuhaus", type = DrinkType.BEER),
        Drink(215, "Bayreuther Hell", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.9, "#f5d442", "☀️", "Ehrliches bayerisches Bier", type = DrinkType.BEER),

        // === WEITERE DEUTSCHE MARKEN ===
        Drink(220, "Astra Urtyp", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.9, "#f4c430", "⚓", "Hamburger Kiez-Kult", type = DrinkType.BEER),
        Drink(221, "Rothaus Tannenzäpfle", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.1, "#f0c300", "🌲", "Schwarzwald-Kult", type = DrinkType.BEER),
        Drink(222, "Flensburger Pilsener", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f4c430", "🍾", "Plop! Das Original", type = DrinkType.BEER),
        Drink(223, "Früh Kölsch", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f5e642", "⛪", "Kölner Traditionskölsch", type = DrinkType.BEER),
        Drink(224, "Gaffel Kölsch", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#f5e642", "⛪", "Die Kölner Erfrischung", type = DrinkType.BEER),
        Drink(225, "Füchschen Alt", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.8, "#8b4513", "🦊", "Düsseldorfer Altbier-Kult", type = DrinkType.BEER),

        // === INTERNATIONALE STARS ===
        Drink(250, "Heineken", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.0, "#00ff00", "🇳🇱", "Premium Lager aus Holland", type = DrinkType.BEER),
        Drink(251, "Corona Extra", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.5, "#fffacd", "🇲🇽", "Mit Limette genießen", type = DrinkType.BEER),
        Drink(252, "Desperados", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.9, "#ffff00", "🌵", "Lager mit Tequila-Flavour", type = DrinkType.BEER),
        Drink(253, "Budweiser (CZE)", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.0, "#daa520", "🇨🇿", "Das Original aus Budweis", type = DrinkType.BEER),
        Drink(254, "Guinness Stout", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.2, "#000000", "🇮🇪", "Irischer Klassiker", type = DrinkType.BEER),
        Drink(255, "Pilsner Urquell", "Bier", DEFAULT_BEER_PRICE, emptyList(), 4.4, "#daa520", "🇨🇿", "Das erste Pils der Welt", type = DrinkType.BEER),
        Drink(256, "Stella Artois", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.0, "#fffacd", "🇧🇪", "Belgisches Premium-Lager", type = DrinkType.BEER),
        Drink(257, "San Miguel", "Bier", DEFAULT_BEER_PRICE, emptyList(), 5.0, "#daa520", "🇪🇸", "Spanisches Lebensgefühl", type = DrinkType.BEER),

        // === MIX-BIERE ===
        Drink(280, "Schöfferhofer Grapefruit", "Bier", DEFAULT_BEER_PRICE, emptyList(), 2.5, "#ff7f50", "🍊", "Weizen-Mix Kult", type = DrinkType.BEER),
        Drink(281, "Gösser Naturradler", "Bier", DEFAULT_BEER_PRICE, emptyList(), 2.0, "#fff44f", "🍋", "Der Radler-König", type = DrinkType.BEER),
        Drink(282, "Cola-Bier / Diesel", "Bier", DEFAULT_BEER_PRICE, emptyList(), 2.5, "#3b1e08", "🥤", "Klassischer Mix", type = DrinkType.BEER)
    )

    val shots = listOf(
        Drink(301, "Jägermeister", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 35.0, "#2f4f4f", "🦌", "Kräuterlikör", type = DrinkType.SHOT),
        Drink(302, "Tequila Silver", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#f5f5f5", "🌵", "Mit Salz & Zitrone", type = DrinkType.SHOT),
        Drink(303, "Tequila Gold", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#ffd700", "🏜️", "Mit Zimt & Orange", type = DrinkType.SHOT),
        Drink(304, "Vodka Shot", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 40.0, "#f5f5f5", "🧊", "Eisgekühlt", type = DrinkType.SHOT),
        Drink(305, "Sambuca", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 38.0, "#ffffff", "✨", "Mit Kaffeebohne", type = DrinkType.SHOT),
        Drink(306, "Berliner Luft", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 18.0, "#e0ffff", "🌬️", "Pfefferminz-Kult", type = DrinkType.SHOT),
        Drink(311, "B-52", "Shot", DEFAULT_SHOT_PRICE, listOf("Kaffeelikör", "Baileys", "Grand Marnier"), 25.0, "#8b6508", "✈️", "Schicht-Klassiker", type = DrinkType.SHOT),
        Drink(312, "Mexikaner", "Shot", DEFAULT_SHOT_PRICE, listOf("Korn", "Tomate", "Tabasco"), 15.0, "#cc0000", "🌶️", "Scharfer Tomaten-Shot", type = DrinkType.SHOT),
        Drink(318, "Flimm", "Shot", DEFAULT_SHOT_PRICE, emptyList(), 17.0, "#32cd32", "🍏", "Waldmeister-Kult", type = DrinkType.SHOT)
    )

    val allDrinks = cocktails + beers + shots
}

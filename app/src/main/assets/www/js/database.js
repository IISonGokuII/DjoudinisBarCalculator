// ============================================================
// DJOUDINI'S BAR CALCULATOR - DRINK DATABASE
// Top 100 Cocktails + Top 50 German Beers
// ============================================================

const COCKTAIL_DATABASE = [
  // ===== KLASSIKER & WELTBERÜHMT =====
  { id: 1, name: "Mojito", category: "Klassiker", avgPrice: 10.50, ingredients: ["Weißer Rum", "Limette", "Zucker", "Minze", "Sodawasser"], abv: 10, color: "#a8e6a3", emoji: "🍃", description: "Kubanischer Klassiker mit frischer Minze" },
  { id: 2, name: "Margarita", category: "Klassiker", avgPrice: 11.00, ingredients: ["Tequila", "Triple Sec", "Limettensaft"], abv: 13, color: "#f0e68c", emoji: "🌮", description: "Mexikanischer Tequila-Klassiker mit Salzrand" },
  { id: 3, name: "Old Fashioned", category: "Klassiker", avgPrice: 12.50, ingredients: ["Bourbon", "Zucker", "Angostura Bitter", "Orangenzeste"], abv: 32, color: "#d4760a", emoji: "🥃", description: "Der Ur-Cocktail seit 1806" },
  { id: 4, name: "Negroni", category: "Klassiker", avgPrice: 11.50, ingredients: ["Gin", "Campari", "Roter Wermut"], abv: 24, color: "#cc3333", emoji: "🔴", description: "Italienischer Bitter-Klassiker" },
  { id: 5, name: "Daiquiri", category: "Klassiker", avgPrice: 10.00, ingredients: ["Weißer Rum", "Limettensaft", "Zuckersirup"], abv: 20, color: "#fffacd", emoji: "🍋", description: "Hemingways Lieblingsdrink" },
  { id: 6, name: "Martini", category: "Klassiker", avgPrice: 12.00, ingredients: ["Gin", "Trockener Wermut", "Olive"], abv: 30, color: "#e8e8e8", emoji: "🍸", description: "Der eleganteste aller Cocktails" },
  { id: 7, name: "Manhattan", category: "Klassiker", avgPrice: 12.50, ingredients: ["Rye Whiskey", "Süßer Wermut", "Angostura Bitter"], abv: 28, color: "#8b4513", emoji: "🏙️", description: "New Yorker Whiskey-Klassiker" },
  { id: 8, name: "Whiskey Sour", category: "Klassiker", avgPrice: 11.00, ingredients: ["Bourbon", "Zitronensaft", "Zuckersirup", "Eiweiß"], abv: 15, color: "#fff3b0", emoji: "🍋", description: "Perfekte Balance aus sauer und süß" },
  { id: 9, name: "Cosmopolitan", category: "Klassiker", avgPrice: 11.50, ingredients: ["Vodka Citron", "Triple Sec", "Cranberrysaft", "Limette"], abv: 18, color: "#ff69b4", emoji: "💗", description: "Der Sex and the City Drink" },
  { id: 10, name: "Mai Tai", category: "Tiki", avgPrice: 12.00, ingredients: ["Weißer Rum", "Brauner Rum", "Curaçao", "Orgeat", "Limette"], abv: 17, color: "#ff8c00", emoji: "🌺", description: "Polynesischer Tiki-Klassiker" },

  // ===== ERFRISCHEND & FRUCHTIG =====
  { id: 11, name: "Piña Colada", category: "Tiki", avgPrice: 11.00, ingredients: ["Weißer Rum", "Kokosmilch", "Ananassaft"], abv: 12, color: "#fffdd0", emoji: "🍍", description: "Karibischer Kokos-Ananas-Traum" },
  { id: 12, name: "Caipirinha", category: "Klassiker", avgPrice: 10.00, ingredients: ["Cachaça", "Limette", "Rohrzucker"], abv: 18, color: "#c5e384", emoji: "🇧🇷", description: "Brasiliens Nationaldrink" },
  { id: 13, name: "Moscow Mule", category: "Longdrink", avgPrice: 10.50, ingredients: ["Vodka", "Ginger Beer", "Limette"], abv: 10, color: "#f5deb3", emoji: "🫏", description: "Serviert im Kupferbecher" },
  { id: 14, name: "Gin Tonic", category: "Longdrink", avgPrice: 10.00, ingredients: ["Gin", "Tonic Water", "Zitrone/Gurke"], abv: 12, color: "#e0ffff", emoji: "🫧", description: "Zeitloser Longdrink-Klassiker" },
  { id: 15, name: "Aperol Spritz", category: "Spritz", avgPrice: 9.50, ingredients: ["Aperol", "Prosecco", "Sodawasser"], abv: 8, color: "#ff6347", emoji: "🧡", description: "Italiens Sommer-Hit" },
  { id: 16, name: "Hugo", category: "Spritz", avgPrice: 9.00, ingredients: ["Holunderblütensirup", "Prosecco", "Sodawasser", "Minze"], abv: 6, color: "#98fb98", emoji: "🌿", description: "Alpenländischer Spritz-Liebling" },
  { id: 17, name: "Tequila Sunrise", category: "Longdrink", avgPrice: 10.00, ingredients: ["Tequila", "Orangensaft", "Grenadine"], abv: 11, color: "#ff4500", emoji: "🌅", description: "Sonnenaufgang im Glas" },
  { id: 18, name: "Sex on the Beach", category: "Longdrink", avgPrice: 10.50, ingredients: ["Vodka", "Pfirsichlikör", "Orangensaft", "Cranberrysaft"], abv: 10, color: "#ff6b6b", emoji: "🏖️", description: "Fruchtiger Party-Klassiker" },
  { id: 19, name: "Blue Lagoon", category: "Longdrink", avgPrice: 10.00, ingredients: ["Vodka", "Blue Curaçao", "Zitronensaft", "Sprite"], abv: 10, color: "#00bfff", emoji: "💎", description: "Leuchtend blauer Hingucker" },
  { id: 20, name: "Zombie", category: "Tiki", avgPrice: 13.00, ingredients: ["Weißer Rum", "Brauner Rum", "Overproof Rum", "Aprikose", "Limette", "Grenadine"], abv: 22, color: "#9acd32", emoji: "🧟", description: "Mächtigster Tiki-Drink" },

  // ===== WHISKEY & SPIRITUOSEN =====
  { id: 21, name: "Sazerac", category: "Klassiker", avgPrice: 13.00, ingredients: ["Rye Whiskey", "Absinth", "Peychaud's Bitter", "Zucker"], abv: 30, color: "#d4a574", emoji: "⚜️", description: "New Orleans' ältester Cocktail" },
  { id: 22, name: "Mint Julep", category: "Klassiker", avgPrice: 11.50, ingredients: ["Bourbon", "Minze", "Zucker", "Crushed Ice"], abv: 18, color: "#90ee90", emoji: "🏇", description: "Kentucky Derby Tradition" },
  { id: 23, name: "Rob Roy", category: "Klassiker", avgPrice: 12.00, ingredients: ["Scotch Whisky", "Süßer Wermut", "Angostura"], abv: 28, color: "#b22222", emoji: "🏴󠁧󠁢󠁳󠁣󠁴󠁿", description: "Schottischer Manhattan" },
  { id: 24, name: "Rusty Nail", category: "Klassiker", avgPrice: 12.00, ingredients: ["Scotch Whisky", "Drambuie"], abv: 32, color: "#cd853f", emoji: "🔩", description: "Scotch trifft auf Honilikör" },
  { id: 25, name: "Irish Coffee", category: "Warm", avgPrice: 9.50, ingredients: ["Irish Whiskey", "Kaffee", "Zucker", "Sahne"], abv: 10, color: "#3c1414", emoji: "☕", description: "Wärmender Whiskey-Kaffee" },
  { id: 26, name: "Boulevardier", category: "Klassiker", avgPrice: 12.00, ingredients: ["Bourbon", "Campari", "Süßer Wermut"], abv: 25, color: "#c41e3a", emoji: "🎩", description: "Negroni mit Whiskey" },
  { id: 27, name: "Penicillin", category: "Modern", avgPrice: 12.50, ingredients: ["Scotch", "Zitrone", "Honig-Ingwer-Sirup", "Islay Scotch Float"], abv: 20, color: "#daa520", emoji: "💊", description: "Moderner Scotch-Klassiker" },
  { id: 28, name: "Gold Rush", category: "Modern", avgPrice: 11.50, ingredients: ["Bourbon", "Zitronensaft", "Honigsirup"], abv: 18, color: "#ffd700", emoji: "⛏️", description: "Honig-Bourbon-Sour" },
  { id: 29, name: "Paper Plane", category: "Modern", avgPrice: 12.00, ingredients: ["Bourbon", "Aperol", "Amaro Nonino", "Zitronensaft"], abv: 20, color: "#f4a460", emoji: "✈️", description: "Equal-Parts Meisterwerk" },
  { id: 30, name: "Godfather", category: "Klassiker", avgPrice: 11.00, ingredients: ["Scotch Whisky", "Amaretto"], abv: 28, color: "#8b6914", emoji: "🎬", description: "Einfach und stark" },

  // ===== GIN-BASIERT =====
  { id: 31, name: "Gin Fizz", category: "Fizz", avgPrice: 10.50, ingredients: ["Gin", "Zitronensaft", "Zucker", "Sodawasser"], abv: 12, color: "#f5f5dc", emoji: "🫧", description: "Prickelnder Gin-Klassiker" },
  { id: 32, name: "Tom Collins", category: "Longdrink", avgPrice: 10.00, ingredients: ["Old Tom Gin", "Zitronensaft", "Zucker", "Sodawasser"], abv: 10, color: "#fafad2", emoji: "🍋", description: "Der erste Highball" },
  { id: 33, name: "Last Word", category: "Klassiker", avgPrice: 12.50, ingredients: ["Gin", "Green Chartreuse", "Maraschino", "Limettensaft"], abv: 24, color: "#7fff00", emoji: "🟢", description: "Equal-Parts Prohibition-Cocktail" },
  { id: 34, name: "Gimlet", category: "Klassiker", avgPrice: 11.00, ingredients: ["Gin", "Lime Cordial"], abv: 26, color: "#c1ffc1", emoji: "🍸", description: "Navy-Klassiker" },
  { id: 35, name: "Aviation", category: "Klassiker", avgPrice: 12.00, ingredients: ["Gin", "Maraschino", "Crème de Violette", "Zitronensaft"], abv: 22, color: "#b0c4de", emoji: "✈️", description: "Violetter Gin-Traum" },
  { id: 36, name: "Corpse Reviver No.2", category: "Klassiker", avgPrice: 12.00, ingredients: ["Gin", "Cointreau", "Lillet Blanc", "Zitronensaft", "Absinth"], abv: 20, color: "#ffefd5", emoji: "💀", description: "Anti-Kater Klassiker" },
  { id: 37, name: "Bramble", category: "Modern", avgPrice: 11.50, ingredients: ["Gin", "Zitronensaft", "Zucker", "Crème de Mûre"], abv: 16, color: "#6b2fa0", emoji: "🫐", description: "Brombeer-Gin Sour" },
  { id: 38, name: "French 75", category: "Champagner", avgPrice: 13.00, ingredients: ["Gin", "Zitronensaft", "Zucker", "Champagner"], abv: 15, color: "#fffacd", emoji: "🥂", description: "Gin trifft Champagner" },
  { id: 39, name: "Singapore Sling", category: "Longdrink", avgPrice: 13.50, ingredients: ["Gin", "Kirschlikör", "Cointreau", "Bénédictine", "Ananassaft", "Limette", "Grenadine", "Bitter"], abv: 12, color: "#ff7f7f", emoji: "🇸🇬", description: "Komplexer Raffles-Klassiker" },
  { id: 40, name: "Southside", category: "Klassiker", avgPrice: 11.50, ingredients: ["Gin", "Limettensaft", "Zucker", "Minze"], abv: 18, color: "#90ee90", emoji: "🌿", description: "Gin-Mojito mit Geschichte" },

  // ===== VODKA-BASIERT =====
  { id: 41, name: "Espresso Martini", category: "Modern", avgPrice: 12.50, ingredients: ["Vodka", "Kaffeelikör", "Espresso", "Zuckersirup"], abv: 18, color: "#2f1b14", emoji: "☕", description: "Koffein trifft Alkohol" },
  { id: 42, name: "Bloody Mary", category: "Longdrink", avgPrice: 11.00, ingredients: ["Vodka", "Tomatensaft", "Zitrone", "Worcestersauce", "Tabasco", "Sellerie"], abv: 10, color: "#dc143c", emoji: "🍅", description: "Der Brunch-Cocktail" },
  { id: 43, name: "White Russian", category: "Klassiker", avgPrice: 10.50, ingredients: ["Vodka", "Kaffeelikör", "Sahne"], abv: 15, color: "#f5deb3", emoji: "🥛", description: "The Dude's Lieblingsdrink" },
  { id: 44, name: "Black Russian", category: "Klassiker", avgPrice: 10.00, ingredients: ["Vodka", "Kaffeelikör"], abv: 22, color: "#1a1a2e", emoji: "🖤", description: "Puristische Vodka-Kaffee Kombination" },
  { id: 45, name: "Lemon Drop Martini", category: "Modern", avgPrice: 11.00, ingredients: ["Vodka Citron", "Triple Sec", "Zitronensaft", "Zucker"], abv: 20, color: "#fff44f", emoji: "🍋", description: "Süß-saurer Vodka-Drink" },
  { id: 46, name: "Pornstar Martini", category: "Modern", avgPrice: 13.00, ingredients: ["Vanilla Vodka", "Passoa", "Passionsfrucht", "Limette", "Prosecco"], abv: 14, color: "#ffb347", emoji: "⭐", description: "Passionsfrucht-Sensation" },
  { id: 47, name: "Harvey Wallbanger", category: "Longdrink", avgPrice: 10.50, ingredients: ["Vodka", "Orangensaft", "Galliano"], abv: 10, color: "#ffa500", emoji: "🧱", description: "70er-Jahre Klassiker" },
  { id: 48, name: "Screwdriver", category: "Longdrink", avgPrice: 8.50, ingredients: ["Vodka", "Orangensaft"], abv: 10, color: "#ffa500", emoji: "🔧", description: "Einfach und effektiv" },
  { id: 49, name: "Appletini", category: "Modern", avgPrice: 11.00, ingredients: ["Vodka", "Apfellikör", "Zitronensaft"], abv: 18, color: "#7cfc00", emoji: "🍏", description: "Grüner Apfel-Martini" },
  { id: 50, name: "Vodka Sour", category: "Sour", avgPrice: 10.50, ingredients: ["Vodka", "Zitronensaft", "Zuckersirup", "Eiweiß"], abv: 16, color: "#fffff0", emoji: "🍸", description: "Samtweicher Vodka-Sour" },

  // ===== RUM-BASIERT =====
  { id: 51, name: "Dark 'n' Stormy", category: "Longdrink", avgPrice: 10.50, ingredients: ["Dark Rum", "Ginger Beer", "Limette"], abv: 12, color: "#654321", emoji: "⛈️", description: "Bermudas Nationaldrink" },
  { id: 52, name: "Cuba Libre", category: "Longdrink", avgPrice: 9.50, ingredients: ["Weißer Rum", "Cola", "Limette"], abv: 10, color: "#3b1e08", emoji: "🇨🇺", description: "Rum und Cola - Freiheit!" },
  { id: 53, name: "Hurricane", category: "Tiki", avgPrice: 12.00, ingredients: ["Weißer Rum", "Brauner Rum", "Passionsfrucht", "Orange", "Limette", "Grenadine"], abv: 14, color: "#ff4040", emoji: "🌀", description: "Mardi Gras Klassiker" },
  { id: 54, name: "Painkiller", category: "Tiki", avgPrice: 12.00, ingredients: ["Dark Rum", "Ananassaft", "Orangensaft", "Kokosmilch", "Muskat"], abv: 13, color: "#ffe4b5", emoji: "💊", description: "Schmerzmittel aus der Karibik" },
  { id: 55, name: "Jungle Bird", category: "Tiki", avgPrice: 12.00, ingredients: ["Dark Rum", "Campari", "Ananassaft", "Limette", "Demerara Sirup"], abv: 16, color: "#b8441f", emoji: "🦜", description: "Bitter-tropischer Tiki" },
  { id: 56, name: "El Diablo", category: "Longdrink", avgPrice: 11.00, ingredients: ["Tequila", "Crème de Cassis", "Limette", "Ginger Beer"], abv: 10, color: "#800020", emoji: "😈", description: "Teuflisch guter Tequila-Drink" },
  { id: 57, name: "Planters Punch", category: "Tiki", avgPrice: 11.00, ingredients: ["Dark Rum", "Zitronensaft", "Zucker", "Grenadine", "Angostura"], abv: 14, color: "#ff6b35", emoji: "🌴", description: "Jamaikanischer Plantagen-Punch" },
  { id: 58, name: "Banana Daiquiri", category: "Frozen", avgPrice: 11.50, ingredients: ["Weißer Rum", "Banane", "Limette", "Zucker"], abv: 14, color: "#ffe135", emoji: "🍌", description: "Gefrorener Bananen-Traum" },
  { id: 59, name: "Rum Sour", category: "Sour", avgPrice: 10.50, ingredients: ["Dark Rum", "Zitronensaft", "Zucker", "Eiweiß"], abv: 16, color: "#deb887", emoji: "🍋", description: "Samtweich und aromatisch" },
  { id: 60, name: "Ti' Punch", category: "Klassiker", avgPrice: 9.00, ingredients: ["Rhum Agricole", "Limette", "Rohrzuckersirup"], abv: 25, color: "#f5f5dc", emoji: "🌴", description: "Martiniques Nationaldrink" },

  // ===== TEQUILA & MEZCAL =====
  { id: 61, name: "Paloma", category: "Longdrink", avgPrice: 10.50, ingredients: ["Tequila", "Grapefruitsaft", "Limette", "Sodawasser", "Salz"], abv: 10, color: "#ffb6c1", emoji: "🕊️", description: "Mexikos eigentlicher Liebling" },
  { id: 62, name: "Tommy's Margarita", category: "Modern", avgPrice: 11.50, ingredients: ["Tequila", "Limettensaft", "Agavensirup"], abv: 18, color: "#f0e68c", emoji: "🌵", description: "San Franciscos beste Margarita" },
  { id: 63, name: "Mezcal Mule", category: "Modern", avgPrice: 11.50, ingredients: ["Mezcal", "Ginger Beer", "Limette"], abv: 12, color: "#d2b48c", emoji: "🔥", description: "Rauchiger Moscow Mule" },
  { id: 64, name: "Oaxaca Old Fashioned", category: "Modern", avgPrice: 13.00, ingredients: ["Tequila", "Mezcal", "Agavensirup", "Angostura"], abv: 30, color: "#cd853f", emoji: "🇲🇽", description: "Mexikanischer Old Fashioned" },
  { id: 65, name: "Mexican Firing Squad", category: "Klassiker", avgPrice: 12.00, ingredients: ["Tequila", "Limette", "Grenadine", "Angostura"], abv: 16, color: "#ff4500", emoji: "💥", description: "Historischer Tequila-Drink" },

  // ===== CHAMPAGNER & WEIN =====
  { id: 66, name: "Bellini", category: "Champagner", avgPrice: 11.00, ingredients: ["Prosecco", "Pfirsichpüree"], abv: 7, color: "#ffdab9", emoji: "🍑", description: "Venetianischer Brunch-Klassiker" },
  { id: 67, name: "Kir Royal", category: "Champagner", avgPrice: 11.50, ingredients: ["Champagner", "Crème de Cassis"], abv: 10, color: "#722f37", emoji: "👑", description: "Französischer Champagner-Aperitif" },
  { id: 68, name: "Mimosa", category: "Champagner", avgPrice: 9.50, ingredients: ["Champagner", "Orangensaft"], abv: 7, color: "#ffa500", emoji: "🥂", description: "Brunch-Essential" },
  { id: 69, name: "Spritz Veneziano", category: "Spritz", avgPrice: 9.50, ingredients: ["Select", "Prosecco", "Sodawasser", "Olive"], abv: 8, color: "#ff4500", emoji: "🇮🇹", description: "Der Original Venezianische Spritz" },
  { id: 70, name: "Sangria", category: "Punch", avgPrice: 8.50, ingredients: ["Rotwein", "Brandy", "Orangensaft", "Früchte", "Zucker"], abv: 10, color: "#8b0000", emoji: "🍷", description: "Spanischer Frucht-Punch" },

  // ===== MODERNE KLASSIKER =====
  { id: 71, name: "Amaretto Sour", category: "Sour", avgPrice: 10.50, ingredients: ["Amaretto", "Bourbon", "Zitronensaft", "Zucker", "Eiweiß"], abv: 14, color: "#daa520", emoji: "🍒", description: "Jeffrey Morgenthaler's Version" },
  { id: 72, name: "Naked & Famous", category: "Modern", avgPrice: 12.50, ingredients: ["Mezcal", "Aperol", "Yellow Chartreuse", "Limettensaft"], abv: 20, color: "#ffa07a", emoji: "🔥", description: "Rauchig-bitterer Modern Classic" },
  { id: 73, name: "Clover Club", category: "Klassiker", avgPrice: 12.00, ingredients: ["Gin", "Himbeersirup", "Zitrone", "Eiweiß"], abv: 16, color: "#db7093", emoji: "🍀", description: "Rosa Gin-Sour mit Geschichte" },
  { id: 74, name: "Vieux Carré", category: "Klassiker", avgPrice: 13.00, ingredients: ["Rye", "Cognac", "Süßer Wermut", "Bénédictine", "Peychaud's", "Angostura"], abv: 28, color: "#8b4513", emoji: "🎺", description: "New Orleans' komplexester Drink" },
  { id: 75, name: "Pisco Sour", category: "Sour", avgPrice: 11.50, ingredients: ["Pisco", "Limettensaft", "Zucker", "Eiweiß", "Angostura"], abv: 16, color: "#fdf5e6", emoji: "🇵🇪", description: "Peruanischer Nationalcocktail" },
  { id: 76, name: "Hemingway Daiquiri", category: "Klassiker", avgPrice: 12.00, ingredients: ["Weißer Rum", "Limette", "Grapefruit", "Maraschino"], abv: 18, color: "#ffe4e1", emoji: "📝", description: "Papa Doble - zuckerfrei" },
  { id: 77, name: "Sidecar", category: "Klassiker", avgPrice: 12.00, ingredients: ["Cognac", "Cointreau", "Zitronensaft"], abv: 22, color: "#f4a460", emoji: "🏍️", description: "Pariser Cognac-Klassiker" },
  { id: 78, name: "Bee's Knees", category: "Klassiker", avgPrice: 11.00, ingredients: ["Gin", "Honigsirup", "Zitronensaft"], abv: 18, color: "#f0c300", emoji: "🐝", description: "Prohibition-Ära Honig-Gin" },
  { id: 79, name: "Paloma Rosa", category: "Modern", avgPrice: 11.00, ingredients: ["Tequila", "Pink Grapefruit", "Limette", "Agavensirup", "Soda"], abv: 10, color: "#ff69b4", emoji: "🌸", description: "Pinke Paloma-Variante" },
  { id: 80, name: "Japanese Highball", category: "Highball", avgPrice: 10.00, ingredients: ["Japanese Whisky", "Sodawasser"], abv: 10, color: "#f5f5dc", emoji: "🇯🇵", description: "Japanische Trinkkultur" },

  // ===== BESONDERE & EXOTISCHE =====
  { id: 81, name: "Long Island Iced Tea", category: "Longdrink", avgPrice: 12.00, ingredients: ["Vodka", "Gin", "Rum", "Tequila", "Triple Sec", "Zitrone", "Cola"], abv: 22, color: "#d2691e", emoji: "🏝️", description: "5 Spirituosen in einem Glas" },
  { id: 82, name: "B-52", category: "Shooter", avgPrice: 7.50, ingredients: ["Kaffeelikör", "Bailey's", "Grand Marnier"], abv: 20, color: "#8b6508", emoji: "✈️", description: "Geschichteter Bomber-Shot" },
  { id: 83, name: "Amaretto Disaronno Sour", category: "Sour", avgPrice: 10.00, ingredients: ["Disaronno", "Zitronensaft", "Zucker"], abv: 12, color: "#daa520", emoji: "🍒", description: "Mandel-Sour pur" },
  { id: 84, name: "Grasshopper", category: "After Dinner", avgPrice: 10.00, ingredients: ["Crème de Menthe", "Crème de Cacao", "Sahne"], abv: 14, color: "#00ff7f", emoji: "🦗", description: "Minz-Schoko Dessert-Drink" },
  { id: 85, name: "Amaretto Stone Sour", category: "Sour", avgPrice: 10.00, ingredients: ["Amaretto", "Orangensaft", "Sweet & Sour"], abv: 10, color: "#ff8c00", emoji: "🪨", description: "Fruchtiger Amaretto-Drink" },
  { id: 86, name: "Vesper Martini", category: "Klassiker", avgPrice: 13.00, ingredients: ["Gin", "Vodka", "Lillet Blanc"], abv: 28, color: "#f0e68c", emoji: "🔫", description: "James Bonds Erfindung" },
  { id: 87, name: "Hanky Panky", category: "Klassiker", avgPrice: 12.00, ingredients: ["Gin", "Süßer Wermut", "Fernet Branca"], abv: 26, color: "#800000", emoji: "🎭", description: "Ada Colemans Meisterwerk" },
  { id: 88, name: "Jungle Juice", category: "Punch", avgPrice: 9.00, ingredients: ["Vodka", "Rum", "Diverse Fruchtsäfte"], abv: 12, color: "#ff6347", emoji: "🌴", description: "Party-Punch Deluxe" },
  { id: 89, name: "Zombie Punch", category: "Tiki", avgPrice: 14.00, ingredients: ["3x Rum", "Falernum", "Absinth", "Zimt", "Grenadine", "Zitrus"], abv: 25, color: "#556b2f", emoji: "🧟", description: "Donn Beach's Geheimrezept" },
  { id: 90, name: "Pistachio Sour", category: "Modern", avgPrice: 13.00, ingredients: ["Vodka", "Pistaziensirup", "Zitrone", "Eiweiß"], abv: 16, color: "#93c572", emoji: "🟢", description: "Trendiger Nuss-Sour" },

  // ===== ALKOHOLFREIE COCKTAILS (MOCKTAILS) =====
  { id: 91, name: "Virgin Mojito", category: "Alkoholfrei", avgPrice: 7.50, ingredients: ["Limette", "Minze", "Zucker", "Sodawasser"], abv: 0, color: "#a8e6cf", emoji: "🍃", description: "Mojito ohne Alkohol" },
  { id: 92, name: "Shirley Temple", category: "Alkoholfrei", avgPrice: 6.50, ingredients: ["Ginger Ale", "Grenadine", "Zitrone"], abv: 0, color: "#ff6b6b", emoji: "👧", description: "Kindheitserinnerung" },
  { id: 93, name: "Ipanema", category: "Alkoholfrei", avgPrice: 7.50, ingredients: ["Limette", "Rohrzucker", "Maracuja", "Ginger Ale"], abv: 0, color: "#f0e68c", emoji: "🏖️", description: "Alkoholfreie Caipirinha" },

  // ===== SHOTS & SPECIALS =====
  { id: 94, name: "Jägerbomb", category: "Shot", avgPrice: 6.50, ingredients: ["Jägermeister", "Red Bull"], abv: 12, color: "#2f4f4f", emoji: "💣", description: "Party-Shot Nummer 1" },
  { id: 95, name: "Tequila Shot", category: "Shot", avgPrice: 5.00, ingredients: ["Tequila", "Salz", "Zitrone"], abv: 38, color: "#f5f5dc", emoji: "🧂", description: "Lecken, Trinken, Beißen" },
  { id: 96, name: "Kamikaze", category: "Shot", avgPrice: 5.50, ingredients: ["Vodka", "Triple Sec", "Limette"], abv: 25, color: "#e0ffff", emoji: "⚡", description: "Scharfer Zitrus-Shot" },
  { id: 97, name: "Sambuca Shot", category: "Shot", avgPrice: 5.50, ingredients: ["Sambuca", "Kaffeebohnen"], abv: 38, color: "#f5f5f5", emoji: "✨", description: "Italienischer Anis-Shot" },
  { id: 98, name: "Mexikaner", category: "Shot", avgPrice: 4.50, ingredients: ["Korn", "Tomatensaft", "Tabasco", "Salz", "Pfeffer"], abv: 15, color: "#cc0000", emoji: "🌶️", description: "Deutscher Party-Shot" },
  { id: 99, name: "Swimming Pool", category: "Longdrink", avgPrice: 11.50, ingredients: ["Vodka", "Rum", "Blue Curaçao", "Kokosmilch", "Ananassaft", "Sahne"], abv: 12, color: "#87ceeb", emoji: "🏊", description: "Wie ein Pool im Glas" },
  { id: 100, name: "Gin Basil Smash", category: "Modern", avgPrice: 11.50, ingredients: ["Gin", "Basilikum", "Zitronensaft", "Zucker"], abv: 16, color: "#3cb371", emoji: "🌿", description: "Hamburger Gin-Revolution" }
];

// ============================================================
// TOP 50 DEUTSCHE BIERE
// ============================================================
const BEER_DATABASE = [
  // ===== PILSENER =====
  { id: 1, name: "Krombacher Pils", category: "Pilsener", brewery: "Krombacher Brauerei", region: "Nordrhein-Westfalen", avgPrice: 4.20, abv: 4.8, color: "#f4c430", emoji: "🍺", description: "Eine Perle der Natur" },
  { id: 2, name: "Bitburger Premium Pils", category: "Pilsener", brewery: "Bitburger Brauerei", region: "Rheinland-Pfalz", avgPrice: 4.20, abv: 4.8, color: "#f0c300", emoji: "🍺", description: "Bitte ein Bit" },
  { id: 3, name: "Warsteiner Premium Verum", category: "Pilsener", brewery: "Warsteiner Brauerei", region: "Nordrhein-Westfalen", avgPrice: 4.00, abv: 4.8, color: "#f5d442", emoji: "🍺", description: "Das einzig Wahre" },
  { id: 4, name: "Veltins Pilsener", category: "Pilsener", brewery: "Brauerei Veltins", region: "Nordrhein-Westfalen", avgPrice: 4.00, abv: 4.8, color: "#f0c300", emoji: "🍺", description: "Aus dem Sauerland" },
  { id: 5, name: "Beck's", category: "Pilsener", brewery: "Brauerei Beck's", region: "Bremen", avgPrice: 4.00, abv: 4.9, color: "#e8c300", emoji: "🍺", description: "Folge deinem inneren Kompass" },
  { id: 6, name: "Radeberger Pilsner", category: "Pilsener", brewery: "Radeberger Exportbierbrauerei", region: "Sachsen", avgPrice: 4.00, abv: 4.8, color: "#f5d442", emoji: "🍺", description: "Erstes deutsches Pilsner" },
  { id: 7, name: "Jever Pilsener", category: "Pilsener", brewery: "Friesisches Brauhaus", region: "Niedersachsen", avgPrice: 4.20, abv: 4.9, color: "#e8c300", emoji: "🍺", description: "Friesisch herb" },
  { id: 8, name: "Flensburger Pilsener", category: "Pilsener", brewery: "Flensburger Brauerei", region: "Schleswig-Holstein", avgPrice: 4.20, abv: 4.8, color: "#f4c430", emoji: "🍺", description: "Plop! - Die Bügelflasche" },
  { id: 9, name: "Hasseröder Premium Pils", category: "Pilsener", brewery: "Hasseröder Brauerei", region: "Sachsen-Anhalt", avgPrice: 3.80, abv: 4.9, color: "#f0c300", emoji: "🍺", description: "Männer sind so" },
  { id: 10, name: "König Pilsener", category: "Pilsener", brewery: "König-Brauerei", region: "Nordrhein-Westfalen", avgPrice: 4.00, abv: 4.9, color: "#f5d442", emoji: "🍺", description: "Die König unter den Bieren" },
  { id: 11, name: "Oettinger Pils", category: "Pilsener", brewery: "Oettinger Brauerei", region: "Bayern", avgPrice: 3.00, abv: 4.7, color: "#e8c300", emoji: "🍺", description: "Deutschlands meistverkauftes Bier" },
  { id: 12, name: "Holsten Pilsener", category: "Pilsener", brewery: "Holsten-Brauerei", region: "Hamburg", avgPrice: 3.80, abv: 4.8, color: "#f4c430", emoji: "🍺", description: "Aus dem Norden" },

  // ===== WEIZENBIER / HEFEWEIZEN =====
  { id: 13, name: "Erdinger Weißbier", category: "Weizenbier", brewery: "Erdinger Weißbräu", region: "Bayern", avgPrice: 4.50, abv: 5.3, color: "#ffb347", emoji: "🌾", description: "Bayerns Weißbier Nr. 1" },
  { id: 14, name: "Paulaner Hefe-Weißbier", category: "Weizenbier", brewery: "Paulaner Brauerei", region: "Bayern", avgPrice: 4.50, abv: 5.5, color: "#ffb347", emoji: "🌾", description: "Münchner Braukunst" },
  { id: 15, name: "Franziskaner Weißbier", category: "Weizenbier", brewery: "Franziskaner Brauerei", region: "Bayern", avgPrice: 4.50, abv: 5.0, color: "#f0a830", emoji: "🌾", description: "Seit 1363" },
  { id: 16, name: "Weihenstephaner Hefeweißbier", category: "Weizenbier", brewery: "Bayerische Staatsbrauerei Weihenstephan", region: "Bayern", avgPrice: 4.60, abv: 5.4, color: "#ffcc00", emoji: "🌾", description: "Älteste Brauerei der Welt" },
  { id: 17, name: "Schneider Weisse TAP 7", category: "Weizenbier", brewery: "Schneider Weisse", region: "Bayern", avgPrice: 4.50, abv: 5.4, color: "#cc8400", emoji: "🌾", description: "Original Weißbier" },
  { id: 18, name: "Maisel's Weisse", category: "Weizenbier", brewery: "Brauerei Maisel", region: "Bayern", avgPrice: 4.40, abv: 5.2, color: "#ffb347", emoji: "🌾", description: "Aus Bayreuth" },

  // ===== HELLES / LAGER =====
  { id: 19, name: "Augustiner Helles", category: "Helles", brewery: "Augustiner Bräu", region: "Bayern", avgPrice: 4.50, abv: 5.2, color: "#f5d442", emoji: "☀️", description: "Münchens Liebling" },
  { id: 20, name: "Tegernseer Hell", category: "Helles", brewery: "Herzogl. Bayerisches Brauhaus", region: "Bayern", avgPrice: 4.50, abv: 4.8, color: "#f4c430", emoji: "☀️", description: "Vom Tegernsee" },
  { id: 21, name: "Spaten Münchner Hell", category: "Helles", brewery: "Spaten-Franziskaner-Bräu", region: "Bayern", avgPrice: 4.30, abv: 5.2, color: "#f0c300", emoji: "☀️", description: "Seit 1397" },
  { id: 22, name: "Hofbräu Original", category: "Helles", brewery: "Staatliches Hofbräuhaus", region: "Bayern", avgPrice: 4.50, abv: 5.1, color: "#f5d442", emoji: "☀️", description: "Das Hofbräuhaus-Bier" },
  { id: 23, name: "Löwenbräu Original", category: "Helles", brewery: "Löwenbräu", region: "Bayern", avgPrice: 4.20, abv: 5.2, color: "#f4c430", emoji: "🦁", description: "Löwenstark" },
  { id: 24, name: "Hacker-Pschorr Münchner Hell", category: "Helles", brewery: "Hacker-Pschorr", region: "Bayern", avgPrice: 4.40, abv: 5.0, color: "#f5d442", emoji: "☀️", description: "Himmel der Bayern" },
  { id: 25, name: "Paulaner Münchner Hell", category: "Helles", brewery: "Paulaner Brauerei", region: "Bayern", avgPrice: 4.30, abv: 4.9, color: "#f0c300", emoji: "☀️", description: "Gut. Besser. Paulaner." },

  // ===== EXPORT & MÄRZEN =====
  { id: 26, name: "Dortmunder Union Export", category: "Export", brewery: "Dortmunder Union", region: "Nordrhein-Westfalen", avgPrice: 3.80, abv: 5.2, color: "#e8b000", emoji: "🏭", description: "Dortmunder Bierkultur" },
  { id: 27, name: "DAB Export", category: "Export", brewery: "DAB", region: "Nordrhein-Westfalen", avgPrice: 3.80, abv: 5.0, color: "#e8b000", emoji: "🏭", description: "Dortmunder Actien-Brauerei" },
  { id: 28, name: "Spaten Oktoberfestbier", category: "Märzen", brewery: "Spaten-Franziskaner-Bräu", region: "Bayern", avgPrice: 5.00, abv: 5.9, color: "#cc8400", emoji: "🎪", description: "Das Original vom Oktoberfest" },

  // ===== KÖLSCH =====
  { id: 29, name: "Gaffel Kölsch", category: "Kölsch", brewery: "Privatbrauerei Gaffel", region: "Nordrhein-Westfalen", avgPrice: 4.00, abv: 4.8, color: "#f5e642", emoji: "⛪", description: "Kölner Traditionsbier" },
  { id: 30, name: "Früh Kölsch", category: "Kölsch", brewery: "Cölner Hofbräu Früh", region: "Nordrhein-Westfalen", avgPrice: 4.00, abv: 4.8, color: "#f5e642", emoji: "⛪", description: "Für die schönsten Stunden" },
  { id: 31, name: "Reissdorf Kölsch", category: "Kölsch", brewery: "Privat-Brauerei Heinrich Reissdorf", region: "Nordrhein-Westfalen", avgPrice: 4.00, abv: 4.8, color: "#f5e642", emoji: "⛪", description: "Kölns meistgetrunkenes Kölsch" },
  { id: 32, name: "Mühlen Kölsch", category: "Kölsch", brewery: "Brauerei zur Malzmühle", region: "Nordrhein-Westfalen", avgPrice: 4.00, abv: 4.8, color: "#f5e642", emoji: "⛪", description: "Echt Kölsch" },

  // ===== ALT =====
  { id: 33, name: "Frankenheim Alt", category: "Altbier", brewery: "Frankenheim", region: "Nordrhein-Westfalen", avgPrice: 4.00, abv: 4.8, color: "#8b4513", emoji: "🏰", description: "Düsseldorfer Tradition" },
  { id: 34, name: "Diebels Alt", category: "Altbier", brewery: "Brauerei Diebels", region: "Nordrhein-Westfalen", avgPrice: 4.00, abv: 4.9, color: "#8b4513", emoji: "🏰", description: "Altbier Nr. 1" },
  { id: 35, name: "Füchschen Alt", category: "Altbier", brewery: "Brauerei Im Füchschen", region: "Nordrhein-Westfalen", avgPrice: 4.20, abv: 4.8, color: "#a0522d", emoji: "🦊", description: "Düsseldorfer Hausbrauerei" },
  { id: 36, name: "Schumacher Alt", category: "Altbier", brewery: "Brauerei Schumacher", region: "Nordrhein-Westfalen", avgPrice: 4.20, abv: 4.6, color: "#8b4513", emoji: "🏰", description: "Älteste Altbierbrauerei" },

  // ===== SCHWARZBIER & DUNKEL =====
  { id: 37, name: "Köstritzer Schwarzbier", category: "Schwarzbier", brewery: "Köstritzer Schwarzbierbrauerei", region: "Thüringen", avgPrice: 4.00, abv: 4.8, color: "#1a1a1a", emoji: "🌑", description: "Deutschlands Nr. 1 Schwarzbier" },
  { id: 38, name: "König Ludwig Dunkel", category: "Dunkel", brewery: "König Ludwig Schlossbrauerei", region: "Bayern", avgPrice: 4.40, abv: 5.1, color: "#3c1414", emoji: "🏰", description: "Königlich bayerisch" },
  { id: 39, name: "Weltenburger Kloster Barock Dunkel", category: "Dunkel", brewery: "Weltenburger Kloster", region: "Bayern", avgPrice: 4.50, abv: 4.7, color: "#2f1b14", emoji: "⛪", description: "Älteste Klosterbrauerei der Welt" },

  // ===== BOCKBIER =====
  { id: 40, name: "Paulaner Salvator", category: "Doppelbock", brewery: "Paulaner Brauerei", region: "Bayern", avgPrice: 5.00, abv: 7.9, color: "#5c3317", emoji: "💪", description: "Der Urvater aller Doppelböcke" },
  { id: 41, name: "Ayinger Celebrator", category: "Doppelbock", brewery: "Brauerei Aying", region: "Bayern", avgPrice: 5.00, abv: 6.7, color: "#2f1b14", emoji: "🎉", description: "Weltberühmter Doppelbock" },
  { id: 42, name: "Einbecker Ur-Bock", category: "Bock", brewery: "Einbecker Brauhaus", region: "Niedersachsen", avgPrice: 4.50, abv: 6.5, color: "#cc8400", emoji: "🐐", description: "Geburtsort des Bockbiers" },

  // ===== CRAFT & SPEZIAL =====
  { id: 43, name: "BRLO German IPA", category: "IPA", brewery: "BRLO", region: "Berlin", avgPrice: 5.50, abv: 6.5, color: "#ffb347", emoji: "🏙️", description: "Berliner Craft Revolution" },
  { id: 44, name: "Crew Republic 7:45 Escalation", category: "IPA", brewery: "Crew Republic", region: "Bayern", avgPrice: 5.50, abv: 5.6, color: "#daa520", emoji: "🚀", description: "Double IPA aus München" },
  { id: 45, name: "Störtebeker Bernstein-Weizen", category: "Weizenbier", brewery: "Störtebeker Braumanufaktur", region: "Mecklenburg-Vorpommern", avgPrice: 4.50, abv: 5.3, color: "#d2691e", emoji: "☠️", description: "Piratenbier von der Küste" },
  { id: 46, name: "Astra Urtyp", category: "Pilsener", brewery: "Holsten (Astra)", region: "Hamburg", avgPrice: 4.00, abv: 4.9, color: "#f4c430", emoji: "❤️", description: "Was dagegen?" },
  { id: 47, name: "Rothaus Tannenzäpfle", category: "Pilsener", brewery: "Badische Staatsbrauerei Rothaus", region: "Baden-Württemberg", avgPrice: 4.20, abv: 5.1, color: "#f0c300", emoji: "🌲", description: "Schwarzwald-Kult" },
  { id: 48, name: "Schlenkerla Rauchbier Märzen", category: "Rauchbier", brewery: "Brauerei Heller-Trum", region: "Bayern", avgPrice: 4.80, abv: 5.1, color: "#654321", emoji: "🔥", description: "Bamberger Rauchbier-Original" },
  { id: 49, name: "Berliner Kindl Weisse", category: "Berliner Weisse", brewery: "Berliner-Kindl-Schultheiss-Brauerei", region: "Berlin", avgPrice: 4.00, abv: 3.0, color: "#fffacd", emoji: "🧸", description: "Mit Schuss (rot oder grün)" },
  { id: 50, name: "Andechser Doppelbock Dunkel", category: "Doppelbock", brewery: "Klosterbrauerei Andechs", region: "Bayern", avgPrice: 5.00, abv: 7.0, color: "#2f1b14", emoji: "🙏", description: "Heiliger Berg Andechs" }
];

// ============================================================
// DRINK RECOGNITION PATTERNS (für Foto-Erkennung)
// ============================================================
const DRINK_VISUAL_PATTERNS = {
  cocktails: {
    clear: ["Martini", "Gimlet", "Gin Tonic", "Tom Collins", "Vodka Sour"],
    amber: ["Old Fashioned", "Manhattan", "Whiskey Sour", "Sazerac", "Rusty Nail"],
    red: ["Negroni", "Cosmopolitan", "Bloody Mary", "Sangria", "Singapore Sling"],
    orange: ["Tequila Sunrise", "Aperol Spritz", "Mai Tai", "Hurricane", "Sex on the Beach"],
    yellow: ["Margarita", "Bee's Knees", "Gold Rush", "Lemon Drop Martini", "Bellini"],
    green: ["Mojito", "Hugo", "Grasshopper", "Gin Basil Smash", "Southside"],
    blue: ["Blue Lagoon", "Swimming Pool"],
    pink: ["Cosmopolitan", "Clover Club", "Paloma Rosa"],
    brown: ["Espresso Martini", "Irish Coffee", "White Russian", "Long Island Iced Tea"],
    white: ["Piña Colada", "Banana Daiquiri", "Painkiller"],
    dark: ["Black Russian", "Espresso Martini"]
  },
  glassTypes: {
    martini: ["Martini", "Cosmopolitan", "Espresso Martini", "Lemon Drop Martini", "Appletini", "Pornstar Martini"],
    highball: ["Mojito", "Gin Tonic", "Moscow Mule", "Cuba Libre", "Dark 'n' Stormy", "Tequila Sunrise"],
    rocks: ["Old Fashioned", "Negroni", "Sazerac", "Whiskey Sour", "Caipirinha"],
    coupe: ["Daiquiri", "Sidecar", "Aviation", "Last Word", "Gimlet"],
    hurricane: ["Hurricane", "Piña Colada", "Blue Lagoon", "Singapore Sling"],
    champagneflute: ["Bellini", "Kir Royal", "Mimosa", "French 75"],
    wine: ["Aperol Spritz", "Hugo", "Sangria", "Spritz Veneziano"],
    shot: ["Jägerbomb", "Tequila Shot", "Kamikaze", "Sambuca Shot", "B-52", "Mexikaner"],
    copper: ["Moscow Mule", "Mezcal Mule"]
  }
};

// Export für Module
if (typeof module !== 'undefined' && module.exports) {
  module.exports = { COCKTAIL_DATABASE, BEER_DATABASE, DRINK_VISUAL_PATTERNS };
}

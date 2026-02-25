package com.djoudinis.barcalculator

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.djoudinis.barcalculator.data.Drink
import com.djoudinis.barcalculator.data.DrinkDatabase
import com.djoudinis.barcalculator.ui.MainViewModel
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sensorManager = getSystemService(android.content.Context.SENSOR_SERVICE) as SensorManager
        
        setContent {
            val viewModel: MainViewModel = viewModel()
            
            // Shake Detection
            DisposableEffect(Unit) {
                val listener = object : SensorEventListener {
                    private var lastShakeTime: Long = 0
                    override fun onSensorChanged(event: SensorEvent) {
                        val x = event.values[0]
                        val y = event.values[1]
                        val z = event.values[2]
                        val acceleration = sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH
                        if (acceleration > 13f) {
                            val now = System.currentTimeMillis()
                            if (now - lastShakeTime > 2000) {
                                lastShakeTime = now
                                viewModel.getRandomSuggestion()
                            }
                        }
                    }
                    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                }
                sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI)
                onDispose { sensorManager.unregisterListener(listener) }
            }

            val accentColor by animateColorAsState(targetValue = viewModel.partyColor, label = "color")

            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = accentColor,
                    background = Color(0xFF0A0A0F),
                    surface = Color(0xFF1A1A24)
                )
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BarCalculatorApp(viewModel, accentColor)
                    
                    // Price Entry Dialog
                    viewModel.selectedDrinkForPrice?.let { drink ->
                        PriceEntryDialog(drink, viewModel) { price ->
                            viewModel.addToBill(drink, price)
                            viewModel.selectedDrinkForPrice = null
                        }
                    }

                    // Suggestion Dialog
                    viewModel.lastSuggestedDrink?.let { drink ->
                        AlertDialog(
                            onDismissRequest = { viewModel.lastSuggestedDrink = null },
                            title = { Text("Djoudinis Empfehlung 🎲", color = accentColor) },
                            text = { 
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                    Text(drink.emoji, fontSize = 80.sp)
                                    Text(drink.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                                    Text(drink.description, textAlign = TextAlign.Center, color = Color.Gray)
                                }
                            },
                            confirmButton = {
                                Button(onClick = { 
                                    viewModel.selectedDrinkForPrice = drink
                                    viewModel.lastSuggestedDrink = null 
                                }) { Text("Wählen") }
                            },
                            dismissButton = {
                                TextButton(onClick = { viewModel.lastSuggestedDrink = null }) { Text("Anderer") }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceEntryDialog(drink: Drink, viewModel: MainViewModel, onConfirm: (Double) -> Unit) {
    var priceInput by remember(drink) { mutableStateOf(
        when(drink.type) {
            com.djoudinis.barcalculator.data.DrinkType.COCKTAIL -> viewModel.standardCocktailPrice
            com.djoudinis.barcalculator.data.DrinkType.BEER -> viewModel.standardBeerPrice
            com.djoudinis.barcalculator.data.DrinkType.SHOT -> viewModel.standardShotPrice
        }.toString()
    ) }
    val price = priceInput.toDoubleOrNull() ?: 0.0
    val analysis = viewModel.getPriceAnalysis(drink, price)

    AlertDialog(
        onDismissRequest = { viewModel.selectedDrinkForPrice = null },
        title = { Text("${drink.emoji} Preis-Check") },
        text = {
            Column {
                Text("Bar-Preis in '${viewModel.currentBarName}':")
                OutlinedTextField(
                    value = priceInput,
                    onValueChange = { priceInput = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    suffix = { Text("€") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                Text(analysis, color = if (analysis.contains("ABZOCKE")) Color.Red else Color.Green, fontWeight = FontWeight.Bold)
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(price) }) { Text("Hinzufügen") }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.selectedDrinkForPrice = null }) { Text("Abbrechen") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarCalculatorApp(viewModel: MainViewModel, accentColor: Color) {
    var currentTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("DJOUDINI'S BAR CALC", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, letterSpacing = 2.sp)
                        Text("THE NIGHT IN NUMBERS", fontSize = 10.sp, color = accentColor, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF0A0A0F))
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0A0A0F).copy(alpha = 0.95f)) {
                NavigationBarItem(
                    selected = currentTab == 0,
                    onClick = { currentTab = 0 },
                    icon = { Icon(Icons.Filled.Home, "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = accentColor, selectedTextColor = accentColor)
                )
                NavigationBarItem(
                    selected = currentTab == 1,
                    onClick = { currentTab = 1 },
                    icon = { Icon(Icons.Filled.Receipt, "Bill") },
                    label = { Text("Rechnung") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = accentColor, selectedTextColor = accentColor)
                )
                NavigationBarItem(
                    selected = currentTab == 2,
                    onClick = { currentTab = 2 },
                    icon = { Icon(Icons.Filled.LocalDrink, "Menu") },
                    label = { Text("Menü") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = accentColor, selectedTextColor = accentColor)
                )
                NavigationBarItem(
                    selected = currentTab == 3,
                    onClick = { currentTab = 3 },
                    icon = { Icon(Icons.Filled.WineBar, "Pegel") },
                    label = { Text("Pegel") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = accentColor, selectedTextColor = accentColor)
                )
                NavigationBarItem(
                    selected = currentTab == 4,
                    onClick = { currentTab = 4 },
                    icon = { Icon(Icons.Filled.Settings, "Set") },
                    label = { Text("Set") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = accentColor, selectedTextColor = accentColor)
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize().background(
            Brush.verticalGradient(listOf(Color(0xFF0A0A0F), Color(0xFF14141F)))
        )) {
            when (currentTab) {
                0 -> HomeScreen(viewModel, accentColor)
                1 -> BillScreen(viewModel, accentColor)
                2 -> MenuScreen(viewModel, accentColor)
                3 -> BacScreen(viewModel, accentColor)
                4 -> SettingsScreen(viewModel, accentColor)
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel, accentColor: Color) {
    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("📍 Aktuelle Location", color = accentColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.currentBarName,
                        onValueChange = { viewModel.currentBarName = it },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Filled.Place, "Location", tint = accentColor) },
                        trailingIcon = { if(viewModel.currentBarName.isNotEmpty()) IconButton(onClick = { viewModel.currentBarName = "" }) { Icon(Icons.Filled.Clear, "Clear") } },
                        singleLine = true,
                        placeholder = { Text("Bar Name eingeben...") }
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = accentColor.copy(alpha = 0.1f))) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("🧠 Djoudini sagt:", color = accentColor, fontWeight = FontWeight.Bold)
                    Text(viewModel.djoudiniWisdom, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("📊 The Night in Numbers", fontWeight = FontWeight.Black, color = accentColor, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            NightStatLabel("Start", viewModel.startTime ?: "--:--")
                            NightStatLabel("Bars", "${viewModel.barsVisitedCount}")
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            NightStatLabel("Favorit", viewModel.favoriteDrink)
                            NightStatLabel("Alkohol", "%.1fg".format(viewModel.totalAlcoholGrams))
                        }
                    }
                    
                    @Suppress("DEPRECATION")
                    Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.DarkGray)
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        SummaryItem("Drinks", "${viewModel.billItems.size}")
                        SummaryItem("Euro", "%.2f€".format(viewModel.billTotal))
                        SummaryItem("Peak", "%.2f‰".format(viewModel.peakBac))
                    }
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth().clickable { viewModel.rollDice() }) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("🎲", fontSize = 40.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Bar-Würfel", fontWeight = FontWeight.Bold)
                        Text("Handy schütteln oder tippen", fontSize = 12.sp, color = Color.Gray)
                    }
                    Text("${viewModel.lastDiceRoll}", fontSize = 32.sp, fontWeight = FontWeight.Black, color = accentColor)
                }
            }
        }

        item {
            Button(
                onClick = { viewModel.addWater() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
            ) {
                Icon(Icons.Filled.WaterDrop, "Water")
                Spacer(modifier = Modifier.width(8.dp))
                Text("WASSER LOGGEN", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun NightStatLabel(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun SummaryItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.Black)
        Text(label, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MenuScreen(viewModel: MainViewModel, accentColor: Color) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.searchQuery = it },
            placeholder = { Text("Getränk suchen...") },
            leadingIcon = { Icon(Icons.Filled.Search, "Search") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(viewModel.filteredDrinks) { drink ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { viewModel.selectedDrinkForPrice = drink },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(drink.emoji, fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(drink.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("${drink.category} · ${drink.abv}% vol", fontSize = 11.sp, color = Color.Gray)
                        }
                        Text("%.2f€".format(drink.avgPrice), fontWeight = FontWeight.Black, color = accentColor)
                    }
                }
            }
        }
    }
}

@Composable
fun BillScreen(viewModel: MainViewModel, accentColor: Color) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Meine Rechnung", fontWeight = FontWeight.Black, fontSize = 24.sp)
        LazyColumn(modifier = Modifier.weight(1f).padding(vertical = 16.dp)) {
            items(viewModel.billItems) { item ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(item.emoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.name, fontWeight = FontWeight.Bold)
                        Row {
                            Text(item.time, fontSize = 10.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("📍 ${item.barName}", fontSize = 10.sp, color = accentColor, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text("%.2f€".format(item.price), fontWeight = FontWeight.Bold)
                    IconButton(onClick = { viewModel.removeBillItem(item) }) {
                        Icon(Icons.Filled.Delete, "Remove", tint = Color.Red.copy(alpha = 0.4f))
                    }
                }
                @Suppress("DEPRECATION")
                Divider(color = Color.DarkGray.copy(alpha = 0.2f))
            }
        }
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF252530))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("GESAMT", fontWeight = FontWeight.Black, modifier = Modifier.weight(1f))
                    Text("%.2f€".format(viewModel.billTotal), fontWeight = FontWeight.Black, fontSize = 24.sp, color = accentColor)
                }
            }
        }
    }
}

@Composable
fun BacScreen(viewModel: MainViewModel, accentColor: Color) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pegel & Prognose", fontWeight = FontWeight.Black, fontSize = 24.sp)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
            Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("%.2f‰".format(viewModel.bacValue), fontSize = 64.sp, fontWeight = FontWeight.Black, color = if (viewModel.bacValue > 0.5) Color.Red else accentColor)
                
                Spacer(modifier = Modifier.height(16.dp))
                Text("Hangover-Gefahr:", fontSize = 14.sp, color = Color.Gray)
                LinearProgressIndicator(
                    progress = viewModel.hangoverForecast / 100f,
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                    color = if (viewModel.hangoverForecast > 60) Color.Red else Color.Yellow
                )
            }
        }
        Text("Körpergewicht: ${viewModel.weight.toInt()}kg")
        Slider(value = viewModel.weight, onValueChange = { viewModel.weight = it }, valueRange = 40f..150f, colors = SliderDefaults.colors(thumbColor = accentColor, activeTrackColor = accentColor))
        Text("Zeit seit Start: ${viewModel.hoursSinceFirstDrink.toInt()}h")
        Slider(value = viewModel.hoursSinceFirstDrink, onValueChange = { viewModel.hoursSinceFirstDrink = it }, valueRange = 0f..12f, colors = SliderDefaults.colors(thumbColor = accentColor, activeTrackColor = accentColor))
    }
}

@Composable
fun SettingsScreen(viewModel: MainViewModel, accentColor: Color) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Optionen", fontWeight = FontWeight.Black, fontSize = 24.sp, modifier = Modifier.padding(bottom = 24.dp))
        
        Text("Standard-Preise", fontWeight = FontWeight.Bold, color = accentColor)
        Spacer(modifier = Modifier.height(12.dp))
        
        NightStatLabel("Cocktails", "%.2f €".format(viewModel.standardCocktailPrice))
        Slider(value = viewModel.standardCocktailPrice.toFloat(), onValueChange = { viewModel.standardCocktailPrice = it.toDouble() }, valueRange = 3f..15f, colors = SliderDefaults.colors(thumbColor = accentColor, activeTrackColor = accentColor))
        
        NightStatLabel("Bier", "%.2f €".format(viewModel.standardBeerPrice))
        Slider(value = viewModel.standardBeerPrice.toFloat(), onValueChange = { viewModel.standardBeerPrice = it.toDouble() }, valueRange = 1f..8f, colors = SliderDefaults.colors(thumbColor = accentColor, activeTrackColor = accentColor))

        NightStatLabel("Shots", "%.2f €".format(viewModel.standardShotPrice))
        Slider(value = viewModel.standardShotPrice.toFloat(), onValueChange = { viewModel.standardShotPrice = it.toDouble() }, valueRange = 1f..5f, colors = SliderDefaults.colors(thumbColor = accentColor, activeTrackColor = accentColor))
        
        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Geschlecht", modifier = Modifier.weight(1f))
            TextButton(onClick = { viewModel.isMale = true }) { Text("M", color = if(viewModel.isMale) accentColor else Color.Gray) }
            TextButton(onClick = { viewModel.isMale = false }) { Text("W", color = if(!viewModel.isMale) accentColor else Color.Gray) }
        }
    }
}

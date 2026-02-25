package com.djoudinis.barcalculator

import android.content.Context
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
        
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        
        setContent {
            val viewModel: MainViewModel = viewModel()
            
            // Shake Detection Logic
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
                    
                    // Suggestion Dialog
                    viewModel.lastSuggestedDrink?.let { drink ->
                        AlertDialog(
                            onDismissRequest = { viewModel.lastSuggestedDrink = null },
                            title = { Text("Djoudinis Empfehlung 🎲", color = accentColor) },
                            text = { 
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                    Text(drink.emoji, fontSize = 64.sp)
                                    Text(drink.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                                    Text(drink.description, textAlign = TextAlign.Center, color = Color.Gray)
                                }
                            },
                            confirmButton = {
                                Button(onClick = { 
                                    viewModel.addToBill(drink)
                                    viewModel.lastSuggestedDrink = null 
                                }) { Text("Hinzufügen") }
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
fun BarCalculatorApp(viewModel: MainViewModel, accentColor: Color) {
    var currentTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("DJOUDINI'S BAR CALC", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, letterSpacing = 2.sp)
                        Text("LUXURY EDITION", fontSize = 10.sp, color = accentColor, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF0A0A0F))
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0A0A0F).copy(alpha = 0.95f)) {
                val tabs = listOf("Home", "Rechnung", "Menü", "Pegel")
                val icons = listOf(Icons.Default.Home, Icons.Default.Receipt, Icons.Default.LocalDrink, Icons.Default.WineBar)
                
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = currentTab == index,
                        onClick = { currentTab = index },
                        icon = { Icon(icons[index], title) },
                        label = { Text(title) },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = accentColor, selectedTextColor = accentColor)
                    )
                }
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
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel, accentColor: Color) {
    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = accentColor.copy(alpha = 0.1f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("🧠 Djoudini sagt:", color = accentColor, fontWeight = FontWeight.Bold)
                    Text(viewModel.djoudiniWisdom, fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White)
                }
            }
        }
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🎲", fontSize = 64.sp)
                    Text("Handy schütteln!", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text("Lass Djoudini entscheiden, was du als nächstes trinkst.", textAlign = TextAlign.Center, color = Color.Gray)
                }
            }
        }
        item {
            Button(
                onClick = { viewModel.isDrunkMode = !viewModel.isDrunkMode },
                modifier = Modifier.fillMaxWidth().height(if (viewModel.isDrunkMode) 80.dp else 56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (viewModel.isDrunkMode) Color.Red else Color(0xFF2E7D32))
            ) {
                Text(if (viewModel.isDrunkMode) "🍻 BETRUNKEN-MODUS AN" else "🍹 Betrunken-Modus AUS", fontSize = if (viewModel.isDrunkMode) 22.sp else 16.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
fun MenuScreen(viewModel: MainViewModel, accentColor: Color) {
    val drinks = DrinkDatabase.allDrinks
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(drinks) { drink ->
            Card(
                modifier = Modifier.fillMaxWidth().clickable { viewModel.addToBill(drink) },
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(50.dp).background(Color(0xFF0A0A0F), CircleShape), contentAlignment = Alignment.Center) {
                        Text(drink.emoji, fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(drink.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(drink.description, fontSize = 12.sp, color = Color.Gray, maxLines = 1)
                    }
                    Text("%.2f€".format(drink.avgPrice), fontWeight = FontWeight.Black, color = accentColor, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun BillScreen(viewModel: MainViewModel, accentColor: Color) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Meine Rechnung", fontWeight = FontWeight.Black, fontSize = 28.sp)
        LazyColumn(modifier = Modifier.weight(1f).padding(vertical = 16.dp)) {
            items(viewModel.billItems) { item ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(item.emoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.name, fontWeight = FontWeight.Bold)
                        Text(item.time, fontSize = 12.sp, color = Color.Gray)
                    }
                    Text("%.2f€".format(item.price), fontWeight = FontWeight.Bold)
                    IconButton(onClick = { viewModel.removeBillItem(item) }) {
                        Icon(Icons.Default.Delete, "Remove", tint = Color.Red.copy(alpha = 0.5f))
                    }
                }
                Divider(color = Color.DarkGray.copy(alpha = 0.2f))
            }
        }
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF252530))) {
            Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("GESAMT", fontWeight = FontWeight.Black, fontSize = 20.sp, modifier = Modifier.weight(1f))
                Text("%.2f€".format(viewModel.billTotal), fontWeight = FontWeight.Black, fontSize = 28.sp, color = accentColor)
            }
        }
    }
}

@Composable
fun BacScreen(viewModel: MainViewModel, accentColor: Color) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Promillerechner", fontWeight = FontWeight.Black, fontSize = 28.sp)
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = if (viewModel.bacValue > 0.5) Color(0x33FF0000) else Color(0xFF1A1A24))
        ) {
            Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("%.2f‰".format(viewModel.bacValue), fontSize = 72.sp, fontWeight = FontWeight.Black, color = if (viewModel.bacValue > 0.5) Color.Red else accentColor)
                Text(if (viewModel.bacValue > 0.5) "Fahren verboten! ⛔" else "Noch fit ✅", fontWeight = FontWeight.Bold)
            }
        }
        Text("Körpergewicht: ${viewModel.weight.toInt()}kg")
        Slider(value = viewModel.weight, onValueChange = { viewModel.weight = it }, valueRange = 40f..150f, colors = SliderDefaults.colors(thumbColor = accentColor, activeTrackColor = accentColor))
        
        Text("Stunden seit erstem Drink: ${viewModel.hoursSinceFirstDrink.toInt()}h")
        Slider(value = viewModel.hoursSinceFirstDrink, onValueChange = { viewModel.hoursSinceFirstDrink = it }, valueRange = 0f..12f, colors = SliderDefaults.colors(thumbColor = accentColor, activeTrackColor = accentColor))
        
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { viewModel.addBacDrink("Bier", 5.0, 500) }, modifier = Modifier.weight(1f)) { Text("+ Bier") }
            Button(onClick = { viewModel.addBacDrink("Shot", 40.0, 40) }, modifier = Modifier.weight(1f)) { Text("+ Shot") }
        }
    }
}

package com.djoudinis.barcalculator

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
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

            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFFD4AF37), // Gold
                    background = Color(0xFF0A0A0F),
                    surface = Color(0xFF1A1A24)
                )
            ) {
                // Background Sway Animation
                val infiniteTransition = rememberInfiniteTransition(label = "sway")
                val swayRotation by infiniteTransition.animateFloat(
                    initialValue = -viewModel.swayIntensity,
                    targetValue = viewModel.swayIntensity,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "swayRotation"
                )

                Surface(
                    modifier = Modifier.fillMaxSize().graphicsLayer {
                        rotationZ = if (viewModel.bacValue > 0.4) swayRotation else 0f
                        scaleX = if (viewModel.bacValue > 1.0) 1.05f else 1f
                        scaleY = if (viewModel.bacValue > 1.0) 1.05f else 1f
                    },
                    color = MaterialTheme.colorScheme.background
                ) {
                    BarCalculatorApp(viewModel)
                    
                    if (viewModel.showSobrietyCheck) {
                        SobrietyCheckDialog(viewModel)
                    }
                    
                    viewModel.lastSuggestedDrink?.let { drink ->
                        AlertDialog(
                            onDismissRequest = { viewModel.lastSuggestedDrink = null },
                            title = { Text("Zufalls-Vorschlag 🎲") },
                            text = { 
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                    Text(drink.emoji, fontSize = 64.sp)
                                    Text(drink.name, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                                    Text(drink.description, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Gray)
                                }
                            },
                            confirmButton = {
                                Button(onClick = { 
                                    viewModel.addToBill(drink)
                                    viewModel.lastSuggestedDrink = null 
                                }) { Text("Hinzufügen") }
                            },
                            dismissButton = {
                                TextButton(onClick = { viewModel.lastSuggestedDrink = null }) { Text("Nein danke") }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SobrietyCheckDialog(viewModel: MainViewModel) {
    var input by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { viewModel.showSobrietyCheck = false },
        title = { Text("Bist du noch fit? 🧐") },
        text = {
            Column {
                Text("Löse diese Aufgabe, um die Rechnung zu ändern:")
                Text(viewModel.mathProblem, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD4AF37), modifier = Modifier.padding(vertical = 16.dp).align(Alignment.CenterHorizontally))
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    singleLine = true,
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (input.toIntOrNull() == viewModel.mathAnswer) {
                    viewModel.showSobrietyCheck = false
                    viewModel.sobrietyAction?.invoke()
                    viewModel.sobrietyAction = null
                }
            }) { Text("Bestätigen") }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.showSobrietyCheck = false }) { Text("Abbrechen") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarCalculatorApp(viewModel: MainViewModel) {
    var currentTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("DJOUDINI'S BAR CALC", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Nie wieder abgezogen werden", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF0A0A0F))
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0A0A0F)) {
                NavigationBarItem(selected = currentTab == 0, onClick = { currentTab = 0 }, icon = { Icon(Icons.Default.Home, "Home") }, label = { Text("Home") })
                NavigationBarItem(selected = currentTab == 1, onClick = { currentTab = 1 }, icon = { Icon(Icons.Default.Receipt, "Bill") }, label = { Text("Bill") })
                NavigationBarItem(selected = currentTab == 2, onClick = { currentTab = 2 }, icon = { Icon(Icons.Default.LocalDrink, "Drinks") }, label = { Text("Drinks") })
                NavigationBarItem(selected = currentTab == 3, onClick = { currentTab = 3 }, icon = { Icon(Icons.Default.WineBar, "BAC") }, label = { Text("Promille") })
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (currentTab) {
                0 -> HomeScreen(viewModel)
                1 -> BillScreen(viewModel)
                2 -> DrinkListScreen(viewModel)
                3 -> BacScreen(viewModel)
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🥃", fontSize = 64.sp)
                    Text("Dein Bar-Buddy", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text(
                        "Schüttle dein Handy für einen Drink-Vorschlag!",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
        
        item {
            Button(
                onClick = { viewModel.isDrunkMode = !viewModel.isDrunkMode },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (viewModel.isDrunkMode) Color(0xFFD32F2F) else Color(0xFF2E7D32)
                )
            ) {
                Text(if (viewModel.isDrunkMode) "🍻 Betrunken-Modus AN" else "🍹 Betrunken-Modus AUS", fontSize = if (viewModel.isDrunkMode) 24.sp else 16.sp)
            }
        }

        if (viewModel.billItems.isNotEmpty()) {
            item {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A24))) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Receipt, "Bill", tint = Color(0xFFD4AF37))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Aktuelle Rechnung: ", modifier = Modifier.weight(1f))
                        Text("%.2f€".format(viewModel.billTotal), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun DrinkListScreen(viewModel: MainViewModel) {
    val drinks = DrinkDatabase.allDrinks
    LazyColumn {
        items(drinks) { drink ->
            DrinkItem(drink) { viewModel.addToBill(drink) }
        }
    }
}

@Composable
fun DrinkItem(drink: Drink, onAdd: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp).clickable { onAdd() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A24))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(drink.emoji, fontSize = 36.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(drink.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(drink.description, fontSize = 13.sp, color = Color.Gray)
            }
            Text("%.2f€".format(drink.avgPrice), fontWeight = FontWeight.Bold, color = Color(0xFFD4AF37), fontSize = 18.sp)
        }
    }
}

@Composable
fun BillScreen(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Meine Rechnung", fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.billItems) { item ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(item.emoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.name, fontWeight = FontWeight.Bold)
                        Text(item.time, fontSize = 12.sp, color = Color.Gray)
                    }
                    Text("%.2f€".format(item.price), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    IconButton(onClick = { viewModel.checkSobrietyAndRun { viewModel.removeBillItem(item) } }) {
                        Icon(Icons.Default.Delete, "Remove", tint = Color(0x66FF0000))
                    }
                }
                HorizontalDivider(color = Color(0xFF252530))
            }
        }
        
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF252530))) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Gesamt", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.weight(1f))
                Text("%.2f€".format(viewModel.billTotal), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = Color(0xFFD4AF37))
            }
        }
        
        Button(
            onClick = { viewModel.checkSobrietyAndRun { viewModel.clearBill() } },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0x22FF0000))
        ) {
            Text("Rechnung löschen", color = Color(0xFFFF5252))
        }
    }
}

@Composable
fun BacScreen(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Promillerechner", fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = if (viewModel.bacValue > 0.5) Color(0x33FF0000) else Color(0xFF1A1A24))
        ) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("%.2f‰".format(viewModel.bacValue), fontSize = 64.sp, fontWeight = FontWeight.ExtraBold, color = if (viewModel.bacValue > 0.5) Color.Red else Color(0xFFD4AF37))
                Text(if (viewModel.bacValue > 0.5) "Fahren verboten! ⛔" else "Noch im grünen Bereich ✅", color = Color.Gray)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("Körpergewicht: ${viewModel.weight.toInt()}kg")
        Slider(value = viewModel.weight, onValueChange = { viewModel.weight = it }, valueRange = 40f..150f)
        
        Text("Zeit seit erstem Drink: ${viewModel.hoursSinceFirstDrink.toInt()}h")
        Slider(value = viewModel.hoursSinceFirstDrink, onValueChange = { viewModel.hoursSinceFirstDrink = it }, valueRange = 0f..12f)

        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), gap = 8.dp) {
            Button(onClick = { viewModel.addBacDrink("Bier", 5.0, 500) }, modifier = Modifier.weight(1f)) { Text("+ Bier") }
            Button(onClick = { viewModel.addBacDrink("Shot", 40.0, 40) }, modifier = Modifier.weight(1f)) { Text("+ Shot") }
        }
        
        Button(
            onClick = { /* Call Taxi Action */ },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37))
        ) {
            Icon(Icons.Default.LocalTaxi, "Taxi")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Taxi rufen", fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

package com.djoudinis.barcalculator

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Vibrator
import android.os.VibrationEffect
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
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
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        
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
                                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                            }
                        }
                    }
                    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                }
                sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI)
                onDispose { sensorManager.unregisterListener(listener) }
            }

            // Neon Party Mode Color Animation
            val infiniteTransition = rememberInfiniteTransition(label = "party")
            val neonColor by infiniteTransition.animateColor(
                initialValue = viewModel.partyColor.copy(alpha = 0.3f),
                targetValue = viewModel.partyColor,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "neonColor"
            )

            val swayRotation by infiniteTransition.animateFloat(
                initialValue = -viewModel.swayIntensity,
                targetValue = viewModel.swayIntensity,
                animationSpec = infiniteRepeatable(
                    animation = tween(2500, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "swayRotation"
            )

            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = viewModel.partyColor,
                    background = Color(0xFF0A0A0F),
                    surface = Color(0xFF1A1A24)
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize().graphicsLayer {
                        rotationZ = if (viewModel.bacValue > 0.4) swayRotation else 0f
                        scaleX = if (viewModel.bacValue > 1.2) 1.05f else 1f
                        scaleY = if (viewModel.bacValue > 1.2) 1.05f else 1f
                    },
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Dynamic Gradient Background when "in the zone"
                    if (viewModel.bacValue > 0.5) {
                        Box(modifier = Modifier.fillMaxSize().background(
                            Brush.verticalGradient(listOf(Color(0xFF0A0A0F), neonColor.copy(alpha = 0.15f)))
                        ))
                    }

                    BarCalculatorApp(viewModel, vibrator)
                    
                    if (viewModel.showSobrietyCheck) {
                        SobrietyCheckDialog(viewModel)
                    }
                    
                    viewModel.lastSuggestedDrink?.let { drink ->
                        AlertDialog(
                            onDismissRequest = { viewModel.lastSuggestedDrink = null },
                            title = { Text("Zufalls-Vorschlag 🎲", color = viewModel.partyColor) },
                            text = { 
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                    Text(drink.emoji, fontSize = 80.sp)
                                    Text(drink.name, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White)
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
        title = { Text("Bist du noch fit? 🧐", color = Color.Red) },
        text = {
            Column {
                Text("Löse diese Aufgabe, um fortzufahren:")
                Text(viewModel.mathProblem, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, modifier = Modifier.padding(vertical = 16.dp).align(Alignment.CenterHorizontally))
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    singleLine = true,
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color(0xFF252530))
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
fun BarCalculatorApp(viewModel: MainViewModel, vibrator: Vibrator) {
    var currentTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("DJOUDINI'S BAR CALC", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, letterSpacing = 2.sp)
                        Text("Nie wieder abgezogen werden", fontSize = 12.sp, color = viewModel.partyColor)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0A0A0F).copy(alpha = 0.9f)) {
                val tabs = listOf("Home", "Bill", "Drinks", "BAC")
                val icons = listOf(Icons.Default.Home, Icons.Default.Receipt, Icons.Default.LocalDrink, Icons.Default.WineBar)
                
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = currentTab == index,
                        onClick = { 
                            currentTab = index 
                            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                        },
                        icon = { Icon(icons[index], title) },
                        label = { Text(title) },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = viewModel.partyColor, selectedTextColor = viewModel.partyColor)
                    )
                }
            }
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (currentTab) {
                0 -> HomeScreen(viewModel, vibrator)
                1 -> BillScreen(viewModel, vibrator)
                2 -> DrinkListScreen(viewModel, vibrator)
                3 -> BacScreen(viewModel, vibrator)
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel, vibrator: Vibrator) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            // Djoudini's Wisdom Card
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = viewModel.partyColor.copy(alpha = 0.1f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, viewModel.partyColor.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🧠 Djoudini sagt:", color = viewModel.partyColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        viewModel.djoudiniWisdom,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🎲", fontSize = 72.sp)
                    Text("Dein Bar-Buddy", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text(
                        "Schüttle dein Handy für einen Drink-Vorschlag!",
                        textAlign = TextAlign.Center,
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
        
        item {
            Button(
                onClick = { 
                    viewModel.isDrunkMode = !viewModel.isDrunkMode 
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp).height(if (viewModel.isDrunkMode) 80.dp else 56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (viewModel.isDrunkMode) Color(0xFFD32F2F) else Color(0xFF2E7D32)
                )
            ) {
                Text(
                    if (viewModel.isDrunkMode) "🍻 BETRUNKEN-MODUS AN" else "🍹 Betrunken-Modus AUS",
                    fontSize = if (viewModel.isDrunkMode) 22.sp else 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        if (viewModel.billItems.isNotEmpty()) {
            item {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A24))) {
                    Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Receipt, "Bill", tint = viewModel.partyColor)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Aktuelle Rechnung: ", modifier = Modifier.weight(1f), color = Color.LightGray)
                        Text("%.2f€".format(viewModel.billTotal), fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = viewModel.partyColor)
                    }
                }
            }
        }
    }
}

@Composable
fun DrinkListScreen(viewModel: MainViewModel, vibrator: Vibrator) {
    val drinks = DrinkDatabase.allDrinks
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(drinks) { drink ->
            DrinkItem(drink, viewModel.partyColor) { 
                viewModel.addToBill(drink)
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            }
        }
    }
}

@Composable
fun DrinkItem(drink: Drink, accentColor: Color, onAdd: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onAdd() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A24)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(56.dp).background(Color(0xFF0A0A0F), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Text(drink.emoji, fontSize = 32.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(drink.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                Text(drink.description, fontSize = 13.sp, color = Color.Gray, maxLines = 1)
            }
            Text("%.2f€".format(drink.avgPrice), fontWeight = FontWeight.ExtraBold, color = accentColor, fontSize = 18.sp)
        }
    }
}

@Composable
fun BillScreen(viewModel: MainViewModel, vibrator: Vibrator) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Meine Rechnung", fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, modifier = Modifier.padding(bottom = 16.dp))
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.billItems) { item ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(item.emoji, fontSize = 32.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(item.time, fontSize = 12.sp, color = Color.Gray)
                    }
                    Text("%.2f€".format(item.price), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    IconButton(onClick = { 
                        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                        viewModel.checkSobrietyAndRun { viewModel.removeBillItem(item) } 
                    }) {
                        Icon(Icons.Default.Delete, "Remove", tint = Color(0x99FF0000))
                    }
                }
                HorizontalDivider(color = Color(0xFF252530), thickness = 1.dp)
            }
        }
        
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF252530))) {
            Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Gesamt", fontWeight = FontWeight.Bold, fontSize = 22.sp, modifier = Modifier.weight(1f))
                Text("%.2f€".format(viewModel.billTotal), fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, color = viewModel.partyColor)
            }
        }
        
        Button(
            onClick = { 
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                viewModel.checkSobrietyAndRun { viewModel.clearBill() } 
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0x22FF0000))
        ) {
            Text("RECHNUNG LÖSCHEN", color = Color(0xFFFF5252), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BacScreen(viewModel: MainViewModel, vibrator: Vibrator) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Promillerechner", fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, modifier = Modifier.padding(bottom = 16.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = if (viewModel.bacValue > 0.5) Color(0x33FF0000) else Color(0xFF1A1A24)),
            border = if (viewModel.bacValue > 0.5) androidx.compose.foundation.BorderStroke(2.dp, Color.Red) else null
        ) {
            Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("%.2f‰".format(viewModel.bacValue), fontSize = 72.sp, fontWeight = FontWeight.ExtraBold, color = if (viewModel.bacValue > 0.5) Color.Red else viewModel.partyColor)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    if (viewModel.bacValue > 0.5) "FAHREN VERBOTEN! ⛔" else "Noch im grünen Bereich ✅", 
                    fontWeight = FontWeight.Bold,
                    color = if (viewModel.bacValue > 0.5) Color.Red else Color.Gray
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Körpergewicht: ${viewModel.weight.toInt()}kg", fontWeight = FontWeight.Bold)
        Slider(value = viewModel.weight, onValueChange = { viewModel.weight = it }, valueRange = 40f..150f, colors = SliderDefaults.colors(thumbColor = viewModel.partyColor, activeTrackColor = viewModel.partyColor))
        
        Text("Zeit seit erstem Drink: ${viewModel.hoursSinceFirstDrink.toInt()}h", fontWeight = FontWeight.Bold)
        Slider(value = viewModel.hoursSinceFirstDrink, onValueChange = { viewModel.hoursSinceFirstDrink = it }, valueRange = 0f..12f, colors = SliderDefaults.colors(thumbColor = viewModel.partyColor, activeTrackColor = viewModel.partyColor))

        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { 
                viewModel.addBacDrink("Bier", 5.0, 500)
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) { Text("+ Bier") }
            Button(onClick = { 
                viewModel.addBacDrink("Shot", 40.0, 40)
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) { Text("+ Shot") }
        }
        
        Button(
            onClick = { 
                vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
                /* Intent to call taxi could be here */ 
            },
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp).height(64.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = viewModel.partyColor)
        ) {
            Icon(Icons.Default.LocalTaxi, "Taxi", tint = Color.Black)
            Spacer(modifier = Modifier.width(12.dp))
            Text("TAXI RUFEN", fontWeight = FontWeight.Black, color = Color.Black, fontSize = 18.sp)
        }
    }
}

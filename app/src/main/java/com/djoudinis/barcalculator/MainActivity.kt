package com.djoudinis.barcalculator

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.djoudinis.barcalculator.data.Drink
import com.djoudinis.barcalculator.ui.MainViewModel
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            }
            else -> {
                // No location access granted.
                Log.e("MainActivity", "Location permissions not granted.")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(application)
            )

            // Request location permissions when the app starts
            LaunchedEffect(Unit) {
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
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
                }
            }
        }
    }
}

// A simple ViewModelFactory to pass the Application instance
class MainViewModelFactory(private val application: Application) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarCalculatorApp(viewModel: MainViewModel, accentColor: Color) {
    var currentTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0A0A0F).copy(alpha = 0.95f)) {
                NavigationBarItem(
                    selected = currentTab == 0, onClick = { currentTab = 0 },
                    icon = { Icon(Icons.Filled.Home, "Home") }, label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = currentTab == 1, onClick = { currentTab = 1 },
                    icon = { Icon(Icons.Filled.Receipt, "Bill") }, label = { Text("Rechnung", maxLines = 1, overflow = TextOverflow.Ellipsis) }
                )
                NavigationBarItem(
                    selected = currentTab == 2, onClick = { currentTab = 2 },
                    icon = { Icon(Icons.Filled.LocalDrink, "Menu") }, label = { Text("Menü") }
                )
                NavigationBarItem(
                    selected = currentTab == 3, onClick = { currentTab = 3 },
                    icon = { Icon(Icons.Filled.Settings, "Set") }, label = { Text("Set") }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize().background(
            Brush.verticalGradient(listOf(Color(0xFF0A0A0F), Color(0xFF14141F)))
        )) {
            when (currentTab) {
                0 -> HomeScreen(viewModel, accentColor)
                1 -> BillScreen(viewModel)
                2 -> MenuScreen(viewModel)
                3 -> SettingsScreen(viewModel)
            }
        }
    }

    // --- Dialogs ---
    viewModel.selectedDrinkForPrice?.let { drink ->
        PriceEntryDialog(drink, viewModel) { price ->
            viewModel.addToBill(drink, price)
            viewModel.selectedDrinkForPrice = null
        }
    }

    if (viewModel.showNearbyPlacesDialog) {
        NearbyPlacesDialog(
            places = viewModel.nearbyPlaces,
            onPlaceSelected = { placeName ->
                viewModel.addOrSwitchBar(placeName)
                viewModel.showNearbyPlacesDialog = false
            },
            onDismiss = { viewModel.showNearbyPlacesDialog = false }
        )
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel, accentColor: Color) {
    LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("📍 Aktuelle Location", color = accentColor, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = viewModel.currentBarVisit?.barName ?: "Keine Bar ausgewählt",
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        IconButton(onClick = {
                            viewModel.fetchNearbyPlaces()
                        }) {
                            Icon(Icons.Filled.EditLocation, "Change Location", tint = accentColor)
                        }
                    }
                }
            }
        }
        // Other HomeScreen items...
        item {
            Card(modifier = Modifier.fillMaxWidth().clickable { viewModel.getRandomSuggestion() }) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("🎲", fontSize = 40.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Bar-Würfel", fontWeight = FontWeight.Bold)
                        Text("Tippen für Vorschlag", fontSize = 12.sp, color = Color.Gray)
                    }
                    Text(viewModel.lastSuggestedDrink?.emoji ?: "-", fontSize = 32.sp, fontWeight = FontWeight.Black, color = accentColor)
                }
            }
        }

        item {
            Button(
                onClick = { /* TODO: Implement water logging */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
            ) {
                Icon(Icons.Filled.WaterDrop, "Water")
                Spacer(modifier = Modifier.width(8.dp))
                Text("WASSER LOGGEN", fontWeight = FontWeight.Bold)
            }
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

@Composable
fun BillScreen(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Rechnungen", fontWeight = FontWeight.Black, fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.barVisits) { visit ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(visit.barName, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, modifier = Modifier.weight(1f))
                            Text("%.2f€".format(visit.billItems.sumOf { it.price }), fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                        }
                        if (visit.billItems.isNotEmpty()) {
                            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                            visit.billItems.forEach { item ->
                                Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text(item.emoji, modifier = Modifier.padding(end = 8.dp), fontSize = 18.sp)
                                    Text(item.name, modifier = Modifier.weight(1f))
                                    Text("%.2f€".format(item.price))
                                }
                            }
                        }
                    }
                }
            }
        }
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("GESAMT", fontWeight = FontWeight.Black, modifier = Modifier.weight(1f))
                    Text("%.2f€".format(viewModel.billTotal), fontWeight = FontWeight.Black, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Einstellungen", fontWeight = FontWeight.Black, fontSize = 24.sp, modifier = Modifier.padding(bottom = 24.dp))

        Text("Gewicht: ${viewModel.weight.toInt()}kg", fontWeight = FontWeight.Bold)
        Slider(
            value = viewModel.weight,
            onValueChange = { viewModel.weight = it },
            onValueChangeFinished = { viewModel.saveUserSettings() },
            valueRange = 40f..150f
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Geschlecht", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = viewModel.isMale, onClick = { if (!viewModel.isMale) { viewModel.isMale = true; viewModel.saveUserSettings() } })
                Text("Männlich")
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(selected = !viewModel.isMale, onClick = { if (viewModel.isMale) { viewModel.isMale = false; viewModel.saveUserSettings() } })
                Text("Weiblich")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NearbyPlacesDialog(
    places: List<Pair<String, String>>,
    onPlaceSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Bar in der Nähe auswählen") },
        text = {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                if (places.isEmpty()) {
                    item {
                        Text("Keine Bars in der Nähe gefunden.", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    }
                } else {
                    items(places) { (_, placeName) ->
                        ListItem(
                            headlineContent = { Text(placeName) },
                            modifier = Modifier.clickable { onPlaceSelected(placeName) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Abbrechen") }
        }
    )
}

@Composable
fun PriceEntryDialog(drink: Drink, viewModel: MainViewModel, onConfirm: (Double) -> Unit) {
    var priceInput by remember { mutableStateOf("") }
    val price = priceInput.toDoubleOrNull()

    AlertDialog(
        onDismissRequest = { viewModel.selectedDrinkForPrice = null },
        title = { Text("Preis für ${drink.name}") },
        text = {
            OutlinedTextField(
                value = priceInput,
                onValueChange = { priceInput = it },
                label = { Text("Preis in €") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (price != null) {
                        onConfirm(price)
                    }
                },
                enabled = price != null
            ) {
                Text("Hinzufügen")
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.selectedDrinkForPrice = null }) {
                Text("Abbrechen")
            }
        }
    )
}

// Dummy MenuScreen, you can expand this
@Composable
fun MenuScreen(viewModel: MainViewModel) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(viewModel.filteredDrinks) { drink ->
            ListItem(
                headlineContent = { Text(drink.name) },
                leadingContent = { Text(drink.emoji, fontSize = 24.sp) },
                modifier = Modifier.clickable { viewModel.selectedDrinkForPrice = drink }
            )
        }
    }
}
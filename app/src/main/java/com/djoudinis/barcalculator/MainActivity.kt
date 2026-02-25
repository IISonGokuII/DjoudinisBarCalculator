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
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {

    private lateinit var placesClient: PlacesClient
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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Places SDK
        Places.initialize(applicationContext, "YOUR_API_KEY") // TODO: Add your API Key
        placesClient = Places.createClient(this)
        
        
        setContent {
            val viewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(application)
            )
            
            LaunchedEffect(Unit) {
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
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
                    BarCalculatorApp(viewModel, accentColor, placesClient)
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
fun BarCalculatorApp(viewModel: MainViewModel, accentColor: Color, placesClient: PlacesClient) {
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
                0 -> HomeScreen(viewModel, accentColor, placesClient)
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

    if (viewModel.showNearbyPlaces) {
        NearbyPlacesDialog(
            places = viewModel.nearbyPlaces,
            onPlaceSelected = { placeName ->
                viewModel.addOrSwitchBar(placeName)
                viewModel.showNearbyPlaces = false
            },
            onDismiss = { viewModel.showNearbyPlaces = false }
        )
    }
}

@SuppressLint("MissingPermission")
fun fetchNearbyPlaces(
    context: Context,
    placesClient: PlacesClient,
    onResult: (List<Pair<String, String>>) -> Unit
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val placeFields = listOf(Place.Field.ID, Place.Field.NAME)
            val request = FindCurrentPlaceRequest.newInstance(placeFields)
            placesClient.findCurrentPlace(request).addOnSuccessListener { response ->
                val places = response.placeLikelihoods.mapNotNull { it.place.name }
                    .map { it to it } // Simple name to ID mapping for this example
                onResult(places)
            }.addOnFailureListener { exception ->
                Log.e("MainActivity", "Place not found: ${exception.message}")
                onResult(emptyList())
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel, accentColor: Color, placesClient: PlacesClient) {
    val context = LocalContext.current
    
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
                            fetchNearbyPlaces(context, placesClient) { places ->
                                viewModel.nearbyPlaces = places
                                viewModel.showNearbyPlaces = true
                            }
                        }) {
                            Icon(Icons.Filled.EditLocation, "Change Location", tint = accentColor)
                        }
                    }
                }
            }
        }
        // Other HomeScreen items...
    }
}

@Composable
fun BillScreen(viewModel: MainViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Rechnungen", fontWeight = FontWeight.Black, fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        }
        
        items(viewModel.barVisits) { visit ->
            Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row {
                        Text(visit.barName, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.weight(1f))
                        Text("%.2f€".format(visit.billItems.sumOf { it.price }), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    visit.billItems.forEach { item ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text(item.emoji, modifier = Modifier.padding(end = 8.dp))
                            Text(item.name, modifier = Modifier.weight(1f))
                            Text("%.2f€".format(item.price))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Einstellungen", fontWeight = FontWeight.Black, fontSize = 24.sp, modifier = Modifier.padding(bottom = 24.dp))
        
        Text("Gewicht: ${viewModel.weight.toInt()}kg")
        Slider(
            value = viewModel.weight,
            onValueChange = { viewModel.weight = it },
            onValueChangeFinished = { viewModel.saveUserSettings() },
            valueRange = 40f..150f
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Geschlecht", modifier = Modifier.weight(1f))
            RadioButton(selected = viewModel.isMale, onClick = { if (!viewModel.isMale) { viewModel.isMale = true; viewModel.saveUserSettings() } })
            Text("Männlich")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(selected = !viewModel.isMale, onClick = { if (viewModel.isMale) { viewModel.isMale = false; viewModel.saveUserSettings() } })
            Text("Weiblich")
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
            LazyColumn {
                items(places) { (_, placeName) ->
                    ListItem(
                        headlineContent = { Text(placeName) },
                        modifier = Modifier.clickable { onPlaceSelected(placeName) }
                    )
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

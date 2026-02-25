package com.djoudinis.barcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.djoudinis.barcalculator.data.Drink
import com.djoudinis.barcalculator.data.DrinkDatabase
import com.djoudinis.barcalculator.ui.MainViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFFD4AF37), // Gold
                    background = Color(0xFF0A0A0F),
                    surface = Color(0xFF1A1A24)
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BarCalculatorApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarCalculatorApp() {
    val viewModel: MainViewModel = viewModel()
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0A0A0F)
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0A0A0F)) {
                NavigationBarItem(
                    selected = currentTab == 0,
                    onClick = { currentTab = 0 },
                    icon = { Icon(Icons.Default.Home, "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = currentTab == 1,
                    onClick = { currentTab = 1 },
                    icon = { Icon(Icons.Default.Receipt, "Bill") },
                    label = { Text("Bill") }
                )
                NavigationBarItem(
                    selected = currentTab == 2,
                    onClick = { currentTab = 2 },
                    icon = { Icon(Icons.Default.LocalDrink, "Drinks") },
                    label = { Text("Drinks") }
                )
                NavigationBarItem(
                    selected = currentTab == 3,
                    onClick = { currentTab = 3 },
                    icon = { Icon(Icons.Default.WineBar, "BAC") },
                    label = { Text("Promille") }
                )
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
                    Text("🥃", fontSize = 48.sp)
                    Text("Dein Bar-Buddy", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(
                        "Behalte den Überblick über deine Rechnung, prüfe Preise und bleib smart.",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
        
        item {
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                Button(
                    onClick = { viewModel.isDrunkMode = !viewModel.isDrunkMode },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (viewModel.isDrunkMode) Color(0xFFD32F2F) else Color(0xFF2E7D32)
                    )
                ) {
                    Text(if (viewModel.isDrunkMode) "🍻 Betrunken-Modus AN" else "🍹 Betrunken-Modus AUS")
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
            DrinkItem(drink) {
                viewModel.addToBill(drink)
            }
        }
    }
}

@Composable
fun DrinkItem(drink: Drink, onAdd: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp).clickable { onAdd() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A24))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(drink.emoji, fontSize = 32.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(drink.name, fontWeight = FontWeight.Bold)
                Text(drink.description, fontSize = 12.sp, color = Color.Gray)
            }
            Text("%.2f€".format(drink.avgPrice), fontWeight = FontWeight.Bold, color = Color(0xFFD4AF37))
        }
    }
}

@Composable
fun BillScreen(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Meine Rechnung", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.billItems) { item ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.emoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.name, fontWeight = FontWeight.Medium)
                        Text(item.time, fontSize = 12.sp, color = Color.Gray)
                    }
                    Text("%.2f€".format(item.price), fontWeight = FontWeight.Bold)
                    IconButton(onClick = { viewModel.removeBillItem(item) }) {
                        Icon(Icons.Default.Delete, "Remove", tint = Color.Red)
                    }
                }
                HorizontalDivider(color = Color.DarkGray)
            }
        }
        
        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF252530))
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Gesamt", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.weight(1f))
                Text("%.2f€".format(viewModel.billTotal), fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFFD4AF37))
            }
        }
    }
}

@Composable
fun BacScreen(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Promillerechner", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
        
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("%.2f‰".format(viewModel.bacValue), fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFD32F2F))
                Text("Geschätzter Blutalkoholwert", fontSize = 14.sp, color = Color.Gray)
            }
        }
        
        Text("Körpergewicht: ${viewModel.weight.toInt()}kg", modifier = Modifier.padding(top = 16.dp))
        Slider(
            value = viewModel.weight,
            onValueChange = { viewModel.weight = it },
            valueRange = 40f..150f
        )
        
        Text("Zeit seit erstem Drink: ${viewModel.hoursSinceFirstDrink.toInt()}h")
        Slider(
            value = viewModel.hoursSinceFirstDrink,
            onValueChange = { viewModel.hoursSinceFirstDrink = it },
            valueRange = 0f..12f
        )

        Button(
            onClick = { viewModel.addBacDrink("Bier", 5.0, 500) },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("+ 0,5L Bier hinzufügen")
        }
    }
}

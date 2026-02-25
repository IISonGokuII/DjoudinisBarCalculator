package com.djoudinis.barcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.djoudinis.barcalculator.data.DrinkDatabase
import com.djoudinis.barcalculator.ui.MainViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFFD4AF37),
                    background = Color(0xFF0A0A0F),
                    surface = Color(0xFF1A1A24)
                )
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val viewModel: MainViewModel = viewModel()
                    var currentTab by remember { mutableIntStateOf(0) }

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("BAR CALCULATOR", fontWeight = FontWeight.Bold) },
                                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0A0A0F))
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
                                    icon = { Icon(Icons.Default.WineBar, "BAC") },
                                    label = { Text("Promille") }
                                )
                            }
                        }
                    ) { padding ->
                        Box(modifier = Modifier.padding(padding)) {
                            when (currentTab) {
                                0 -> MainList(viewModel)
                                1 -> BillList(viewModel)
                                2 -> BacCalculator(viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainList(viewModel: MainViewModel) {
    val drinks = DrinkDatabase.allDrinks
    LazyColumn {
        item {
            Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Djoudini sagt:", fontWeight = FontWeight.Bold, color = viewModel.partyColor)
                    Text(viewModel.djoudiniWisdom)
                }
            }
        }
        items(drinks) { drink ->
            ListItem(
                headlineContent = { Text(drink.name, fontWeight = FontWeight.Bold) },
                supportingContent = { Text(drink.description) },
                leadingContent = { Text(drink.emoji, fontSize = 24.sp) },
                trailingContent = { Text("%.2f€".format(drink.avgPrice), color = viewModel.partyColor) },
                modifier = Modifier.clickable { viewModel.addToBill(drink) }
            )
        }
    }
}

@Composable
fun BillList(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Rechnung", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.billItems) { item ->
                ListItem(
                    headlineContent = { Text(item.name) },
                    trailingContent = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("%.2f€".format(item.price))
                            IconButton(onClick = { viewModel.removeBillItem(item) }) {
                                Icon(Icons.Default.Delete, "Delete", tint = Color.Red)
                            }
                        }
                    }
                )
            }
        }
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(16.dp)) {
                Text("Total", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text("%.2f€".format(viewModel.billTotal), fontWeight = FontWeight.Bold, color = Color(0xFFD4AF37))
            }
        }
    }
}

@Composable
fun BacCalculator(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Promille Rechner", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("%.2f‰".format(viewModel.bacValue), fontSize = 48.sp, fontWeight = FontWeight.Bold, color = viewModel.partyColor)
            }
        }
        Text("Gewicht: ${viewModel.weight.toInt()}kg")
        Slider(value = viewModel.weight, onValueChange = { viewModel.weight = it }, valueRange = 40f..150f)
        
        Text("Stunden: ${viewModel.hoursSinceFirstDrink.toInt()}h")
        Slider(value = viewModel.hoursSinceFirstDrink, onValueChange = { viewModel.hoursSinceFirstDrink = it }, valueRange = 0f..12f)
        
        Button(onClick = { viewModel.addBacDrink(5.0, 500) }, modifier = Modifier.fillMaxWidth()) {
            Text("0.5L Bier hinzufügen")
        }
    }
}

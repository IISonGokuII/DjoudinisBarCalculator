package com.djoudinis.barcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.djoudinis.barcalculator.ui.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val vm: MainViewModel = viewModel()
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Bar Calculator", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Rechnung: %.2f€".format(vm.billTotal))
                        Button(onClick = { vm.addDrink(10.5) }) { Text("+ 10.50€ Drink") }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Pegel: %.2f‰".format(vm.bacValue))
                        Button(onClick = { vm.addAlcohol() }) { Text("Trinken") }
                    }
                }
            }
        }
    }
}

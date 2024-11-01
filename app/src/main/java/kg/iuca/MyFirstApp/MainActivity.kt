package kg.iuca.MyFirstApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Task5and6()
                }
            }
        }
    }
}

@Composable
fun Task5and6() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "calculator_screen") {
        composable("calculator_screen") { CalculatorScreen(navController) }
        composable("second_screen") { SecondScreen(navController) }
    }
}

@Composable
fun CalculatorScreen(navController: NavController) {
    var displayText by remember { mutableStateOf("0") }
    var firstNumber by remember { mutableStateOf(0.0) }
    var operation by remember { mutableStateOf("") }
    var isSecondNumber by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = displayText,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val rows = listOf(
                listOf("7", "8", "9", "/"),
                listOf("4", "5", "6", "*"),
                listOf("1", "2", "3", "-"),
                listOf("0", ".", "=", "+")
            )
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { label ->
                        Button(
                            onClick = {
                                handleButtonClick(
                                    label, displayText,
                                    { newText -> displayText = newText },
                                    firstNumber,
                                    { newFirstNumber -> firstNumber = newFirstNumber },
                                    operation,
                                    { newOperation -> operation = newOperation },
                                    isSecondNumber,
                                    { newIsSecondNumber -> isSecondNumber = newIsSecondNumber }
                                )
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = label)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("second_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Перейти на второй экран")
        }
    }
}

@Composable
fun SecondScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(text = "Второй экран")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Вернуться")
        }
    }
}

fun handleButtonClick(
    label: String,
    displayText: String,
    updateDisplayText: (String) -> Unit,
    currentFirstNumber: Double,
    updateFirstNumber: (Double) -> Unit,
    currentOperation: String,
    updateOperation: (String) -> Unit,
    isSecondNumber: Boolean,
    updateIsSecondNumber: (Boolean) -> Unit
) {
    try {
        when (label) {
            in "0".."9", "." -> {
                if (displayText == "0" || isSecondNumber) {
                    updateDisplayText(label)
                    updateIsSecondNumber(false)
                } else {
                    updateDisplayText(displayText + label)
                }
            }
            "/", "*", "-", "+" -> {
                updateFirstNumber(displayText.toDouble())
                updateOperation(label)
                updateIsSecondNumber(true)
                updateDisplayText("0")
            }
            "=" -> {
                val secondNumber = displayText.toDouble()
                val result = when (currentOperation) {
                    "+" -> currentFirstNumber + secondNumber
                    "-" -> currentFirstNumber - secondNumber
                    "*" -> currentFirstNumber * secondNumber
                    "/" -> if (secondNumber != 0.0) currentFirstNumber / secondNumber else Double.NaN
                    else -> 0.0
                }
                updateDisplayText(result.toString())
                updateIsSecondNumber(false)
            }
            else -> {
                updateDisplayText("Error")
            }
        }
    } catch (e: Exception) {
        updateDisplayText("Error")
    }
}

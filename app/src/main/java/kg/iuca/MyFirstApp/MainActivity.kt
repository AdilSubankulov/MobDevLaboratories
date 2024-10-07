package kg.iuca.MyFirstApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kg.iuca.MyFirstApp.ui.theme.MyFirstAppTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirstAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Task1And2()
                }
            }
        }
    }
    @Composable
    fun Task1And2() {

        val countNum = remember { mutableStateOf(0) }
        val helloWorld = remember { mutableStateOf("Привет, мир!") }

        Column {
            Text(text = helloWorld.value)
            Button(onClick = {
                helloWorld.value = "Кнопка нажата"
            }) {
                Text("Нажми на меня")
            }



            Text(text = "Counting number: ${countNum.value}")
            Button(onClick = {
                countNum.value += 1
            }) {
                Text("Я счетчик нажатий")
            }
        }
    }
}

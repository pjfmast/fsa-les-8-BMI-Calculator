package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bmicalculator.ui.theme.BMICalculatorTheme
import com.example.bmicalculator.ui.theme.Purple80
import kotlin.math.pow
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMICalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Purple80//MaterialTheme.colorScheme.primary
                ) {
                    BmiLayout()
                }
            }
        }
    }
}

@Composable
fun BmiLayout(modifier: Modifier = Modifier) {
    var lengthInput by remember { mutableStateOf("170") }
    var weightInput by remember { mutableStateOf("60") }

    val length = lengthInput.toIntOrNull() ?: 0
    val weight = weightInput.toIntOrNull() ?: 0
    val bmi = calculateBmi(length, weight)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            EditNumberField(
                label = stringResource(R.string.length),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                value = lengthInput,
                onValueChanged = { lengthInput = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            EditNumberField(
                label = stringResource(R.string.weight),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done // IME: Input Method Editors
                ),
                value = weightInput,
                onValueChanged = { weightInput = it }
            )
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = stringResource(R.string.your_bmi, bmi),
                modifier.padding(bottom = 20.dp)
            )
        }
    }
}

fun calculateBmi(length: Int, weight: Int): String {
    val lengthInMeter = length / 100.0
    val bmi = (weight / (lengthInMeter.pow(2))).round(1)

    return bmi.toString()
}
// see: https://discuss.kotlinlang.org/t/how-do-you-round-a-number-to-n-decimal-places/8843/2
fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

@Composable
fun EditNumberField(
    label: String,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChanged,
        label = { Text(label) },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun BmiLayoutPreview() {
    BMICalculatorTheme {
        BmiLayout()
    }
}
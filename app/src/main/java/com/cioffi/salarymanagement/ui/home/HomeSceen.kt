package com.cioffi.salarymanagement.ui.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cioffi.salarymanagement.R
import com.cioffi.salarymanagement.ui.theme.SalaryManagementTheme
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import kotlin.math.roundToInt


@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top){
        EditNumberField(homeViewModel.salary, onSalaryChanged = {homeViewModel.updateSalary(it)})
        AmountText(label = stringResource(R.string.fixed_needs),
            value =homeUiState.fixedNeeds,
            slider = homeUiState.fixedNeedsPercentage,
            changePerc = homeViewModel::onNeedsPercChange )
        AmountText(label = stringResource(R.string.wants),
            value =homeUiState.wants,
            slider = homeUiState.wantsPercentage,
            changePerc = homeViewModel::onWantsPercChange )
        AmountText(label = stringResource(R.string.savings),
            value =homeUiState.savings,
            slider = homeUiState.savingsPercentage,
            changePerc = homeViewModel::onSavingPercChange )
        OutlinedButton(
            onClick = { homeViewModel.calcPercentage() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.calculate),
                color = colorScheme.secondary,
                fontSize = 16.sp
            )
        }
        IconButton(onClick = { homeViewModel.onClear() }) {
            Icon(Icons.Filled.Clear, contentDescription = "Clear")
        }
    }
}

@Composable
fun EditNumberField(salary : String, onSalaryChanged: (String) -> Unit,modifier: Modifier = Modifier) {

    OutlinedTextField(
        value = salary,
        singleLine = true,
        shape = shapes.large,
        modifier = Modifier.fillMaxWidth(),

        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorScheme.surface,
            unfocusedContainerColor = colorScheme.surface,
            disabledContainerColor = colorScheme.surface,
        ),
        onValueChange = onSalaryChanged,
        label = {Text(stringResource(R.string.salary))},
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
    )
}

@Composable
fun AmountText(label:String,slider: Float,changePerc :(Float) -> Unit,value:Double,modifier: Modifier = Modifier){
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 60.dp
        ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainer,
        ),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier
                .align(Alignment.Start)
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            //TODO Add an Icon
            Row( Modifier
                .fillMaxWidth()){
                Text(
                    modifier = Modifier
                        .wrapContentWidth(align = Alignment.Start)
                        .weight(1f),
                    text = label
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth(align = Alignment.End),
                    text = slider.roundToInt().toString() + "%",
                    )
            }

            SliderPercentage(slider,changePerc)
            Text(
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = NumberFormat.getCurrencyInstance().format(value)
            )
        }
        }

}


@Composable
fun SliderPercentage(perc : Float, changePerc :(Float) -> Unit) {
    Column {
        Slider(
            value = perc,
            onValueChange = changePerc,
            steps = 7,
            valueRange = 10f..90f
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SalaryManagementTheme {
        HomeScreen()
    }
}
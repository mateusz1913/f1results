package dev.mateusz1913.f1results.composable.circuit

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.common.Loading
import dev.mateusz1913.f1results.composable.di.getViewModelInstance
import dev.mateusz1913.f1results.viewmodel.CircuitViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CircuitScreen(
    circuitId: String,
    circuitViewModel: CircuitViewModel = getViewModelInstance { parametersOf(circuitId) }
) {
    val circuitState = circuitViewModel.circuitState.collectAsState()
    val circuit = circuitState.value.circuit
    if (circuitState.value.isFetching) {
        Loading()
        return
    }
    if (circuit == null) {
        Text("No circuit")
        return
    }
    CircuitLayoutWithMap(circuit) {
        Box(
            modifier = Modifier.height(128.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(modifier = Modifier.padding(top = 24.dp, start = 20.dp, end = 20.dp)) {
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                                append("Circuit: ")
                            }
                            append(circuit.circuitName)
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                Row {
                    Column(
                        modifier = Modifier.weight(0.5f).padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                                    append("Locality: ")
                                }
                                append(circuit.location.locality)
                            },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(0.5f).padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                                    append("Country: ")
                                }
                                append(circuit.location.country)
                            },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
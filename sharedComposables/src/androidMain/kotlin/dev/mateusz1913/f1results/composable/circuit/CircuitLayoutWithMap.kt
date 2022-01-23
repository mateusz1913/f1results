package dev.mateusz1913.f1results.composable.circuit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.mateusz1913.f1results.composable.common.MapboxMapView
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun CircuitLayoutWithMap(circuit: CircuitType, content: @Composable () -> Unit) {
    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    BottomSheetScaffold(
        sheetContent = {
            Box(
                modifier = Modifier.border(
                    1.dp,
                    MaterialTheme.colors.onBackground,
                    RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.width(40.dp).height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(color = MaterialTheme.colors.onBackground)
                    )
                }
                content()
            }
        },
        sheetPeekHeight = 30.dp,
        sheetElevation = 10.dp,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        scaffoldState = scaffoldState,
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
                .background(color = MaterialTheme.colors.background),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                val latitude = circuit.location.lat?.toDouble()
                val longitude = circuit.location.long?.toDouble()
                if (latitude != null && longitude != null) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        MapboxMapView(
                            latitude = latitude,
                            longitude = longitude
                        )
                    }
                }
            }
        }
    }
}

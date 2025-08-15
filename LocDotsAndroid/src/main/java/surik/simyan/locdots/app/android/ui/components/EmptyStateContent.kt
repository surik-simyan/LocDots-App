package surik.simyan.locdots.app.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import surik.simyan.locdots.app.android.toColor
import surik.simyan.locdots.app.shared.ui.EerieBlackHex
import surik.simyan.locdots.app.shared.ui.PlatinumHex

@Composable
fun EmptyStateContent(
    onCreateDot: () -> Unit,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "No dots nearby, be the first one",
            color = PlatinumHex.toColor()
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = onCreateDot,
            colors = ButtonDefaults.buttonColors(
                containerColor = PlatinumHex.toColor()
            )
        ) {
            Text("Create dot", color = EerieBlackHex.toColor())
        }
    }
}
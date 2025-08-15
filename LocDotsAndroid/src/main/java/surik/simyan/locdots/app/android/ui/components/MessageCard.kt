package surik.simyan.locdots.app.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import surik.simyan.locdots.app.android.toColor
import surik.simyan.locdots.app.shared.data.Dot
import surik.simyan.locdots.app.shared.getDateFromDateTime
import surik.simyan.locdots.app.shared.ui.DavyGrayHex
import surik.simyan.locdots.app.shared.ui.JetHex
import surik.simyan.locdots.app.shared.ui.PlatinumHex

@Composable
fun MessageCard(dot: Dot) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = DavyGrayHex.toColor(),
        ),
        modifier = Modifier
            .heightIn(min = 100.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = dot.message,
                modifier = Modifier.padding(16.dp),
                color = PlatinumHex.toColor(),
                textAlign = TextAlign.Center,
            )
            HorizontalDivider(thickness = 1.dp, color = JetHex.toColor())

            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = getDateFromDateTime(dot.dateTime),
                    color = PlatinumHex.toColor(),
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "16 km",
                    color = PlatinumHex.toColor(),
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}
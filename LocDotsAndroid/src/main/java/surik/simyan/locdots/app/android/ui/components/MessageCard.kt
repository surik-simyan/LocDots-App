package surik.simyan.locdots.app.android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import surik.simyan.locdots.app.shared.domain.model.Dot
import surik.simyan.locdots.app.shared.ui.DavyGray
import surik.simyan.locdots.app.shared.ui.Jet
import surik.simyan.locdots.app.shared.ui.Platinum

@Composable
fun MessageCard(dot: Dot) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = DavyGray,
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
                color = Platinum,
                textAlign = TextAlign.Center,
            )
            HorizontalDivider(thickness = 1.dp, color = Jet)

            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = dot.formattedDate,
                    color = Platinum,
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "16 km",
                    color = Platinum,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}
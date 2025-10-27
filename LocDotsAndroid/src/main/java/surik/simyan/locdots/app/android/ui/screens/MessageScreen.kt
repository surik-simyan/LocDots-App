package surik.simyan.locdots.app.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import surik.simyan.locdots.app.android.ui.components.ThemedDialog
import surik.simyan.locdots.app.shared.ui.EerieBlack
import surik.simyan.locdots.app.shared.ui.Gray
import surik.simyan.locdots.app.shared.ui.Platinum

@Serializable
data object MessageScreenRoute

@Composable
fun MessageScreen(
    onNavigateUp: () -> Unit,
    viewModel: MessageScreenViewModel = koinViewModel(),
) {
    val uploadState by viewModel.uploadState.collectAsStateWithLifecycle()

    MessageScreenContent(
        state = uploadState,
        onNavigateUp = onNavigateUp,
        onSendClick = viewModel::onSendClick,
        resetState = viewModel::resetState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MessageScreenContent(
    state: MessageScreenViewModel.MessageScreenState,
    onNavigateUp: () -> Unit,
    onSendClick: (message: String) -> Unit,
    resetState: () -> Unit,
) {
    val maxChar = 500
    var text by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = EerieBlack,
                    titleContentColor = Platinum,
                ),
                title = {
                    Text(
                        text = "${text.length} / $maxChar",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onNavigateUp() },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Platinum,
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onSendClick(text)
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Platinum,
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Localized description",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(Gray)
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                MessageScreenViewModel.MessageScreenState.Idle -> {
                    TextField(
                        value = text,
                        onValueChange = { if (it.length <= maxChar) text = it },
                        modifier = Modifier
                            .fillMaxSize(),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = EerieBlack,
                            focusedContainerColor = Gray,
                            unfocusedContainerColor = Gray,
                            focusedTextColor = Platinum,
                            unfocusedTextColor = Platinum,
                        ),
                    )
                }

                MessageScreenViewModel.MessageScreenState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = EerieBlack,
                        trackColor = Platinum,
                    )
                }

                else -> Unit
            }
        }
        when (state) {
            is MessageScreenViewModel.MessageScreenState.Error -> {
                ThemedDialog(state.error) {
                    resetState()
                }
            }

            MessageScreenViewModel.MessageScreenState.Success -> {
                ThemedDialog("Dot successfully uploaded") {
                    resetState()
                    onNavigateUp()
                }
            }

            else -> Unit
        }
    }
}

@Preview
@Composable
fun MessageScreenContentPreview() {
    MessageScreenContent(
        state = MessageScreenViewModel.MessageScreenState.Idle,
        onNavigateUp = { },
        onSendClick = { },
        resetState = { },
    )
}

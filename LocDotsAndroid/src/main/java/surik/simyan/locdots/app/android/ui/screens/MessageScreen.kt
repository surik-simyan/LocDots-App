package surik.simyan.locdots.app.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import surik.simyan.locdots.app.android.ui.components.LoadingDialog
import surik.simyan.locdots.app.android.ui.components.ThemedDialog
import surik.simyan.locdots.app.shared.data.DotSort
import surik.simyan.locdots.app.shared.domain.model.Dot
import surik.simyan.locdots.app.shared.ui.EerieBlack
import surik.simyan.locdots.app.shared.ui.Gray
import surik.simyan.locdots.app.shared.ui.Platinum

@Composable
fun MessageScreen(
    onNavigateUp: () -> Unit,
    viewModel: MessageScreenViewModel = koinViewModel()
) {
    val uploadState by viewModel.uploadState.collectAsStateWithLifecycle()

    MessageScreenContent(
        state = uploadState,
        onNavigateUp = onNavigateUp,
        onSendClick = viewModel::onSendClick,
        resetState = viewModel::resetState
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
                            contentColor = Platinum
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onSendClick(text)
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Platinum
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(Gray)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                value = text,
                onValueChange = { if (it.length <= maxChar) text = it },
                modifier = Modifier
                    .heightIn(300.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = EerieBlack,
                    focusedContainerColor = Gray,
                    unfocusedContainerColor = Gray,
                    focusedTextColor = Platinum,
                    unfocusedTextColor = Platinum
                ),
            )
        }
        when (state) {
            MessageScreenViewModel.MessageScreenState.Idle -> Unit
            MessageScreenViewModel.MessageScreenState.Loading -> LoadingDialog()

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
        resetState = { }
    )
}
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import surik.simyan.locdots.app.android.toColor
import surik.simyan.locdots.app.shared.ui.EerieBlackHex
import surik.simyan.locdots.app.shared.ui.GrayHex
import surik.simyan.locdots.app.shared.ui.PlatinumHex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(
    onNavigateUp: () -> Unit
) {
//    val viewModel = koinViewModel<MessageScreenViewModel>()
//    val uploadState by viewModel.uploadState.collectAsState()
    val maxChar = 500
    var text by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = EerieBlackHex.toColor(),
                    titleContentColor = PlatinumHex.toColor(),
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
                            contentColor = PlatinumHex.toColor()
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
//                            viewModel.onSendClick(text)
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = PlatinumHex.toColor()
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
                .background(GrayHex.toColor())
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
                    cursorColor = EerieBlackHex.toColor(),
                    focusedContainerColor = GrayHex.toColor(),
                    unfocusedContainerColor = GrayHex.toColor(),
                    focusedTextColor = PlatinumHex.toColor(),
                    unfocusedTextColor = PlatinumHex.toColor()
                ),
            )
        }
//        when (val result = uploadState) {
//            MessageScreenViewModel.MessageScreenState.Idle -> Unit
//            MessageScreenViewModel.MessageScreenState.Loading -> MinimalDialog(
//                "Uploading dot",
//                true
//            )
//
//            is MessageScreenViewModel.MessageScreenState.Error -> {
//                MinimalDialog("Something went wrong. Please try again", false)
//            }
//
//            MessageScreenViewModel.MessageScreenState.Success -> MinimalDialog(
//                "Dot successfully uploaded",
//                false
//            ) {
//                navController.navigateUp()
//            }
//        }
    }
}
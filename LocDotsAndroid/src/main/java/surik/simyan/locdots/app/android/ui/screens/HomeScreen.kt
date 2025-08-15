package surik.simyan.locdots.app.android.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import surik.simyan.locdots.app.android.toColor
import surik.simyan.locdots.app.android.ui.components.BottomSheetContent
import surik.simyan.locdots.app.android.ui.components.EmptyStateContent
import surik.simyan.locdots.app.android.ui.components.LoadingDialog
import surik.simyan.locdots.app.android.ui.components.MessageCard
import surik.simyan.locdots.app.android.ui.components.ThemedDialog
import surik.simyan.locdots.app.shared.data.DotSort
import surik.simyan.locdots.app.shared.ui.EerieBlackHex
import surik.simyan.locdots.app.shared.ui.GrayHex
import surik.simyan.locdots.app.shared.ui.PlatinumHex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToMessageScreen: () -> Unit,
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    val homeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var sortingType by remember { mutableStateOf(DotSort.PostDate) }
    var isRefreshing by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            showBottomSheet = true
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = PlatinumHex.toColor()
                        )
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Sort,
                            contentDescription = "Localized description",
                        )
                    }
                }, floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            onNavigateToMessageScreen()
                        },
                        containerColor = PlatinumHex.toColor(),
                        contentColor = EerieBlackHex.toColor()
                    ) {
                        Icon(Icons.Filled.Edit, "Floating action button.")
                    }
                }, containerColor = EerieBlackHex.toColor()
            )
        },
        containerColor = GrayHex.toColor()
    ) { innerPadding ->

        when (val state = homeScreenState) {
            HomeScreenViewModel.HomeScreenState.Idle -> Unit
            HomeScreenViewModel.HomeScreenState.Loading -> {
                isRefreshing = true
                LoadingDialog()
            }

            is HomeScreenViewModel.HomeScreenState.Error -> {
                isRefreshing = false
                ThemedDialog(state.error) {
                    viewModel.resetState()
                }
            }

            is HomeScreenViewModel.HomeScreenState.Success -> {
                isRefreshing = false
                if (state.items.isEmpty()) {
                    EmptyStateContent(
                        onCreateDot = {
                            onNavigateToMessageScreen.invoke()
                        },
                        innerPadding
                    )
                } else {
                    PullToRefreshBox(
                        isRefreshing = false,
                        onRefresh = {
                            viewModel.getItems()
                        },
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        LazyColumn(
//                            modifier = Modifier.padding(innerPadding)
                        ) {
                            items(state.items) {
                                MessageCard(it)
                            }
                        }
                    }
                    if (showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                showBottomSheet = false
                            }, sheetState = sheetState, containerColor = EerieBlackHex.toColor()
                        ) {
                            val clickHandler: (DotSort) -> Unit = { sortingType ->
                                viewModel.sortType.update { sortingType }
                                viewModel.getItems()
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                            }
                            BottomSheetContent(clickHandler)
                        }
                    }
                }
            }
        }
    }
}
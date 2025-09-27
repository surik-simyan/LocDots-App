package surik.simyan.locdots.app.android.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.koin.androidx.compose.koinViewModel
import surik.simyan.locdots.app.android.now
import surik.simyan.locdots.app.android.ui.components.BottomSheetContent
import surik.simyan.locdots.app.android.ui.components.EmptyStateContent
import surik.simyan.locdots.app.android.ui.components.LoadingDialog
import surik.simyan.locdots.app.android.ui.components.MessageCard
import surik.simyan.locdots.app.android.ui.components.ThemedDialog
import surik.simyan.locdots.app.shared.data.Coordinates
import surik.simyan.locdots.app.shared.data.DotSort
import surik.simyan.locdots.app.shared.domain.model.Dot
import surik.simyan.locdots.app.shared.ui.EerieBlack
import surik.simyan.locdots.app.shared.ui.Gray
import surik.simyan.locdots.app.shared.ui.Platinum

@Composable
fun HomeScreen(
    onNavigateToMessageScreen: () -> Unit,
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    val homeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()

    HomeScreenContent(
        state = homeScreenState,
        onNavigateToMessageScreen = onNavigateToMessageScreen,
        getItems = viewModel::getItems,
        sortItems = { sortingType ->
            viewModel.sortType.update { sortingType }
            viewModel.getItems()
        },
        resetState = viewModel::resetState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    state: HomeScreenViewModel.HomeScreenState,
    onNavigateToMessageScreen: () -> Unit,
    getItems: () -> Unit,
    sortItems: (sortingType: DotSort) -> Unit,
    resetState: () -> Unit,
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            showBottomSheet = true
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Platinum
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
                        containerColor = Platinum,
                        contentColor = EerieBlack
                    ) {
                        Icon(Icons.Filled.Edit, "Floating action button.")
                    }
                }, containerColor = EerieBlack
            )
        },
        containerColor = Gray,
    ) { innerPadding ->

        when (state) {
            HomeScreenViewModel.HomeScreenState.Idle -> Unit
            HomeScreenViewModel.HomeScreenState.Loading -> {
                LoadingDialog()
            }

            is HomeScreenViewModel.HomeScreenState.Error -> {
                ThemedDialog(state.error) {
                    resetState()
                }
            }

            is HomeScreenViewModel.HomeScreenState.Success -> {
                if (state.items.isEmpty()) {
                    EmptyStateContent(
                        onCreateDot = onNavigateToMessageScreen,
                        padding = innerPadding
                    )
                } else {
                    PullToRefreshBox(
                        isRefreshing = false,
                        onRefresh = {
                            getItems()
                        },
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
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
                            }, sheetState = sheetState, containerColor = EerieBlack
                        ) {
                            val clickHandler: (DotSort) -> Unit = { sortingType ->
                                sortItems(sortingType)
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

@Preview
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(
        state = HomeScreenViewModel.HomeScreenState.Success(
            items = listOf(
                Dot(
                    id = "1",
                    message = "Message 1",
                    formattedDate = "was 15 seconds ago"
                )
            )
        ),
        onNavigateToMessageScreen = { },
        getItems = { },
        sortItems = { },
        resetState = { }
    )
}
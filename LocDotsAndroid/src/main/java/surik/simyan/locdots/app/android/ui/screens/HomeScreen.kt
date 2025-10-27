package surik.simyan.locdots.app.android.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import surik.simyan.locdots.app.android.ui.components.BottomSheetContent
import surik.simyan.locdots.app.android.ui.components.MessageCard
import surik.simyan.locdots.app.android.ui.components.ThemedDialog
import surik.simyan.locdots.app.shared.data.DotSort
import surik.simyan.locdots.app.shared.domain.model.Dot
import surik.simyan.locdots.app.shared.ui.EerieBlack
import surik.simyan.locdots.app.shared.ui.Gray
import surik.simyan.locdots.app.shared.ui.Platinum

@Serializable
data object HomeScreenRoute

@Composable
fun HomeScreen(
    onNavigateToMessageScreen: () -> Unit,
    viewModel: HomeScreenViewModel = koinViewModel(),
) {
    val homeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val sortingType by viewModel.sortingType.collectAsStateWithLifecycle()

    HomeScreenContent(
        state = homeScreenState,
        onNavigateToMessageScreen = onNavigateToMessageScreen,
        getItems = viewModel::getItems,
        sortItems = { newSortingType ->
            viewModel.sortingType.update { newSortingType }
            viewModel.getItems()
        },
        resetState = viewModel::resetState,
        sortingType = sortingType,
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
    sortingType: DotSort,
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
                            contentColor = Platinum,
                        ),
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Sort,
                            contentDescription = "Localized description",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            onNavigateToMessageScreen()
                        },
                        containerColor = Platinum,
                        contentColor = EerieBlack,
                    ) {
                        Icon(Icons.Filled.Edit, "Floating action button.")
                    }
                },
                containerColor = EerieBlack,
            )
        },
        containerColor = Gray,
    ) { innerPadding ->

        when (state) {
            HomeScreenViewModel.HomeScreenState.Idle -> Unit
            HomeScreenViewModel.HomeScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = EerieBlack,
                        trackColor = Platinum,
                    )
                }
            }

            is HomeScreenViewModel.HomeScreenState.Error -> {
                ThemedDialog(state.error) {
                    resetState()
                }
            }

            is HomeScreenViewModel.HomeScreenState.Success -> {
                PullToRefreshBox(
                    isRefreshing = false,
                    onRefresh = {
                        getItems()
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                ) {
                    if (state.items.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                "No dots nearby, be the first one",
                                color = Platinum,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Button(
                                onClick = onNavigateToMessageScreen,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Platinum,
                                ),
                            ) {
                                Text("Create dot", color = EerieBlack)
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                        ) {
                            items(state.items) { dot ->
                                MessageCard(dot)
                            }
                        }
                    }
                }
                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        sheetState = sheetState,
                        containerColor = EerieBlack,
                    ) {
                        BottomSheetContent(
                            selectedSort = sortingType,
                            onApply = {
                                sortItems(it)
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                            },
                        )
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
                    formattedDate = "was 15 seconds ago",
                    formattedDistance = "10 km",
                ),
            ),
        ),
        onNavigateToMessageScreen = { },
        getItems = { },
        sortItems = { },
        resetState = { },
        sortingType = DotSort.PostDate,
    )
}

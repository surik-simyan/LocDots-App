package surik.simyan.locdots.app.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import surik.simyan.locdots.app.android.ui.screens.HomeScreen
import surik.simyan.locdots.app.android.ui.screens.HomeScreenRoute
import surik.simyan.locdots.app.android.ui.screens.MessageScreen
import surik.simyan.locdots.app.android.ui.screens.MessageScreenRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeScreenRoute,
                ) {
                    composable<HomeScreenRoute> {
                        HomeScreen(
                            onNavigateToMessageScreen = {
                                navController.navigate(MessageScreenRoute)
                            },
                        )
                    }
                    composable<MessageScreenRoute> {
                        MessageScreen(
                            onNavigateUp = {
                                navController.navigateUp()
                            },
                        )
                    }
                }
            }
        }
    }
}

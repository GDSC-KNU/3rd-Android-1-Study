package com.gdscknu.peachmarket

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gdscknu.peachmarket.presentation.navigation.MyAppHost
import com.gdscknu.peachmarket.presentation.navigation.Router
import com.gdscknu.peachmarket.theme.PeachMarketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeachMarketTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PeachMarketApp()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PeachMarketApp(){
    val navController = rememberNavController()
    val items = listOf(
        Router.HOME,
        Router.LOCAL,
        Router.MYAROUND,
        Router.CHAT,
        Router.MYPAGE
    )
    val icons = listOf(
        Icons.Rounded.Home,
        Icons.Rounded.AccountBox,
        Icons.Rounded.LocationOn,
        Icons.Rounded.Face,
        Icons.Rounded.Person
    )
    Scaffold(
        bottomBar = {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            if( currentRoute in items.map { it.name } ){
                NavigationBar {
                    items.forEachIndexed { index,screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.name,
                            onClick = {
                                navController.navigate(screen.name){
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                   Icon(imageVector = icons[index], contentDescription = null)
                            },
                            label = {
                                Text(text = " ${screen.korean}")
                            }
                        )
                    }
                }
            }


        }
    ) {paddingValues ->
        MyAppHost(navController = navController, modifier = Modifier.padding(paddingValues))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PeachMarketTheme {
        Greeting("Android")
    }
}
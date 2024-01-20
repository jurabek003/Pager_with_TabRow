@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package uz.turgunboyevjurabek.pagerwithtabrow

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import uz.turgunboyevjurabek.pagerwithtabrow.ui.theme.PagerWithTabRowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PagerWithTabRowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val tabItems= listOf(
        TabItem("Home",Icons.Outlined.Home,Icons.Filled.Home),
        TabItem("Browse",Icons.Outlined.ShoppingCart,Icons.Filled.ShoppingCart),
        TabItem("Account",Icons.Outlined.AccountCircle,Icons.Filled.AccountCircle),
        )
    var selectedTabIndex  by remember {
        mutableIntStateOf(0)
    }
    var pagerState= rememberPagerState {
        tabItems.size
    }
    LaunchedEffect(selectedTabIndex){
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage,pagerState.isScrollInProgress){
        if (!pagerState.isScrollInProgress){
            selectedTabIndex=pagerState.currentPage
        }

    }


    
    Column(modifier = Modifier.fillMaxSize()) {

        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed{index, item ->
                Tab(selected = index==selectedTabIndex,
                    onClick = { selectedTabIndex=index },
                    text = { Text(text = item.title)},
                    icon = {
                        Icon(imageVector =if (index==selectedTabIndex){ item.selectedIcon }
                        else{ item.unselectedIcon }, contentDescription = item.title)
                    },

                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {index->
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(text = tabItems[index].title)
            }
        }

        // column
    }



}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    PagerWithTabRowTheme {
        Greeting()
    }
}
data class TabItem(
    val title:String,
    val unselectedIcon:ImageVector,
    val selectedIcon:ImageVector
)
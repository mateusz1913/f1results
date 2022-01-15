package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
actual fun TwoColumnLayout(firstItem: TwoColumnLayoutItem, secondItem: TwoColumnLayoutItem) {
    val pagerState = rememberPagerState()
    val composableScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TabBar(
            items = arrayOf(
                TabBarItem(firstItem.title) {
                    composableScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                TabBarItem(secondItem.title) {
                    composableScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            )
        )
        HorizontalPager(
            count = 2,
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) { page ->
            when (page) {
                0 -> firstItem.item()
                1 -> secondItem.item()
            }
        }
    }
}

package com.tzh.myshop.ui.shareComponent

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tzh.myshop.common.navigation.Route


@Composable
fun MyTopAppBar(navController: NavController, myRoute: Route, scaffoldState: ScaffoldState) {
    val appBarHorizontalPadding = 4.dp
    val titleIconModifier = Modifier
        .fillMaxHeight()
        .width(72.dp - appBarHorizontalPadding)

    TopAppBar(
        backgroundColor = Color.Transparent,
        contentColor = contentColorFor(backgroundColor = Color.Black),
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {

        //TopAppBar Content
        Box(Modifier.height(32.dp)) {

            //Navigation Icon
            Row(titleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                ) {
                    IconButton(onClick = {
                        Log.e("CLICK 1", "TRUE")
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = null
                        )
                    }
                }
            }

            //Title
            Row(
                Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
            ) {

                ProvideTextStyle(value = MaterialTheme.typography.h6) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, maxLines = 1, text = myRoute.title
                        )
                    }
                }
            }

            //actions
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {},
                )
            }
        }
    }

}

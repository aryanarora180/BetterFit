package com.example.betterfit.ui.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.betterfit.R
import com.example.betterfit.ui.theme.BetterFitTheme


@Composable
fun SignInScreen() {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally) {
            BetterFitHeader()

            Button(
                onClick = {/*TODO*/},
                modifier = Modifier.padding(top = 280.dp)
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(4.dp)
                )
                Text(
                    text= "Sign In",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun BetterFitHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 128.dp)
        )
        Text(
            text = "BetterFit",
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewLoginScreen() {
    BetterFitTheme {
        SignInScreen()
    }
}
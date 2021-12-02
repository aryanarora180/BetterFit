package com.example.betterfit.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.betterfit.R

@Composable
fun CenteredView(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
fun ServerConnectionError(
    modifier: Modifier = Modifier,
    errorText: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            painter = painterResource(id = R.drawable.ic_internet_error),
            contentScale = ContentScale.FillWidth,
            contentDescription = "Internet error doodle"
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
            text = errorText,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        TextButton(onClick = onRetryClick) {
            Text(text = "Retry")
        }
    }
}
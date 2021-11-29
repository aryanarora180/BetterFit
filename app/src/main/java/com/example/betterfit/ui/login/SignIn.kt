package com.example.betterfit.ui.login

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betterfit.R
import com.example.betterfit.ui.theme.BetterFitTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(viewModel: SignInViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    val snackbarCoroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(Scopes.FITNESS_ACTIVITY_READ))
            .requestServerAuthCode("150943160797-355b6ahtcb1t4mcp1saq94mo6qpaod6g.apps.googleusercontent.com")
            .build()
    )
    val signInIntent = googleSignInClient.signInIntent

    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            if (task.isSuccessful) {
                viewModel.isSigningIn = false
                snackbarCoroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Sign in success. Server auth code: ${task.result.serverAuthCode}")
                }
            } else {
                viewModel.isSigningIn = false   
                snackbarCoroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Sign in failed")
                }
            }
        }
    )

    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            BetterFitHeader()
            GoogleSignInButton(
                isSigningIn = viewModel.isSigningIn,
                onClick = {
                    viewModel.isSigningIn = true
                    signInLauncher.launch(signInIntent)
                }
            )
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

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    isSigningIn: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = !isSigningIn,
        modifier = modifier
            .animateContentSize()
            .padding(top = 16.dp)
    ) {
        if (isSigningIn) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.size(16.dp)
            )
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = "Google",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(16.dp)
                )
                Text("Sign in with Google")
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewLoginScreen() {
    BetterFitTheme {
        SignInScreen()
    }
}
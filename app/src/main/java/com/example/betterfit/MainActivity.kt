package com.example.betterfit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.betterfit.helper.DataStoreUtils
import com.example.betterfit.ui.competitions.details.CompetitionDetailsScreen
import com.example.betterfit.ui.home.HomeScreen
import com.example.betterfit.ui.login.SignInScreen
import com.example.betterfit.ui.theme.BetterFitTheme
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStoreUtils: DataStoreUtils

    private lateinit var navController: NavHostController

    private lateinit var paymentSheet: PaymentSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)

        lifecycleScope.launchWhenCreated {
            if (dataStoreUtils.getAuthToken().isNullOrEmpty() || dataStoreUtils.getUserId()
                    .isNullOrEmpty()
            ) {
                Log.e(javaClass.simpleName, "User is signed in")
            } else {
                Log.e(javaClass.simpleName, "User is not signed in")
            }
        }

        setContent {
            BetterFitTheme {
                navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            onCompetitionTap = {
                                navController.navigate("competition/$it")
                            }
                        )
                    }
                    composable("login") {
                        SignInScreen(
                            onLogin = {
                                navController.navigate("home")
                                navController.clearBackStack("login")
                            }
                        )
                    }
                    composable("competition/{competitionId}") { backStackEntry ->
                        CompetitionDetailsScreen(
                            competitionId =
                            backStackEntry.arguments?.getString("competitionId") ?: "",
                            onJoinCompetition = {
                                val configuration = PaymentSheet.Configuration("Example, Inc.")
                                paymentSheet.presentWithPaymentIntent(it, configuration)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun onPaymentSheetResult(paymentResult: PaymentSheetResult) {
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                Log.e("StripePayment", "Payment complete")
            }
            is PaymentSheetResult.Canceled -> {
                Log.e("StripePayment", "Payment canceled")
            }
            is PaymentSheetResult.Failed -> {
                Log.e("StripePayment", "Payment failed")
            }
        }
    }
}

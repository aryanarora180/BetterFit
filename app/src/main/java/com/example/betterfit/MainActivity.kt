package com.example.betterfit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.betterfit.helper.DataStoreUtils
import com.example.betterfit.ui.competitions.details.CompetitionDetailsScreen
import com.example.betterfit.ui.competitions.leaderboard.CompetitionProgressScreen
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
    private lateinit var competitionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)

        setContent {
            BetterFitTheme {
                navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    composable("home") {
                        HomeScreen(
                            onTrendingTap = {
                                navController.navigate("competition/$it")
                            },
                            onRegisteredTap = {
                                navController.navigate("progress/$it")
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
                        competitionId = backStackEntry.arguments?.getString("competitionId") ?: ""
                        CompetitionDetailsScreen(
                            competitionId = competitionId,
                            onJoinCompetition = {
                                val configuration = PaymentSheet.Configuration("BetterFit")
                                paymentSheet.presentWithPaymentIntent(it, configuration)
                            }
                        )
                    }
                    composable("progress/{competitionId}") { backStackEntry ->
                        competitionId = backStackEntry.arguments?.getString("competitionId") ?: ""
                        CompetitionProgressScreen(competitionId = competitionId)
                    }
                }
            }
        }
    }

    private fun onPaymentSheetResult(paymentResult: PaymentSheetResult) {
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                navController.navigate("progress/$competitionId")
            }
            is PaymentSheetResult.Canceled -> {
                Log.e("StripePayment", "Payment canceled.")
            }
            is PaymentSheetResult.Failed -> {
                Log.e("StripePayment", "Payment failed.")
            }
        }
    }
}

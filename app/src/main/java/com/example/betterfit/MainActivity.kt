package com.example.betterfit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.betterfit.ui.competitions.details.CompetitionDetailsScreen
import com.example.betterfit.ui.theme.BetterFitTheme
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BetterFitTheme {
                CompetitionDetailsScreen(
                    onJoinCompetition = { clientSecret ->

                        val paymentSheet = PaymentSheet(this) {
                            when (it) {
                                is PaymentSheetResult.Completed -> {
                                    Log.e("StripePayment", "Payment complete")
                                }
                                is PaymentSheetResult.Canceled -> {
                                    Log.e("StripePayment", "Payment canceled")
                                }
                                is PaymentSheetResult.Failed -> {
                                    Log.e(
                                        "StripePayment",
                                        "Payment failed",
                                        it.error.fillInStackTrace()
                                    )
                                }
                            }
                        }
                        val configuration = PaymentSheet.Configuration("BetterFit")
                        paymentSheet.presentWithPaymentIntent(clientSecret, configuration)
                    }
                )
            }
        }
    }
}

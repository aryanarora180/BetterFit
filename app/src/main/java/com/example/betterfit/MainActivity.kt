package com.example.betterfit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.betterfit.helper.DataStoreUtils
import com.example.betterfit.ui.competitions.details.CompetitionDetailsScreen
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                CompetitionDetailsScreen(
                    onJoinCompetition = { clientSecret ->
                        Log.e(javaClass.simpleName, "Stripeeee")
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

package com.example.betterfit

import android.app.Application
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BetterFitApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51K13HHSDwDKIuYbgknRdfGdBGCM6Oc6T290V1oM1LL72TUqxP1xRmD0P3cpZy0AsopK0GO3zBmMizxZ11NNOis1w00gkMXQf7p"
        )
    }
}

package com.sdv.postaldeliveryapp

import android.app.Application
import android.content.Context
import com.sdv.postaldeliveryapp.di.repository
import com.sdv.postaldeliveryapp.di.useCase
import com.sdv.postaldeliveryapp.di.viewModel
import com.sdv.postaldeliveryapp.di.dataSource
import com.sdv.postaldeliveryapp.di.network
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by FrostEagle on 20.08.2020.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */
class PostalApp: Application() {
    private fun initKoin() {
        startKoin {
            androidContext(this@PostalApp)
            modules(
                listOf(
                    network,
                    viewModel,
                    useCase,
                    repository,
                    dataSource
                )
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        initKoin()
    }

    companion object {
        lateinit var appContext: Context
    }
}
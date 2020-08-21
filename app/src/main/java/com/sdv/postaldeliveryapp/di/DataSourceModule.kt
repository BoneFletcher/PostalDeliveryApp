package com.sdv.postaldeliveryapp.di

import com.sdv.datasource.remote.API
import org.koin.core.module.Module
import org.koin.dsl.module

val dataSource: Module = module {
    single { API(get()) }
}
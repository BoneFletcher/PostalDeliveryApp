package com.sdv.postaldeliveryapp.di


import com.sdv.domain.usecase.GetCommentsUseCase
import com.sdv.domain.usecase.GetPostUseCase
import com.sdv.domain.usecase.GetUserListUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val useCase: Module = module {
   single { GetPostUseCase(get()) }
   single { GetUserListUseCase(get()) }
   single { GetCommentsUseCase(get()) }


   // factory { SaveMessageUseCase(get()) }

}
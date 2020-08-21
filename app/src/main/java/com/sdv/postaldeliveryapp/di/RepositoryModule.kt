package com.sdv.postaldeliveryapp.di



import com.sdv.domain.repository.PostRepository
import com.sdv.repository.PostRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module


val repository: Module = module {
    single<PostRepository> { PostRepositoryImpl(get()) }
}
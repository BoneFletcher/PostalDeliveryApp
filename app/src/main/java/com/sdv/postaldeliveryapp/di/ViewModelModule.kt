package com.sdv.postaldeliveryapp.di

import com.sdv.presentation.ui.pager.PagerViewModel
import com.sdv.presentation.ui.pager.posts.PostsViewModel
import com.sdv.presentation.ui.pager.posts.detail.PostDetailViewModel
import com.sdv.presentation.ui.pager.user.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModel: Module = module {

    viewModel {
        UsersViewModel(get(), get(), get())
    }
    viewModel {
        PostsViewModel(get())
    }
    viewModel {
        PagerViewModel()
    }
    viewModel {
        PostDetailViewModel(get())
    }

}
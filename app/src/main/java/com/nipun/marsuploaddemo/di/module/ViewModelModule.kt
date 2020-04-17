package com.nipun.marsuploaddemo.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nipun.marsuploaddemo.di.annotation.ViewModelKey
import com.nipun.marsuploaddemo.viewmodel.GalleryViewModel
import com.nipun.marsuploaddemo.viewmodel.UploadListViewModel
import com.nipun.marsuploaddemo.viewmodel.UploadViewModel
import com.nipun.marsuploaddemo.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    abstract fun bindGalleryViewModel(viewModel: GalleryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UploadViewModel::class)
    abstract fun bindUploadViewModel(viewModel: UploadViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UploadListViewModel::class)
    abstract fun bindUploadListViewModel(viewModel: UploadListViewModel): ViewModel
}
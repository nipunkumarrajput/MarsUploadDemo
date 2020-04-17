package com.nipun.marsuploaddemo.di.module

import com.nipun.marsuploaddemo.fragments.GalleryFragment
import com.nipun.marsuploaddemo.fragments.ImageListFragment
import com.nipun.marsuploaddemo.fragments.UploadFragment
import com.nipun.marsuploaddemo.fragments.UploadListFragment
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

/*
* Created by Nipun Kumar Rajput on 16-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
@Module(includes = [AndroidInjectionModule::class])
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindImageListFragment(): ImageListFragment

    @ContributesAndroidInjector
    abstract fun binUploadFragment(): UploadFragment

    @ContributesAndroidInjector
    abstract fun binGalleryFragment(): GalleryFragment

    @ContributesAndroidInjector
    abstract fun binUploadListFragment(): UploadListFragment
}
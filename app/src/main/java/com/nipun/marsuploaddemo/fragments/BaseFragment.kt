package com.nipun.marsuploaddemo.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/*
* Created by Nipun Kumar Rajput on 12-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
abstract class BaseFragment<VM : ViewModel>(
    private val viewModelClass: Class<VM>
) :
    DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If the fragment is retained, the lifecycle does not get restarted it on config change
        retainInstance = true
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
    }


}
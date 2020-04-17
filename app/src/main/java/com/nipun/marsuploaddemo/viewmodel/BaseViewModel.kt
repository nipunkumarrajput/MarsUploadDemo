package com.nipun.marsuploaddemo.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/*
* Created by Nipun Kumar Rajput on 15-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
abstract class BaseViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
    }

}
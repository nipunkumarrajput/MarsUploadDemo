package com.nipun.marsuploaddemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nipun.marsuploaddemo.data.db.UploadDao
import com.nipun.marsuploaddemo.data.db.UploadEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* Created by Nipun Kumar Rajput on 16-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
class UploadListViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var uploadDao: UploadDao

    private val _images = MutableLiveData<List<UploadEntity>>()
    val images: LiveData<List<UploadEntity>> get() = _images

    fun loadImages() {
        viewModelScope.launch {
            compositeDisposable.add(
                uploadDao.getAllUploadedImages().subscribeOn(
                    Schedulers.io()
                ).observeOn(
                    AndroidSchedulers.mainThread()
                ).subscribe({
                    _images.postValue(it)
                }, {
                    _images.postValue(null)
                    it.printStackTrace()
                })
            )

        }
    }

}
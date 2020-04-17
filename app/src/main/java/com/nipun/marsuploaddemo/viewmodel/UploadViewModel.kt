package com.nipun.marsuploaddemo.viewmodel

import com.nipun.marsuploaddemo.data.ApiService
import com.nipun.marsuploaddemo.data.db.UploadDao
import com.nipun.marsuploaddemo.model.UploadResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

/*
* Created by Nipun Kumar Rajput on 15-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
class UploadViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var uploadDao: UploadDao

    private fun createUploadRequestBody(file: File, mimeType: String) =
        file.asRequestBody(mimeType.toMediaType())

    private fun String.toPlainTextBody() = toRequestBody("text/plain".toMediaType())

    private fun createUploadRequest(
        key: String,
        file: File,
        filename: String,
        mimeType: String
    ): Single<UploadResponse> {
        val requestBody = createUploadRequestBody(file, mimeType)
        return apiService.doImageUploadCall(
            key = key.toPlainTextBody(),
            file = MultipartBody.Part.createFormData(
                name = "image",
                filename = filename,
                body = requestBody
            ),
            name = filename.toPlainTextBody()
        )
    }

    fun uploadImage(
        key: String,
        file: File,
        filename: String,
        mimeType: String,
        uploadResponse: (UploadResponse?) -> Unit
    ) {
        compositeDisposable.add(
            createUploadRequest(key, file, filename, mimeType).subscribeOn(
                Schedulers.io()
            ).observeOn(
                AndroidSchedulers.mainThread()
            ).doOnSuccess {
                uploadDao.insert(it.toUploadEntity())
            }.subscribe({
                uploadResponse(it)
            }, {
                uploadResponse(null)
                it.printStackTrace()
            })
        )

    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
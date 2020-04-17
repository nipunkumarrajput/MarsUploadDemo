package com.nipun.marsuploaddemo.data

import com.nipun.marsuploaddemo.model.UploadResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/*
* Created by Nipun Kumar Rajput on 15-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
interface ApiService {
    @Multipart
    @POST("1/upload")
    fun doImageUploadCall(
        @Part("key") key : RequestBody,
        @Part file: MultipartBody.Part,
        @Part("name") name : RequestBody
    ): Single<UploadResponse>
}
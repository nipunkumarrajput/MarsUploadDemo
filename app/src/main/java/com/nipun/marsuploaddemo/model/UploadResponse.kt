package com.nipun.marsuploaddemo.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.nipun.marsuploaddemo.data.db.UploadEntity


/*
* Created by Nipun Kumar Rajput on 15-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
class UploadResponse {
    @SerializedName("data")
    @Expose
    var data: Data? = null

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("url_viewer")
        @Expose
        var urlViewer: String? = null

        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("display_url")
        @Expose
        var displayUrl: String? = null

        @SerializedName("title")
        @Expose
        var title: String? = null

        @SerializedName("time")
        @Expose
        var time: String? = null

        @SerializedName("image")
        @Expose
        var image: Image? = null

        @SerializedName("thumb")
        @Expose
        var thumb: Thumb? = null

        @SerializedName("medium")
        @Expose
        var medium: Medium? = null

        @SerializedName("delete_url")
        @Expose
        var deleteUrl: String? = null
    }

    class Image {
        @SerializedName("filename")
        @Expose
        var filename: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("mime")
        @Expose
        var mime: String? = null

        @SerializedName("extension")
        @Expose
        var extension: String? = null

        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("size")
        @Expose
        var size: Int? = null
    }

    class Thumb {
        @SerializedName("filename")
        @Expose
        var filename: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("mime")
        @Expose
        var mime: String? = null

        @SerializedName("extension")
        @Expose
        var extension: String? = null

        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("size")
        @Expose
        var size: String? = null
    }

    class Medium {
        @SerializedName("filename")
        @Expose
        var filename: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("mime")
        @Expose
        var mime: String? = null

        @SerializedName("extension")
        @Expose
        var extension: String? = null

        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("size")
        @Expose
        var size: String? = null
    }

    fun toUploadEntity(): UploadEntity {
        return UploadEntity(0, data?.image?.url!!, data?.thumb?.url!!)
    }
}
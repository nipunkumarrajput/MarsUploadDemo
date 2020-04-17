package com.nipun.marsuploaddemo.data.db

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Created by Nipun Kumar Rajput on 16-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
@Entity(tableName = "uploaded_image")
data class UploadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val imageUrl: String,
    val imageThumbUrl: String
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<UploadEntity>() {
            override fun areItemsTheSame(oldItem: UploadEntity, newItem: UploadEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UploadEntity, newItem: UploadEntity) =
                oldItem == newItem
        }
    }
}
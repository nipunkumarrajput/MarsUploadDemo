package com.nipun.marsuploaddemo.data.db

import androidx.room.*
import io.reactivex.Single


/*
* Created by Nipun Kumar Rajput on 16-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
@Dao
interface UploadDao {
    @Query("SELECT * FROM uploaded_image")
    fun getAllUploadedImages(): Single<List<UploadEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(uploadEntity: UploadEntity)

    @Delete
    fun remove(uploadEntity: UploadEntity)

    @Query("SELECT count(*) FROM uploaded_image where id LIKE :id")
    fun isUploaded(id: Long): Int
}
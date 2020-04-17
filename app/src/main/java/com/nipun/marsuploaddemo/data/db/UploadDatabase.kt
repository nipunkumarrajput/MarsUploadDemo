package com.nipun.marsuploaddemo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

/*
* Created by Nipun Kumar Rajput on 16-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
@Database(entities = [UploadEntity::class], version = 1, exportSchema = false)
abstract class UploadDatabase : RoomDatabase() {

    abstract fun uploadDao(): UploadDao

    companion object {
        const val DATABASE_NAME = "upload.db"
    }
}
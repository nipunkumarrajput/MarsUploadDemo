package com.nipun.marsuploaddemo.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.nipun.marsuploaddemo.data.db.UploadDao
import com.nipun.marsuploaddemo.data.db.UploadDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }


    @JvmStatic
    @Singleton
    @Provides
    fun provideUploadDatabase(context: Context): UploadDatabase {
        return Room.databaseBuilder(
            context,
            UploadDatabase::class.java,
            UploadDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideUploadDao(uploadDatabase: UploadDatabase): UploadDao {
        return uploadDatabase.uploadDao()
    }
}
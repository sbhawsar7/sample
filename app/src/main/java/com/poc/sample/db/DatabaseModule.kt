package com.poc.sample.db

import android.content.Context
import androidx.room.Room
import com.poc.sample.MyApplication
import com.poc.sample.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "sample.db").build()


    @Provides
    fun provideUserDao(appDatabase: AppDatabase):UserDao = appDatabase.getUserDao()

}
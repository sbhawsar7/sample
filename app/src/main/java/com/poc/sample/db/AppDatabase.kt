package com.poc.sample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev_challenge.myshopping.db.entities.CategoryItem
import com.poc.sample.db.dao.UserDao
import com.poc.sample.network.model.entity.User

@Database(
    entities = [CategoryItem::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

}
package com.nextory.testapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
	entities = [
		Book::class
	],
	version = 2
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun bookDao(): BookDao
}
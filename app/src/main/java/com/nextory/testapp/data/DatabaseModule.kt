package com.nextory.testapp.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "test.db"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
	@Provides
	@Singleton
	fun provideDatabase(
		@ApplicationContext context: Context
	): AppDatabase = Room.databaseBuilder(
		context,
		AppDatabase::class.java,
		DATABASE_NAME
	)
		.createFromAsset("test.db")
		.addMigrations(Migrations1To2.MIGRATION_1_2)
		.build()

	@Provides
	fun provideBookDao(database: AppDatabase) = database.bookDao()
}

object Migrations1To2 {
	/**
	 * Migrate from:
	 * version 1 - using Room
	 * to
	 * version 2 - using Room where the [Book] has an extra field: isFavorite
	 */
	@VisibleForTesting
	val MIGRATION_1_2: Migration = object : Migration(1, 2) {
		override fun migrate(database: SupportSQLiteDatabase) {
			database.execSQL(
				"ALTER TABLE book"
						+ " ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0"
			)
		}
	}
}
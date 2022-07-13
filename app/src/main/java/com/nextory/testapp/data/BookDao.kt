package com.nextory.testapp.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query

@Dao
interface BookDao {
	@Query("SELECT * FROM book")
	fun observePagedBooks(): PagingSource<Int, Book>

	@Query("SELECT * FROM book WHERE id = :id")
	suspend fun getBookById(id: Long): Book?

	@Query("UPDATE book SET isFavorite = :isFavorite WHERE id=:id")
	suspend fun updateFavoriteById(isFavorite: Int, id: Long)

	@Query("SELECT * FROM book WHERE author OR title LIKE '%' || :query || '%'")
	fun search(query: String): PagingSource<Int, Book>
}
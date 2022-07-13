package com.nextory.testapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepository @Inject constructor(
	private val bookDao: BookDao
) {
	fun observePagedBooks(query: String, pagingConfig: PagingConfig): Flow<PagingData<Book>> =
		Pager(config = pagingConfig) {
			if (query.isEmpty()) {
				bookDao.observePagedBooks()
			} else {
				bookDao.search(query)
			}
		}.flow

	suspend fun getBookById(id: Long): Book? = bookDao.getBookById(id)

	suspend fun updateFavoriteById(isFavorite: Boolean, id: Long) =
		bookDao.updateFavoriteById(if (isFavorite) 1 else 0, id)

}
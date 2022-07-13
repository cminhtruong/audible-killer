package com.nextory.testapp.ui.book_detail

import com.nextory.testapp.data.Book

sealed class BookDetailEvent {
	data class OnFavoriteChanged(val book: Book, val isFavorite: Boolean) : BookDetailEvent()
}

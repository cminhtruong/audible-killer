package com.nextory.testapp.ui.book_detail

import com.nextory.testapp.data.Book

data class BookDetailState(
	val isLoading: Boolean = false,
	val book: Book? = null,
	val error: String = ""
)

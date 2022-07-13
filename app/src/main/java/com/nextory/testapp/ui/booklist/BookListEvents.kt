package com.nextory.testapp.ui.booklist

sealed class BookListEvents {
	data class OnSearch(val query: String) : BookListEvents()
}

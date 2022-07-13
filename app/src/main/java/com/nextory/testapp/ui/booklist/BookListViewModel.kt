package com.nextory.testapp.ui.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import com.nextory.testapp.data.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
	private val bookRepository: BookRepository
) : ViewModel() {
	val mFlow = MutableStateFlow("")
	val pagedBooks = mFlow.flatMapLatest {
		bookRepository.observePagedBooks(it, PAGING_CONFIG)
	}


	companion object {
		val PAGING_CONFIG = PagingConfig(
			pageSize = 12,
			enablePlaceholders = false
		)
	}

	fun onEventChanged(events: BookListEvents) {
		if (events is BookListEvents.OnSearch) {
			viewModelScope.launch {
				mFlow.value = events.query
			}
		}
	}
}
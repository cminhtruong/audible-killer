package com.nextory.testapp.ui.book_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextory.testapp.data.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
	private val repository: BookRepository,
	savedStateHandle: SavedStateHandle
) : ViewModel() {
	private var currentId: Long? = null
	private val _bookItem = mutableStateOf(BookDetailState())
	val bookItem: State<BookDetailState> = _bookItem

	init {
		getBookDetail(savedStateHandle)
	}

	private fun getBookDetail(savedStateHandle: SavedStateHandle) {
		savedStateHandle.get<Long>("id")?.let { id ->
			_bookItem.value = BookDetailState(isLoading = true)
			if (id != -1L) viewModelScope.launch(Dispatchers.IO) {
				try {
					repository.getBookById(id)?.apply {
						currentId = this.id
						_bookItem.value = BookDetailState(book = this)
					}
				} catch (e: Exception) {
					withContext(Dispatchers.Main) {
						_bookItem.value = BookDetailState(error = "Error reading state")
					}
				}
			} else _bookItem.value = BookDetailState(error = "The id does not exist")
		}
	}

	fun onEventChanged(event: BookDetailEvent) {
		if (event is BookDetailEvent.OnFavoriteChanged) {
			viewModelScope.launch(Dispatchers.IO) {
				repository.updateFavoriteById(event.isFavorite, event.book.id)
			}
		}
	}


}
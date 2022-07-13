package com.nextory.testapp.ui.booklist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.nextory.testapp.R
import com.nextory.testapp.data.Book
import com.nextory.testapp.ui.Screen
import com.nextory.testapp.ui.components.ListItem
import com.nextory.testapp.ui.utils.rememberFlowWithLifecycle

@Composable
fun BookList(
	navController: NavController,
	bookListViewModel: BookListViewModel = hiltViewModel()
) {
	val pagedBooks = rememberFlowWithLifecycle(bookListViewModel.pagedBooks)
		.collectAsLazyPagingItems()
	BookList(
		navController,
		pagedBooks = pagedBooks,
		onSearchTextChanged = {
			bookListViewModel.onEventChanged(BookListEvents.OnSearch(it))
		}
	)
}

@OptIn(
	ExperimentalMaterial3Api::class,
	ExperimentalFoundationApi::class,
	ExperimentalComposeUiApi::class
)
@Composable
private fun BookList(
	navController: NavController,
	pagedBooks: LazyPagingItems<Book>,
	onSearchTextChanged: (String) -> Unit = {}
) {
	Scaffold(topBar = { BookListTopBar() }) { paddingValues ->
		LazyColumn(
			modifier = Modifier.padding(paddingValues),
			contentPadding = WindowInsets.safeDrawing
				.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
				.asPaddingValues()
		) {
			stickyHeader {
				var searchText by remember { mutableStateOf("") }
				val keyboardController = LocalSoftwareKeyboardController.current!!
				val focusRequester = remember { FocusRequester() }
				OutlinedTextField(
					modifier = Modifier
						.fillMaxWidth()
						.focusRequester(focusRequester),
					value = searchText,
					onValueChange = {
						searchText = it
						onSearchTextChanged(it)
					},
					placeholder = {
						Text(text = stringResource(R.string.search_placeholder))
					},
					trailingIcon = {
						AnimatedVisibility(visible = searchText.isNotEmpty()) {
							IconButton(onClick = { searchText = "" }) {
								Icon(
									imageVector = Icons.Filled.Close,
									contentDescription = null
								)
							}
						}
					},
					maxLines = 1,
					singleLine = true,
					keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
					keyboardActions = KeyboardActions(onDone = {
						keyboardController.hide()
					}),
					colors = TextFieldDefaults.textFieldColors()
				)
			}

			items(pagedBooks) { book ->
				BookItem(navController, book = book!!)
			}
		}
	}
}

@Composable
private fun BookListTopBar() {
	CenterAlignedTopAppBar(
		title = { Text(stringResource(id = R.string.booklist_title)) },
		modifier = Modifier.windowInsetsPadding(
			WindowInsets.safeDrawing.only(
				WindowInsetsSides.Horizontal + WindowInsetsSides.Top
			)
		)
	)
}

@Composable
private fun BookItem(
	navController: NavController,
	book: Book
) {
	ListItem(
		modifier = Modifier.clickable {
			navController.navigate(
				Screen.BookDetails.route + "?id=${book.id}"
			)
		},
		icon = {
			AsyncImage(
				model = book.imageUrl,
				contentDescription = null,
				modifier = Modifier
					.size(64.dp)
					.clip(RoundedCornerShape(8.dp))
			)
		},
		secondaryText = { Text(book.author) }
	) {
		Text(book.title)
	}
}
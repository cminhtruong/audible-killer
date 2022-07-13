package com.nextory.testapp.ui.book_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.nextory.testapp.data.Book

@Composable
fun BookDetail(
	navController: NavController,
	viewModel: BookDetailViewModel = hiltViewModel()
) {
	val state = viewModel.bookItem.value
	BookDetailScreen(navController = navController, bookDetailState = state, onFavClicked = {
		viewModel.onEventChanged(
			BookDetailEvent.OnFavoriteChanged(
				book = state.book!!,
				isFavorite = state.book.isFavorite
			)
		)
	})
}

@OptIn(
	ExperimentalMaterial3Api::class
)
@Composable
fun BookDetailScreen(
	navController: NavController,
	bookDetailState: BookDetailState,
	onFavClicked: () -> Unit
) {
	Scaffold(
		topBar = {
			when {
				bookDetailState.book != null -> {
					BookDetailTopBar(navController = navController, book = bookDetailState.book) {
						onFavClicked()
					}
				}
			}

		}
	) {
		when {
			bookDetailState.book != null -> {
				val book = bookDetailState.book
				Column(
					modifier = Modifier
						.fillMaxHeight()
						.padding(20.dp),
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Spacer(modifier = Modifier.height(20.dp))
					AsyncImage(
						model = book.imageUrl,
						contentDescription = null,
						modifier = Modifier
							.size(140.dp)
							.clip(RoundedCornerShape(8.dp))
					)
					Spacer(modifier = Modifier.height(20.dp))
					Text(modifier = Modifier.fillMaxWidth(), text = book.author)
					Spacer(modifier = Modifier.height(20.dp))
					Text(book.description)
				}
			}
			bookDetailState.isLoading -> {
				// Display a progress bar
			}
			bookDetailState.error.isNotEmpty() -> {
				// Display error
			}
		}

	}
}

@Composable
private fun BookDetailTopBar(
	navController: NavController,
	book: Book,
	onFavoriteClick: () -> Unit
) {
	var isFavorite by remember { mutableStateOf(book.isFavorite) }
	isFavorite = book.isFavorite

	CenterAlignedTopAppBar(
		title = { Text(book.title) },
		actions = {
			IconButton(onClick = {
				isFavorite = !isFavorite
				onFavoriteClick()
			}) {
				Icon(
					imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
					contentDescription = "Add book favorite",
					tint = MaterialTheme.colorScheme.onSurface
				)

			}
		},
		navigationIcon = {
			IconButton(onClick = { navController.navigateUp() }) {
				Icon(Icons.Filled.ArrowBack, "back")
			}
		},
		modifier = Modifier.windowInsetsPadding(
			WindowInsets.safeDrawing.only(
				WindowInsetsSides.Horizontal + WindowInsetsSides.Top
			)
		)
	)
}

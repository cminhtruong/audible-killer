package com.nextory.testapp.ui.book_detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun BookDetail(
	navController: NavController,
	viewModel: BookDetailViewModel = hiltViewModel()
) {
	val state = viewModel.bookItem.value
	BookDetailScreen(navController = navController, bookDetailState = state)
}

@OptIn(
	ExperimentalMaterial3Api::class,
	ExperimentalFoundationApi::class,
	ExperimentalComposeUiApi::class
)
@Composable
fun BookDetailScreen(
	navController: NavController,
	bookDetailState: BookDetailState
) {
	Scaffold(

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


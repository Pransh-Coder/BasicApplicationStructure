package com.example.basicapplicationstructure.presentation

data class SearchState(
    val isLoading: Boolean = false,
    val searchResults: List<SearchResults> = emptyList(),
)

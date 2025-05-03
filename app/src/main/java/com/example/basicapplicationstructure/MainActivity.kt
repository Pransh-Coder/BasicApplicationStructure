package com.example.basicapplicationstructure

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.basicapplicationstructure.presentation.SearchComposable
import com.example.basicapplicationstructure.presentation.SearchResultsViewModel
import com.example.basicapplicationstructure.ui.theme.BasicApplicationStructureTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<SearchResultsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val context = LocalContext.current

            LaunchedEffect(viewModel.errors) {
                viewModel.errors.collectLatest {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }

            BasicApplicationStructureTheme {
                SearchComposable(state, onSearchClicked = {
                    viewModel.getSearchResults(
                        nameStartsWith = it,
                        maxRows = 10,
                        userName = "keep_truckin"
                    )
                },onBackClicked = {
                    onBackPressedDispatcher.onBackPressed()
                })
            }
        }
    }
}
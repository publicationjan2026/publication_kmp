package com.publication.shiksharth.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.publication.shared.theme.CardBackground
import com.publication.shared.theme.robotoFont
import com.publication.shared.theme.robotoFontFamily
import com.publication.shared.theme.searchBarBorder
import com.publication.shared.theme.searchContainerColorCode
import com.publication.shiksharth_publication.commonDashboard.model.ChapterEntity
import com.publication.shiksharth_publication.commonDashboard.ui.ChapterCard
import com.publication.shiksharth_publication.commonDashboard.viewModel.DashboardViewModel
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import shiksharth_kmp.composeapp.generated.resources.Res
import shiksharth_kmp.composeapp.generated.resources.ic_back_arrow_search
import shiksharth_kmp.composeapp.generated.resources.placeholder_search
import shiksharth_kmp.composeapp.generated.resources.searching_for
import shiksharth_kmp.composeapp.generated.resources.title_no_result_found


@Composable
fun DashboardSearchScreen(
    vm: DashboardViewModel,
    onVideoBtnClick: (String) -> Unit,
    onFlipBookClick: (String) -> Unit,
    onBackPress: () -> Boolean
) {
    LaunchedEffect(Unit) {
        vm.fetchAllChapters()
    }

    DashboardSearchBar(
        listOfChapter = vm.allChapterList,
        onVideoBtnClick = onVideoBtnClick,
        onFlipBookClick = onFlipBookClick
    ) {
        onBackPress()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardSearchBar(
    listOfChapter: StateFlow<List<ChapterEntity>>,
    onVideoBtnClick: (String) -> Unit,
    onFlipBookClick: (String) -> Unit,
    onBackPressed: () -> Unit
) {

    val chapters by listOfChapter.collectAsState(emptyList())
    var query by rememberSaveable { mutableStateOf("") }

    val filteredList = remember(query, chapters) {
        if (query.isBlank()) chapters
        else chapters.filter {
            it.chapterName.contains(query, ignoreCase = true)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CardBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(searchContainerColorCode)
                    .statusBarsPadding()
                    .padding(vertical = 12.dp)
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = onBackPressed) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back_arrow_search),
                        contentDescription = "Back"
                    )
                }

                OutlinedSearchBar(
                    modifier = Modifier.weight(1f),
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = {}
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF7F7F7))
        ) {

            if (query.isNotBlank()) {
                Text(
                    text = stringResource(Res.string.searching_for, query),
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                    fontFamily = robotoFontFamily(), fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }

            if (filteredList.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.title_no_result_found),
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        fontFamily = robotoFontFamily(), fontWeight = FontWeight.SemiBold
                    )
                }

            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),

                    verticalArrangement = Arrangement.spacedBy(16.dp),

                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = 24.dp
                    )
                ) {
                    items(
                        items = filteredList
                    ) { chapter ->

                        ChapterCard(
                            chapter = chapter,
                            onVideoClick = onVideoBtnClick,
                            onFlipClick = onFlipBookClick
                        )
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        placeholder = {
            Text(stringResource(Res.string.placeholder_search), fontSize = 14.sp, fontFamily = robotoFont(), fontWeight = FontWeight.Normal)
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        textStyle = TextStyle(fontSize = 14.sp, fontFamily = robotoFont(), fontWeight = FontWeight.Normal),
        shape = RoundedCornerShape(50),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(query) }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CardBackground,
            unfocusedContainerColor = CardBackground,
            focusedBorderColor = searchBarBorder,
            unfocusedBorderColor = searchBarBorder,
            cursorColor = Color.Black
        )
    )
}
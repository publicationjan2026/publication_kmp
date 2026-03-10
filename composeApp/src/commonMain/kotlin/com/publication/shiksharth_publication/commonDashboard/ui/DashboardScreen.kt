package com.publication.shiksharth_publication.commonDashboard.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.publication.shared.theme.BorderColor
import com.publication.shared.theme.CardBackground
import com.publication.shared.theme.Primary
import com.publication.shared.theme.PrimaryLight
import com.publication.shared.theme.surfaceContainerColor
import shiksharth_kmp.composeapp.generated.resources.chapter_title
import shiksharth_kmp.composeapp.generated.resources.ic_flip_book
import shiksharth_kmp.composeapp.generated.resources.ic_flip_book_icon
import shiksharth_kmp.composeapp.generated.resources.ic_play_video
import shiksharth_kmp.composeapp.generated.resources.read_title
import shiksharth_kmp.composeapp.generated.resources.title_smart_learning
import shiksharth_kmp.composeapp.generated.resources.watch_title
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import com.publication.shared.theme.TextPrimary
import com.publication.shared.theme.TextSecondary
import com.publication.shared.theme.robotoFont
import com.publication.shiksharth_publication.commonDashboard.model.ChapterEntity
import com.publication.shiksharth_publication.commonDashboard.model.ClassEntity
import com.publication.shiksharth_publication.commonDashboard.model.SubjectEntity
import com.publication.shiksharth_publication.commonDashboard.viewModel.DashboardViewModel
import com.publication.shiksharth_publication.commonShimmerEffect.modernShimmer
import com.publication.shiksharth_publication.toCamelCase
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import shiksharth_kmp.composeapp.generated.resources.Res


@Composable
fun DashboardScreenUI(
    viewModel: DashboardViewModel,
    listOfChapter: StateFlow<List<ChapterEntity>>,
    isDataExist: StateFlow<Boolean>,
    listOfClasses: StateFlow<List<ClassEntity>>,
    listOfSubjects: StateFlow<List<SubjectEntity>>,
    selectedClass: StateFlow<String>,
    selectedSubject: StateFlow<String>,
    selectedSubjectName: StateFlow<String>,
    onClassClick: (String) -> Unit,
    onSubjectClick: (String) -> Unit,
    onVideoBtnClick: (String) -> Unit,
    onFlipBookClick: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    var showShimmer by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (showShimmer) {
            delay(1500)
            showShimmer = false
        }
    }
    val isDataExist by isDataExist.collectAsState()
    val chapters by listOfChapter.collectAsState()
    val subjects by listOfSubjects.collectAsState()
    val selectedSubjectId by selectedSubject.collectAsState()
    val selectedSubjectNameValue by selectedSubjectName.collectAsState()
    val classes by listOfClasses.collectAsState()
    val selectedClassName by selectedClass.collectAsState()

    LaunchedEffect(classes) {
        if (classes.isNotEmpty() && selectedClassName.isEmpty()) {
            onClassClick(classes.first().className)
        }
    }


    Scaffold(
        topBar = { ModernTopBar(onSearchClick) },
        containerColor = Color.White
    ) { padding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(vertical = 4.dp, horizontal = 8.dp),
            shape = RoundedCornerShape(24.dp),
            color = surfaceContainerColor,
            shadowElevation = 1.dp
        ) {
            if (!isDataExist || showShimmer) {
                DashboardScreenShimmer()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    item {
                        Column {
                            Text(
                                text = selectedClassName,
                                fontSize = 24.sp,
                                fontFamily = robotoFont(),
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            Text(
                                text = selectedSubjectNameValue,
                                fontSize = 16.sp,
                                fontFamily = robotoFont(),
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }
                    }

                    item {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(classes, key = { it.className }) { item ->
                                ClassChip(
                                    classItem = item,
                                    isSelected = selectedClassName == item.className,
                                    onClick = onClassClick
                                )
                            }
                        }
                    }

                    item {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(subjects, key = { it.subjectName }) { subject ->
                                SubjectCard(
                                    subject = subject,
                                    isSelected = selectedSubjectId == subject.subjectName,
                                    onClick = onSubjectClick
                                )
                            }
                        }
                    }

                    items(chapters, key = { it.chapterName }) { chapter ->
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
fun ModernTopBar(onSearchClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        title = {
            Text(
                text = stringResource(Res.string.title_smart_learning),
                fontFamily = robotoFont(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = TextPrimary
            )
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(start = 16.dp),
                painter = painterResource(Res.drawable.ic_flip_book_icon),
                contentDescription = null,
                tint = Color.Unspecified
            )
        },
    )
}

@Composable
fun ClassChip(
    classItem: ClassEntity,
    isSelected: Boolean,
    onClick: (String) -> Unit
) {
    Surface(
        onClick = { onClick(classItem.className) },
        shape = RoundedCornerShape(50),
        color = if (isSelected) Primary else Color.White,
        border = if (!isSelected) BorderStroke(1.dp, BorderColor) else null
    ) {
        Text(
            text = classItem.className,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            color = if (isSelected) Color.White else TextPrimary,
            fontFamily = robotoFont(),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun SubjectCard(
    subject: SubjectEntity,
    isSelected: Boolean,
    onClick: (String) -> Unit
) {
    Surface(
        modifier = Modifier.size(110.dp),
        onClick = { onClick(subject.subjectName) },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) PrimaryLight else CardBackground,
        border = BorderStroke(1.dp, if (isSelected) Primary else BorderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CoilImage(
                imageModel = { subject.subjectImage },
                modifier = Modifier.size(36.dp),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                ),
                loading = {
                    CircularProgressIndicator()
                },
                failure = {
                        Text("Image error")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subject.subjectName.toCamelCase(),
                fontSize = 13.sp,
                fontFamily = robotoFont(),
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                maxLines = 2,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ChapterCard(
    chapter: ChapterEntity,
    onVideoClick: (String) -> Unit,
    onFlipClick: (String) -> Unit
) {

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = CardBackground,
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                CoilImage(
                    imageModel = { chapter.imageUrl },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center
                    ),
                    loading = {
                        CircularProgressIndicator()
                    },
                    failure = {
                        Text("Image error")
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = stringResource(Res.string.chapter_title) + " ${chapter.chapterNo}",
                        fontFamily = robotoFont(),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = chapter.chapterName,
                        fontFamily = robotoFont(),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = TextSecondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onVideoClick(chapter.videoUrl) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_play_video),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = stringResource(Res.string.watch_title),
                        color = Color.White,
                        fontFamily = robotoFont(), fontWeight = FontWeight.SemiBold
                    )
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { onFlipClick(chapter.flipbookUrl) },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, BorderColor)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_flip_book),
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        stringResource(Res.string.read_title),
                        color = TextPrimary,
                        fontFamily = robotoFont(),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}


@Composable
fun DashboardScreenShimmer() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {


        item {
            Column {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(18.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .modernShimmer()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(30.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .modernShimmer()
                )
            }
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(4) {
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .width(90.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .modernShimmer()
                    )
                }
            }
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(4) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .modernShimmer()
                    )
                }
            }
        }


        items(4) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .modernShimmer()
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .width(110.dp)
                                .height(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .modernShimmer()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(14.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .modernShimmer()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .modernShimmer()
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .modernShimmer()
                    )
                }
            }
        }
    }
}
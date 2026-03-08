package com.publication.shiksharth_publication.commonDashboard


data class ChapterEntity(
    val chapterId: Int = 0,
    val chapterNo: String,
    val chapterName: String,
    val imageUrl: String,
    val videoUrl: String,
    val flipbookUrl: String,
    val subjectId: Int   // FK
)

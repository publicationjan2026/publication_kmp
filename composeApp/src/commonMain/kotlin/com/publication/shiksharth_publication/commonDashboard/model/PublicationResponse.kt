package com.publication.shiksharth_publication.commonDashboard.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassEntity(
    @SerialName("class")
    val className: String,
    val subjects: List<SubjectEntity>
)

@Serializable
data class SubjectEntity(
    @SerialName("subject_name")
    val subjectName: String,
    @SerialName("subject_image")
    val subjectImage: String,
    val chapters: List<ChapterEntity>
)

@Serializable
data class ChapterEntity(
    @SerialName("chapter_no")
    val chapterNo: String,
    @SerialName("chapter_name")
    val chapterName: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("video_url")
    val videoUrl: String,
    @SerialName("flipbook_url")
    val flipbookUrl: String
)
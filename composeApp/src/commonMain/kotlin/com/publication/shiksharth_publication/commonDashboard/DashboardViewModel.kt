package com.publication.shiksharth_publication.commonDashboard


import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DashboardViewModel   {
    private val _subjectList = MutableStateFlow<List<SubjectEntity>>(listOf(SubjectEntity(1,"English",
        "https://cdn-icons-png.flaticon.com/512/2942/2942132.png","1"),
        SubjectEntity(2,"Hindi",
            "https://cdn-icons-png.flaticon.com/512/2942/2942132.png","1"),
    ))
    val subjectList: StateFlow<List<SubjectEntity>> = _subjectList

    private val _chapterList = MutableStateFlow<List<ChapterEntity>>(listOf(ChapterEntity(
        1,
         "1",
         "Living and Non-Living Things",
    "https://cdn-icons-png.flaticon.com/512/2232/2232688.png",
    "https://www.youtube.com/watch?v=8idTHuX0c0M",
    "https://ncert.nic.in/textbook/pdf/lebo113.pdf",
        1
    )))
    val chapterList: StateFlow<List<ChapterEntity>> = _chapterList

    private val _allChapterList = MutableStateFlow<List<ChapterEntity>>(listOf(ChapterEntity(
        1,
        "1",
        "Living and Non-Living Things",
        "https://cdn-icons-png.flaticon.com/512/2232/2232688.png",
        "https://www.youtube.com/watch?v=8idTHuX0c0M",
        "https://ncert.nic.in/textbook/pdf/lebo113.pdf",
        1
    )))
    val allChapterList: StateFlow<List<ChapterEntity>> = _allChapterList

    private val _selectedClass = MutableStateFlow("")
    val selectedClass: StateFlow<String> = _selectedClass

    private val _selectedSubjectId = MutableStateFlow(-1)
    val selectedSubject: StateFlow<Int> = _selectedSubjectId

    private val _selectedSubjectName = MutableStateFlow("")
    val selectedSubjectName: StateFlow<String> = _selectedSubjectName

    private val _isDataFilled = MutableStateFlow(true)
    val isDataFilled : StateFlow<Boolean> = _isDataFilled

    private val _fetchAllClass : MutableStateFlow<List<ClassEntity>> = MutableStateFlow<List<ClassEntity>>(emptyList())
    val fetchAllClass : StateFlow<List<ClassEntity>> = _fetchAllClass
    fun setSelectedClass(value: String) {
        _selectedClass.value = value
        _subjectList.value = listOf(SubjectEntity(1,"English",
            "https://cdn-icons-png.flaticon.com/512/2942/2942132.png","1"),
            SubjectEntity(2,"Hindi",
                "https://cdn-icons-png.flaticon.com/512/2942/2942132.png","1"),
        )
        _selectedSubjectId.value = 1
    }

    fun setSelectedSubject(value: Int) {
        _selectedSubjectId.value = value
        _selectedSubjectName.value = "English"
        _chapterList.value= listOf(ChapterEntity(
            1,
            "1",
            "Living and Non-Living Things",
            "https://cdn-icons-png.flaticon.com/512/2232/2232688.png",
            "https://www.youtube.com/watch?v=8idTHuX0c0M",
            "https://ncert.nic.in/textbook/pdf/lebo113.pdf",
            1
        ))
    }




}
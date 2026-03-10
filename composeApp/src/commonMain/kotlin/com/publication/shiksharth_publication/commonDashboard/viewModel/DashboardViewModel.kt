package com.publication.shiksharth_publication.commonDashboard.viewModel

import com.publication.shiksharth_publication.commonDashboard.model.ChapterEntity
import com.publication.shiksharth_publication.commonDashboard.model.ClassEntity
import com.publication.shiksharth_publication.commonDashboard.model.SubjectEntity
import com.publication.shiksharth_publication.commonDashboard.repo.PublicationRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class DashboardViewModel(
    private val repo: PublicationRepo
) {

    private var _fetchAllClass: MutableStateFlow<List<ClassEntity>> = MutableStateFlow(emptyList())
    var fetchAllClass: StateFlow<List<ClassEntity>> = _fetchAllClass

    private var _subjectList: MutableStateFlow<List<SubjectEntity>> = MutableStateFlow(emptyList())
    val subjectList: StateFlow<List<SubjectEntity>> = _subjectList

    private val _chapterList: MutableStateFlow<List<ChapterEntity>> = MutableStateFlow(emptyList())
    val chapterList: StateFlow<List<ChapterEntity>> = _chapterList

    private val _allChapterList = MutableStateFlow<List<ChapterEntity>>(emptyList())
    val allChapterList: StateFlow<List<ChapterEntity>> = _allChapterList

    private val _selectedClass = MutableStateFlow("")
    val selectedClass: StateFlow<String> = _selectedClass

    private val _selectedSubjectId = MutableStateFlow("")
    val selectedSubject: StateFlow<String> = _selectedSubjectId

    private val _selectedSubjectName = MutableStateFlow("")
    val selectedSubjectName: StateFlow<String> = _selectedSubjectName

    private val _isDataFilled = MutableStateFlow(true)
    val isDataFilled: StateFlow<Boolean> = _isDataFilled


    suspend fun loadData() {
        val json = repo.loadJson()
        _fetchAllClass.value = repo.parseJson(json)
    }

    fun setSelectedClass(value: String) {
        _selectedClass.value = value
        fetchSubjectByClassName()
    }

    fun fetchSubjectByClassName() {
        _subjectList.value =
            _fetchAllClass.value.firstOrNull { it.className == _selectedClass.value }
                ?.subjects
                ?: emptyList()

        _subjectList.value.firstOrNull()?.subjectName?.let {
            setSelectedSubject(it)
        }


    }

    fun setSelectedSubject(value: String) {
        _selectedSubjectId.value = value
        _selectedSubjectName.value = value
        fetchChapters()
    }

    fun fetchChapters() {
        _chapterList.value =
            _fetchAllClass.value
                .firstOrNull { it.className == _selectedClass.value }
                ?.subjects
                ?.firstOrNull { it.subjectName == _selectedSubjectName.value }
                ?.chapters
                ?: emptyList()
    }

    fun fetchAllChapters() {
        _allChapterList.value = _fetchAllClass.value
                .flatMap { it.subjects }
                .flatMap { it.chapters }

    }
}
package com.publication.shiksharth_publication.commonDashboard.repo

import com.publication.shiksharth_publication.commonDashboard.model.ClassEntity
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import shiksharth_kmp.composeapp.generated.resources.Res

class PublicationRepo {

    fun parseJson(json: String): List<ClassEntity> {
        return Json.decodeFromString(json)
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun loadJson(): String {

        val bytes = Res.readBytes("files/PublicationResponse.json")

        return bytes.decodeToString()
    }

}
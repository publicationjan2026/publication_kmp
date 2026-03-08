package com.publication.shiksharth_publication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
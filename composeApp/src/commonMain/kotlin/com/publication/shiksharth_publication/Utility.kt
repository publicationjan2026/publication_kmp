package com.publication.shiksharth_publication




fun String.toCamelCase(): String {
    return this
        .lowercase()
        .split(" ", "_", "-")
        .joinToString("") { it.replaceFirstChar { ch -> ch.uppercase() } }
}

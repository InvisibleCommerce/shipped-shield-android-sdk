package com.shippedsuite.shippedshield

enum class Mode(val value: String) {
    Development("development"), Production("production");

    fun baseUrl(): String {
        return when (this) {
            Development -> "https://api-staging.shippedsuite.com"
            Production -> "https://api.shippedsuite.com"
        }
    }
}
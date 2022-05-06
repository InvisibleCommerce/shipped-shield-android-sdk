package com.shippedsuite.shippedshield

enum class Environment(val value: String) {
    DEVELOPMENT("development"), PRODUCTION("production");

    fun baseUrl(): String {
        return when (this) {
            DEVELOPMENT -> "https://api-staging.shippedsuite.com"
            PRODUCTION -> "https://api.shippedsuite.com"
        }
    }
}

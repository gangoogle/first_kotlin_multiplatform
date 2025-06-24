package org.gangoogle.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
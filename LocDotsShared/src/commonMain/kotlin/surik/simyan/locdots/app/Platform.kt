package surik.simyan.locdots.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
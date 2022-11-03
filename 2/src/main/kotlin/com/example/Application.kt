package com.example
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.application.*
import com.example.databse.DatabaseFactory
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS) {
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)

        allowHost("client-host", subDomains = listOf("en", "de", "es","pl"))
        allowHost("www.google.com", schemes = listOf("http", "https"))
        allowHost("localhost:3400", schemes = listOf("http", "https"))
    }
    DatabaseFactory.init()
    configureSecurity()
    configureSerialization()
    configureRouting()
}

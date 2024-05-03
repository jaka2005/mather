package funn.j2k.plugins

import funn.j2k.routes.sequenceRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        sequenceRouting()
    }
}

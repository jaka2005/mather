package funn.j2k.routes

import io.ktor.server.routing.*

fun Route.sequenceRouting() {
    route("/seq") {
        primesRoute()
    }
}

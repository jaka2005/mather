package funn.j2k.routes

import funn.j2k.math.Primes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val primes = Primes().iterator()

fun Route.primesRoute() {
    route("/primes") {
        get {
            call.respond(primes.next().toString())
        }
    }
}

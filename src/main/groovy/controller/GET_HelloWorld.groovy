package controller

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class GET_HelloWorld implements Handler<RoutingContext> {

    @Override
    void handle(RoutingContext routingContext) {
        def response = routingContext.response()
        response.setChunked(true)
                .write("Hello World!")
                .end()
    }
}

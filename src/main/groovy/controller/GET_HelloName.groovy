package controller

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class GET_HelloName implements Handler<RoutingContext> {

    @Override
    void handle(RoutingContext routingContext) {
        String name = routingContext.request().getParam("name")
        def response = routingContext.response()
        response.setChunked(true)
                .write("Hello ${name}")
                .end()
    }
}

package app

import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router

class Runner {
    static void main(String[] args) {
        Vertx vertx = Vertx.vertx()
        HttpServer httpServer = vertx.createHttpServer()
        Router router = Router.router(vertx)

        router.get("/helloworld").handler({ routingContext ->
            def response = routingContext.response()
            response.setChunked(true)
                    .write("Hello World!")
                    .end()
        })

        router.get("/hello/:name").handler({ routingContext ->
            String name = routingContext.request().getParam("name")
            def response = routingContext.response()
            response.setChunked(true)
                    .write("Hello ${name}")
                    .end()
        })


        httpServer
                .requestHandler(router.&accept)
                .listen(8080, { event ->
            if (event.succeeded()) {
                println("RestServer started at http://127.0.0.1:${httpServer.actualPort()}")
            } else {
                throw new RuntimeException("Unable to start RestServer at http://127.0.0.1:${httpServer.actualPort()}", event.cause())
            }
        })

    }
}

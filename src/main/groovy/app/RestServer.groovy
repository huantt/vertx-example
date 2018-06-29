package app

import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router

class RestServer {

    int port
    Vertx vertx = Vertx.vertx()
    HttpServer httpServer
    Router router

    RestServer(int port) {
        this.port = port
        this.httpServer = vertx.createHttpServer()
        this.router = Router.router(vertx)
        setupRouter()
    }


    void start() {
        httpServer
                .requestHandler(router.&accept)
                .listen(port, { event ->
            if (event.succeeded()) {
                println("RestServer started at http://127.0.0.1:${httpServer.actualPort()}")
            } else {
                throw new RuntimeException("Unable to start RestServer at http://127.0.0.1:${httpServer.actualPort()}", event.cause())
            }
        })

    }

    void setupRouter(){
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
    }
}

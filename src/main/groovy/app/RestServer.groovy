package app

import controller.GET_HelloName
import controller.GET_HelloWorld
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router

class RestServer {

    private int port
    private Vertx vertx = Vertx.vertx()
    private HttpServer httpServer
    private Router router

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

    void setupRouter() {
        router.get("/helloworld").handler(new GET_HelloWorld())
        router.get("/hello/:name").handler(new GET_HelloName())
    }
}

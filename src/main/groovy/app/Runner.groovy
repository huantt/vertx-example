package app

class Runner {

    static void main(String[] args) {
        AppConfig appConfig = AppConfig.newInstance(new File("config/config.yml"))
        RestServer restServer = new RestServer(appConfig.httpPort)
        restServer.start()
    }
}

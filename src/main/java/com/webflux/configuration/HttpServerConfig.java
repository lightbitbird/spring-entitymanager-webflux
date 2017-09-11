package com.webflux.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
//import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import reactor.ipc.netty.http.server.HttpServer;
import reactor.spring.context.config.EnableReactor;

@Configuration
@EnableAutoConfiguration
public class HttpServerConfig {
    private final Environment environment;

    @Autowired
    public HttpServerConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public HttpServer httpServer(RouterFunction<?> routerFunction) {
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFunction);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create(environment.getProperty("server.address"), Integer.valueOf(environment.getProperty("server.port")));
        System.out.println("-------" + environment.getProperty("server.address"));
        System.out.println("-------" + environment.getProperty("server.port"));
        server.newHandler(adapter);
        return server;
    }
}
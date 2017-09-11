/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webflux;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webflux.routing.HelloRouter;
import com.webflux.routing.IndexRouter;
import com.webflux.routing.PostEchoRouter;
import com.webflux.routing.StreamRouter;

import reactor.ipc.netty.http.server.HttpServer;

@SpringBootApplication
public class EntityManagerApplication {

//    @Autowired
//    private WebFluxApplicationConfig context;

    public static void main(String[] args) throws Exception {

        ApplicationContext app = new AnnotationConfigApplicationContext(WebFluxApplicationConfig.class);
        EntityManagerFactory factory = app.getBean(EntityManagerFactory.class);
        factory.createEntityManager();
        SpringApplication.run(EntityManagerApplication.class);
    }

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(IndexRouter indexRouter, PostEchoRouter echoRouter,
            HelloRouter helloRouter, StreamRouter streamRouter) {
        return indexRouter.routes().and(helloRouter.routes()).and(echoRouter.routes()).and(streamRouter.routes())
                .and(streamRouter.routes());
    }

    @Bean
    public HttpServer server(RouterFunction<?> router) {
//        HttpHandler handler = toHttpHandler(router);
        HttpServer httpServer = HttpServer.create("localhost", 8080);
//        httpServer.start(new ReactorHttpHandlerAdapter(handler));
        return httpServer;
    }

}

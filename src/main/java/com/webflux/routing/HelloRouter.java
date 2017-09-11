package com.webflux.routing;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webflux.domain.Person;
import com.webflux.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class HelloRouter {
    @Autowired
    private PersonService personService;

	public RouterFunction<ServerResponse> routes() {
		return nest(path("/hello"), route(GET("/"), this::hello).andRoute(GET("/hoge"), this::hoge)
				.andRoute(GET("/{name}"), this::helloName));
	}

	private Mono<ServerResponse> hello(ServerRequest req) {
		return ok().body(Flux.just("Hello ", "Webflux"), String.class);
	}

	private Mono<ServerResponse> hoge(ServerRequest req) {
	    Optional<Person> pers1 = personService.findById(Long.valueOf(1));

		return ok().body(Flux.just(pers1.get().getFirstName(), pers1.get().getLastName()), String.class);
	}

	private Mono<ServerResponse> helloName(ServerRequest req) {
		return ok().contentType(MediaType.APPLICATION_JSON).body(Flux.just("Hello ", req.pathVariable("name")),
				String.class);
	}
}

package com.webflux.routing;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class StreamRouter {

	public RouterFunction<ServerResponse> routes() {
		return route(GET("/stream"), this::stream).andRoute(GET("/multiStream"), this::multiStream);
	}

	private Mono<ServerResponse> stream(ServerRequest req) {
		Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
		Flux<Integer> flux = Flux.fromStream(stream.filter(i -> i <= 10)).delayElements(Duration.ofSeconds(1));
		return ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(flux, Integer.class);
	}

	private Mono<ServerResponse> multiStream(ServerRequest req) {
		Stream<Integer> stream = Stream.iterate(0, i -> i + 1);

		Flux<Map<String, Integer>> flux = Flux.fromStream(stream).zipWith(Flux.interval(Duration.ofSeconds(1)))
				.map(tuple -> Collections.singletonMap("value", tuple.getT1()));
		return ok().contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(BodyInserters.fromPublisher(flux, new ParameterizedTypeReference<Map<String, Integer>>() {
				}));
	}
}

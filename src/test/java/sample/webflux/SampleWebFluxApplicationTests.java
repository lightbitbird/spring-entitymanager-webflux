package sample.webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.webflux.EntityManagerApplication;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {EntityManagerApplication.class})
public class SampleWebFluxApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	public void testIndex() throws Exception {
		this.webClient.get().uri("/").accept(MediaType.TEXT_PLAIN).exchange()
				.expectBody(String.class).isEqualTo("Webflux works!");
	}

	@Test
	public void testEcho() throws Exception {
		this.webClient.post().uri("/echo").contentType(MediaType.TEXT_PLAIN)
				.accept(MediaType.TEXT_PLAIN)
				.body(Mono.just("Hello WebFlux!"), String.class).exchange()
				.expectBody(String.class).isEqualTo("Hello WebFlux!");
	}

	@Test
	public void testMultiStream() throws Exception {
		this.webClient.get().uri("/multiStream").accept(MediaType.TEXT_PLAIN).exchange()
				.expectBody(String.class).isEqualTo("Webflux works!");
	}

}

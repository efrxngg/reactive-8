package edu.spring.reactive.integration.claro.utils;

import edu.spring.reactive.integration.claro.interfaces.ClaroApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class MicroEISClient implements ClaroApiClient {

    @Value("${integration.service.microeis.url}")
    private String URI;

    public <T> Mono<T> exchange(HttpEntity request, Class<T> result) {
        return WebClient.create(URI).post()
                .headers(h -> h.addAll(request.getHeaders()))
                .bodyValue(request.getBody() != null ? request.getBody() : Collections.emptyMap())
                .retrieve()
                .bodyToMono(result);
    }

}

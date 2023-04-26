package edu.spring.reactive.integration.interfaces;


import org.springframework.http.HttpEntity;
import reactor.core.publisher.Mono;

public interface ClaroApiClient {
    <T> Mono<T> exchange(HttpEntity request, Class<T> result);
}

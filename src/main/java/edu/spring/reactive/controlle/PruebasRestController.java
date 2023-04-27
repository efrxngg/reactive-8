package edu.spring.reactive.controlle;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.spring.reactive.integration.ClaroApiFactory;
import edu.spring.reactive.integration.utils.JsonUtils;
import edu.spring.reactive.integration.utils.MicroType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class PruebasRestController {

    private final JsonUtils jsonUtils;
    private final ClaroApiFactory claroApiFactory;

    public PruebasRestController(ClaroApiFactory claroApiFactory, JsonUtils jsonUtils) {
        this.claroApiFactory = claroApiFactory;
        this.jsonUtils = jsonUtils;
    }


    @GetMapping("/m-gateway")
    public Mono<String> testMicroGateway() throws IllegalAccessException {

        HttpHeaders header = new HttpHeaders();
        header.setAccept(Collections.singletonList(MediaType.ALL));
        header.setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"idRequest\":\"EDX:GETACCEPTANCEREQUIREMENTS\",\"params\":{\"status\":\"cancelado\"}}";
        HttpEntity<?> request = new HttpEntity<>(body, header);

        return claroApiFactory
                .getInstance(MicroType.GATEWAY)
                .exchange(request, String.class);
    }

    @GetMapping("/m-persistor")
    public void testMicroPersistor() throws IllegalAccessException {

        HttpEntity<?> request = new HttpEntity<>(Collections.singletonMap(
                "informationService", "EDX:FindReportByOrdNumAndServNum"));

        Mono<String> root = claroApiFactory
                .getInstance(MicroType.PERSISTOR)
                .exchange(request, String.class);

        root.subscribe(System.out::println);
    }

    @GetMapping("/m-eis")
    public void testMicroEIS() throws IllegalAccessException {

        HttpEntity<?> request = new HttpEntity<>(Collections.singletonMap(
                "informationService", "EDX:FindReportByOrdNumAndServNum"));

        Mono<String> root = claroApiFactory
                .getInstance(MicroType.EIS)
                .exchange(request, String.class);

        root.subscribe(System.out::println);
    }


    public void testBody() throws JsonProcessingException {
        Map<String, ?> a = createBody(
                1, "A",
                2, Collections.singletonMap("a", "b")
        );
        a.forEach((x, y) -> System.out.println(x + ":" + y));

        String json = jsonUtils.convertirAJson(createBody(
                1, "A",
                2, Collections.singletonMap("a", "b")
        ));
        System.out.println(json);
    }

    @SafeVarargs
    private final <T> Map<String, T> createBody(T... body) {
        Map<String, T> result = new HashMap<>();
        for (int a = 0; a < body.length; a += 2) {
            result.put(String.valueOf(body[a]), body[a + 1]);
        }
        return result;
    }

    public Mono<String> test(@RequestBody String body) {
        System.out.println(body);
        return Mono.just("Master");
    }
}

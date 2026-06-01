package com.ankit007.recipe;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ApiDocsController {

    private final OpenAPI openAPI;

    @GetMapping("/v3/api-docs")
    public Mono<OpenAPI> getApiDocs() {
        return Mono.just(openAPI);
    }
}


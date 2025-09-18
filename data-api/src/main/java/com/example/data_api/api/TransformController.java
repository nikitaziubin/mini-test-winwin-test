package com.example.data_api.api;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

record TransformRequest(@NotBlank String text) {}
record TransformResponse(String result) {}

@RestController
@RequestMapping("/api")
public class TransformController {

    private final String internalToken;

    public TransformController(@Value("${internal.token}") String internalToken) {
        this.internalToken = internalToken;
    }

    @PostMapping("/transform")
    public TransformResponse transform(@RequestHeader(value = "X-Internal-Token", required = false) String token,
                                       @RequestBody TransformRequest req) {
        if (token == null || !token.equals(internalToken)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid internal token");
        }

        String result = new StringBuilder(req.text()).reverse().toString().toUpperCase();
        return new TransformResponse(result);
    }
}

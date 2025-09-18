package com.example.auth_api.service;

import com.example.auth_api.model.ProcessingLog;
import com.example.auth_api.repository.ProcessingLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
public class ProcessService {

    private final RestTemplate restTemplate;
    private final ProcessingLogRepository logs;
    private final String internalToken;
    private final String dataApiBaseUrl;

    public ProcessService(RestTemplate restTemplate,
                          ProcessingLogRepository logs,
                          @Value("${internal.token}") String internalToken,
                          @Value("${dataapi.base-url}") String dataApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.logs = logs;
        this.internalToken = internalToken;
        this.dataApiBaseUrl = dataApiBaseUrl;
    }

    public String process(UUID userId, String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Internal-Token", internalToken);
        HttpEntity<Map<String, String>> req = new HttpEntity<>(Map.of("text", text), headers);

        ResponseEntity<Map> resp = restTemplate.postForEntity(dataApiBaseUrl + "/api/transform", req, Map.class);
        if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
            throw new IllegalStateException("Transform failed");
        }
        String result = String.valueOf(resp.getBody().get("result"));

        ProcessingLog log = new ProcessingLog();
        log.setUserId(userId);
        log.setInputText(text);
        log.setOutputText(result);
        logs.save(log);

        return result;
    }
}

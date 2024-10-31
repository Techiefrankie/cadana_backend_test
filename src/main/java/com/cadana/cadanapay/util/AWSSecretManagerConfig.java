package com.cadana.cadanapay.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.Map;

@Slf4j
@Component
public class AWSSecretManagerConfig {
    private final SecretsManagerClient secretsManagerClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AWSSecretManagerConfig() {
        this.secretsManagerClient = SecretsManagerClient.builder()
                .region(Region.of("us-west-2"))  // could be any other region
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public String getSecret(String secretName) {
        try {
            log.info("Getting secret {}", secretName);

            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            GetSecretValueResponse getSecretValueResponse = secretsManagerClient.getSecretValue(getSecretValueRequest);

            if (getSecretValueResponse.secretString() != null) {
                Map<String, String> secrets = objectMapper.readValue(getSecretValueResponse.secretString(), Map.class);
                return secrets.get(secretName);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}

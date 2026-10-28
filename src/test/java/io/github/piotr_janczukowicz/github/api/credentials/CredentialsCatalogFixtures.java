package io.github.piotr_janczukowicz.github.api.credentials;

import java.util.List;

final class CredentialsCatalogFixtures {

    private CredentialsCatalogFixtures() {}

    static GitHubRevokePayload revokePayload() {
        return GitHubRevokePayloadBuilder.builder()
                .credentials(List.of(
                        "ghp_1234567890abcdef1234567890abcdef12345678",
                        "github_pat_0A1B2C3D4E5F6G7H8I9J0K_ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456",
                        "gho_1234567890abcdef1234567890abcdef12345678",
                        "ghu_1234567890abcdef1234567890abcdef12345678",
                        "ghr_1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef1234567890ab"))
                .build();
    }
}

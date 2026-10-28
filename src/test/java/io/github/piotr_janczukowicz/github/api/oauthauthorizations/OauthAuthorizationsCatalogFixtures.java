package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;

public final class OauthAuthorizationsCatalogFixtures {

    private OauthAuthorizationsCatalogFixtures() {}

    static GitHubApplicationGrantApp applicationGrantApp() {
        return GitHubApplicationGrantAppBuilder.builder()
                .clientId("client_id")
                .name("name")
                .url("https://api.github.com/url")
                .build();
    }

    static GitHubCreateAuthorizationPayload createAuthorizationPayload() {
        return GitHubCreateAuthorizationPayloadBuilder.builder()
                .scopes(List.of("scopes"))
                .note("Update all gems")
                .noteUrl("note_url")
                .clientId("client_id")
                .clientSecret("client_secret")
                .fingerprint("fingerprint")
                .build();
    }

    static GitHubGetOrCreateAuthorizationForAppPayload getOrCreateAuthorizationForAppPayload() {
        return GitHubGetOrCreateAuthorizationForAppPayloadBuilder.builder()
                .clientSecret("client_secret")
                .scopes(List.of("scopes"))
                .note("Update all gems")
                .noteUrl("note_url")
                .fingerprint("fingerprint")
                .build();
    }

    static GitHubGetOrCreateAuthorizationForAppAndFingerprintPayload
            getOrCreateAuthorizationForAppAndFingerprintPayload() {
        return GitHubGetOrCreateAuthorizationForAppAndFingerprintPayloadBuilder.builder()
                .clientSecret("client_secret")
                .scopes(List.of("scopes"))
                .note("Update all gems")
                .noteUrl("note_url")
                .build();
    }

    static GitHubUpdateAuthorizationPayload updateAuthorizationPayload() {
        return GitHubUpdateAuthorizationPayloadBuilder.builder()
                .scopes(List.of("scopes"))
                .addScopes(List.of("add_scopes"))
                .removeScopes(List.of("remove_scopes"))
                .note("Update all gems")
                .noteUrl("note_url")
                .fingerprint("fingerprint")
                .build();
    }

    @SuppressWarnings("deprecation")
    static GitHubApplicationGrant applicationGrant() {
        return GitHubApplicationGrantBuilder.builder()
                .id(1L)
                .url("https://api.github.com/applications/grants/1")
                .app(applicationGrantApp())
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .scopes(List.of("public_repo"))
                .user(UsersCatalogFixtures.octocat())
                .build();
    }

    @SuppressWarnings("deprecation")
    static GitHubListGrantsFilter listGrantsFilter() {
        return GitHubListGrantsFilterBuilder.builder().clientId("client_id").build();
    }

    @SuppressWarnings("deprecation")
    static GitHubListAuthorizationsFilter listAuthorizationsFilter() {
        return GitHubListAuthorizationsFilterBuilder.builder()
                .clientId("client_id")
                .build();
    }
}

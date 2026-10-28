package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetOrCreateAuthorizationForAppAndFingerprintPayloadTest {

    @Test
    void constructor_shouldThrow_whenClientSecretNull() {
        final GitHubGetOrCreateAuthorizationForAppAndFingerprintPayloadBuilder builder =
                GitHubGetOrCreateAuthorizationForAppAndFingerprintPayloadBuilder.builder(
                                OauthAuthorizationsCatalogFixtures
                                        .getOrCreateAuthorizationForAppAndFingerprintPayload())
                        .clientSecret(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("clientSecret");
    }

    @Test
    void constructor_shouldAcceptNull_whenScopesNull() {
        final GitHubGetOrCreateAuthorizationForAppAndFingerprintPayload value =
                GitHubGetOrCreateAuthorizationForAppAndFingerprintPayloadBuilder.builder(
                                OauthAuthorizationsCatalogFixtures
                                        .getOrCreateAuthorizationForAppAndFingerprintPayload())
                        .scopes(null)
                        .build();

        assertThat(value.scopes()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenScopesGiven() {
        final List<String> source = new ArrayList<>(List.of("first"));
        final GitHubGetOrCreateAuthorizationForAppAndFingerprintPayload value =
                GitHubGetOrCreateAuthorizationForAppAndFingerprintPayloadBuilder.builder(
                                OauthAuthorizationsCatalogFixtures
                                        .getOrCreateAuthorizationForAppAndFingerprintPayload())
                        .scopes(source)
                        .build();

        source.add("second");

        assertThat(value.scopes()).containsExactly("first");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubGetOrCreateAuthorizationForAppAndFingerprintPayload value =
                OauthAuthorizationsCatalogFixtures.getOrCreateAuthorizationForAppAndFingerprintPayload();

        assertThat(value.toString()).contains("clientSecret=<redacted>").doesNotContain("clientSecret=client_secret");
    }
}

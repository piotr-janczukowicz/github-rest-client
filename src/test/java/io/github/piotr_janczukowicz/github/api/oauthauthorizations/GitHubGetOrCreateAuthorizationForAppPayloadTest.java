package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetOrCreateAuthorizationForAppPayloadTest {

    @Test
    void constructor_shouldThrow_whenClientSecretNull() {
        final GitHubGetOrCreateAuthorizationForAppPayloadBuilder builder =
                GitHubGetOrCreateAuthorizationForAppPayloadBuilder.builder(
                                OauthAuthorizationsCatalogFixtures.getOrCreateAuthorizationForAppPayload())
                        .clientSecret(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("clientSecret");
    }

    @Test
    void constructor_shouldAcceptNull_whenScopesNull() {
        final GitHubGetOrCreateAuthorizationForAppPayload value =
                GitHubGetOrCreateAuthorizationForAppPayloadBuilder.builder(
                                OauthAuthorizationsCatalogFixtures.getOrCreateAuthorizationForAppPayload())
                        .scopes(null)
                        .build();

        assertThat(value.scopes()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenScopesGiven() {
        final List<String> source = new ArrayList<>(List.of("first"));
        final GitHubGetOrCreateAuthorizationForAppPayload value =
                GitHubGetOrCreateAuthorizationForAppPayloadBuilder.builder(
                                OauthAuthorizationsCatalogFixtures.getOrCreateAuthorizationForAppPayload())
                        .scopes(source)
                        .build();

        source.add("second");

        assertThat(value.scopes()).containsExactly("first");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubGetOrCreateAuthorizationForAppPayload value =
                OauthAuthorizationsCatalogFixtures.getOrCreateAuthorizationForAppPayload();

        assertThat(value.toString()).contains("clientSecret=<redacted>").doesNotContain("clientSecret=client_secret");
    }
}

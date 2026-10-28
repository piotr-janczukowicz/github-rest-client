package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateAuthorizationPayloadTest {

    @Test
    void constructor_shouldAcceptNull_whenScopesNull() {
        final GitHubCreateAuthorizationPayload value = GitHubCreateAuthorizationPayloadBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.createAuthorizationPayload())
                .scopes(null)
                .build();

        assertThat(value.scopes()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenScopesGiven() {
        final List<String> source = new ArrayList<>(List.of("first"));
        final GitHubCreateAuthorizationPayload value = GitHubCreateAuthorizationPayloadBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.createAuthorizationPayload())
                .scopes(source)
                .build();

        source.add("second");

        assertThat(value.scopes()).containsExactly("first");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubCreateAuthorizationPayload value = OauthAuthorizationsCatalogFixtures.createAuthorizationPayload();

        assertThat(value.toString()).contains("clientSecret=<redacted>").doesNotContain("clientSecret=client_secret");
    }
}

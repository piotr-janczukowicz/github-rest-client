package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAuthorizationPayloadTest {

    @Test
    void constructor_shouldAcceptNull_whenScopesNull() {
        final GitHubUpdateAuthorizationPayload value = GitHubUpdateAuthorizationPayloadBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.updateAuthorizationPayload())
                .scopes(null)
                .build();

        assertThat(value.scopes()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenScopesGiven() {
        final List<String> source = new ArrayList<>(List.of("first"));
        final GitHubUpdateAuthorizationPayload value = GitHubUpdateAuthorizationPayloadBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.updateAuthorizationPayload())
                .scopes(source)
                .build();

        source.add("second");

        assertThat(value.scopes()).containsExactly("first");
    }

    @Test
    void constructor_shouldAcceptNull_whenAddScopesNull() {
        final GitHubUpdateAuthorizationPayload value = GitHubUpdateAuthorizationPayloadBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.updateAuthorizationPayload())
                .addScopes(null)
                .build();

        assertThat(value.addScopes()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenAddScopesGiven() {
        final List<String> source = new ArrayList<>(List.of("first"));
        final GitHubUpdateAuthorizationPayload value = GitHubUpdateAuthorizationPayloadBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.updateAuthorizationPayload())
                .addScopes(source)
                .build();

        source.add("second");

        assertThat(value.addScopes()).containsExactly("first");
    }

    @Test
    void constructor_shouldAcceptNull_whenRemoveScopesNull() {
        final GitHubUpdateAuthorizationPayload value = GitHubUpdateAuthorizationPayloadBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.updateAuthorizationPayload())
                .removeScopes(null)
                .build();

        assertThat(value.removeScopes()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenRemoveScopesGiven() {
        final List<String> source = new ArrayList<>(List.of("first"));
        final GitHubUpdateAuthorizationPayload value = GitHubUpdateAuthorizationPayloadBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.updateAuthorizationPayload())
                .removeScopes(source)
                .build();

        source.add("second");

        assertThat(value.removeScopes()).containsExactly("first");
    }
}

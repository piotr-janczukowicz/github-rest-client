package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScopeTokenPayloadTest {

    @Test
    void constructor_shouldThrow_whenAccessTokenNull() {
        final GitHubScopeTokenPayloadBuilder builder = GitHubScopeTokenPayloadBuilder.builder(
                        AppsCatalogFixtures.scopeTokenPayload())
                .accessToken(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("accessToken");
    }

    @Test
    void constructor_shouldKeepNullRepositories_whenNull() {
        final GitHubScopeTokenPayloadBuilder builder = GitHubScopeTokenPayloadBuilder.builder(
                        AppsCatalogFixtures.scopeTokenPayload())
                .repositories(null);

        final GitHubScopeTokenPayload value = builder.build();

        assertThat(value.repositories()).isNull();
    }

    @Test
    void constructor_shouldKeepNullRepositoryIds_whenNull() {
        final GitHubScopeTokenPayloadBuilder builder = GitHubScopeTokenPayloadBuilder.builder(
                        AppsCatalogFixtures.scopeTokenPayload())
                .repositoryIds(null);

        final GitHubScopeTokenPayload payload = builder.build();

        assertThat(payload.repositoryIds()).isNull();
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubScopeTokenPayload value = AppsCatalogFixtures.scopeTokenPayload();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("accessToken=<redacted>");
            softly.assertThat(text).doesNotContain(value.accessToken());
        });
    }
}

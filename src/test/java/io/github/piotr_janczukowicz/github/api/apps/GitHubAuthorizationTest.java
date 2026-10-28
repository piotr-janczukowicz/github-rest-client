package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAuthorizationTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubAuthorizationBuilder builder = GitHubAuthorizationBuilder.builder(
                        AppsCatalogFixtures.authorization())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenTokenNull() {
        final GitHubAuthorizationBuilder builder = GitHubAuthorizationBuilder.builder(
                        AppsCatalogFixtures.authorization())
                .token(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("token");
    }

    @Test
    void constructor_shouldThrow_whenAppNull() {
        final GitHubAuthorizationBuilder builder = GitHubAuthorizationBuilder.builder(
                        AppsCatalogFixtures.authorization())
                .app(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("app");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubAuthorizationBuilder builder = GitHubAuthorizationBuilder.builder(
                        AppsCatalogFixtures.authorization())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubAuthorizationBuilder builder = GitHubAuthorizationBuilder.builder(
                        AppsCatalogFixtures.authorization())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldKeepNullScopes_whenNull() {
        final GitHubAuthorizationBuilder builder = GitHubAuthorizationBuilder.builder(
                        AppsCatalogFixtures.authorization())
                .scopes(null);

        final GitHubAuthorization value = builder.build();

        assertThat(value.scopes()).isNull();
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubAuthorization value = AppsCatalogFixtures.authorization();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("token=<redacted>");
            softly.assertThat(text).doesNotContain(value.token());
        });
    }
}

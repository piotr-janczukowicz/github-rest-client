package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubApplicationGrantTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubApplicationGrantBuilder builder = GitHubApplicationGrantBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.applicationGrant())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenAppNull() {
        final GitHubApplicationGrantBuilder builder = GitHubApplicationGrantBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.applicationGrant())
                .app(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("app");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubApplicationGrantBuilder builder = GitHubApplicationGrantBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.applicationGrant())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubApplicationGrantBuilder builder = GitHubApplicationGrantBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.applicationGrant())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenScopesNull() {
        final GitHubApplicationGrantBuilder builder = GitHubApplicationGrantBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.applicationGrant())
                .scopes(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("scopes");
    }
}

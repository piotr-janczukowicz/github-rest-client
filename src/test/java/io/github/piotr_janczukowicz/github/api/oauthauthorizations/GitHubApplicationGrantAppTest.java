package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubApplicationGrantAppTest {

    @Test
    void constructor_shouldThrow_whenClientIdNull() {
        final GitHubApplicationGrantAppBuilder builder = GitHubApplicationGrantAppBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.applicationGrantApp())
                .clientId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("clientId");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubApplicationGrantAppBuilder builder = GitHubApplicationGrantAppBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.applicationGrantApp())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubApplicationGrantAppBuilder builder = GitHubApplicationGrantAppBuilder.builder(
                        OauthAuthorizationsCatalogFixtures.applicationGrantApp())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}

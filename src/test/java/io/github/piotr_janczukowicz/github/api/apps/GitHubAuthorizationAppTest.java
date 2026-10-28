package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAuthorizationAppTest {

    @Test
    void constructor_shouldThrow_whenClientIdNull() {
        final GitHubAuthorizationAppBuilder builder = GitHubAuthorizationAppBuilder.builder(
                        AppsCatalogFixtures.authorizationApp())
                .clientId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("clientId");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubAuthorizationAppBuilder builder = GitHubAuthorizationAppBuilder.builder(
                        AppsCatalogFixtures.authorizationApp())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubAuthorizationAppBuilder builder = GitHubAuthorizationAppBuilder.builder(
                        AppsCatalogFixtures.authorizationApp())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}

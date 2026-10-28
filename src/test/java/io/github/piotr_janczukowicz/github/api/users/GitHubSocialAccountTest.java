package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSocialAccountTest {

    @Test
    void constructor_shouldThrow_whenProviderNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSocialAccount(null, "https://x"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("provider");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSocialAccount("twitter", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}

package io.github.piotr_janczukowicz.github.api.deploykeys;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeployKeyTest {

    @Test
    void constructor_shouldThrow_whenKeyNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeployKey(
                1,
                null,
                "https://example",
                "title",
                true,
                Instant.parse("2014-12-10T15:53:42Z"),
                true,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("key");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeployKey(
                1,
                "ssh-rsa AAA...",
                null,
                "title",
                true,
                Instant.parse("2014-12-10T15:53:42Z"),
                true,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeployKey(
                1,
                "ssh-rsa AAA...",
                "https://example",
                null,
                true,
                Instant.parse("2014-12-10T15:53:42Z"),
                true,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeployKey(
                1, "ssh-rsa AAA...", "https://example", "title", true, null, true, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }
}

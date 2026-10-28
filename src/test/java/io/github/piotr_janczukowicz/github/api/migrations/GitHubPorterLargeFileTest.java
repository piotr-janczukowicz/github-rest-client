package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPorterLargeFileTest {

    @Test
    void constructor_shouldThrow_whenRefNameNull() {
        final Throwable thrown = catchThrowable(() -> largeFile(null, "foo/bar/1", "oid"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("refName");
    }

    @Test
    void constructor_shouldThrow_whenPathNull() {
        final Throwable thrown = catchThrowable(() -> largeFile("refs/heads/master", null, "oid"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void constructor_shouldThrow_whenOidNull() {
        final Throwable thrown = catchThrowable(() -> largeFile("refs/heads/master", "foo/bar/1", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("oid");
    }

    private static GitHubPorterLargeFile largeFile(final String refName, final String path, final String oid) {
        return new GitHubPorterLargeFile(refName, path, oid, 1);
    }
}

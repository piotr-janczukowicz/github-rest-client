package io.github.piotr_janczukowicz.github.api.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPageBuildTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Instant now = Instant.parse("2014-08-10T10:10:10Z");

        final Throwable thrown = catchThrowable(
                () -> new GitHubPageBuild(null, "built", new GitHubPageBuildError(null), null, "sha", 1L, now, now));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}

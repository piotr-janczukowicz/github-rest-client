package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubContentTrafficTest {

    @Test
    void constructor_shouldStoreFields_whenCalled() {
        final GitHubContentTraffic traffic = new GitHubContentTraffic("/github/hubot", "hubot", 3542L, 2225L);

        assertSoftly(softly -> {
            softly.assertThat(traffic.path()).isEqualTo("/github/hubot");
            softly.assertThat(traffic.title()).isEqualTo("hubot");
        });
    }

    @Test
    void constructor_shouldThrow_whenPathNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubContentTraffic(null, "hubot", 1L, 2L));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubContentTraffic("/github/hubot", null, 1L, 2L));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }
}

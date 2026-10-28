package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckRunTest {

    @Test
    void constructor_shouldThrow_whenHeadShaNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCheckRunBuilder.builder(ChecksCatalogFixtures.checkRun())
                .headSha(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("headSha");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCheckRunBuilder.builder(ChecksCatalogFixtures.checkRun())
                .nodeId(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCheckRunBuilder.builder(ChecksCatalogFixtures.checkRun())
                .url(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenStatusNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCheckRunBuilder.builder(ChecksCatalogFixtures.checkRun())
                .status(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("status");
    }

    @Test
    void constructor_shouldThrow_whenOutputNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCheckRunBuilder.builder(ChecksCatalogFixtures.checkRun())
                .output(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("output");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCheckRunBuilder.builder(ChecksCatalogFixtures.checkRun())
                .name(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPullRequestsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCheckRunBuilder.builder(ChecksCatalogFixtures.checkRun())
                .pullRequests(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pullRequests");
    }
}

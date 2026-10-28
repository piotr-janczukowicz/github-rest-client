package io.github.piotr_janczukowicz.github.api.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPagesDeploymentTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final String url = "https://example.com";

        final Throwable thrown = catchThrowable(() -> new GitHubPagesDeployment(null, url, url, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }
}

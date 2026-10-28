package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateIssueFieldOptionTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateIssueFieldOption(null, null, null, 0));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenColorNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateIssueFieldOption("High", null, null, 0));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("color");
    }
}

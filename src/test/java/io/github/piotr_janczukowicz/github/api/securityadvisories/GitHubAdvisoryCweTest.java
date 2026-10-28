package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAdvisoryCweTest {

    @Test
    void constructor_shouldThrow_whenCweIdNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAdvisoryCwe(null, "Cross-site Scripting (XSS)"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cweId");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAdvisoryCwe("CWE-79", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}

package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetIssueFieldValueTest {

    @Test
    void constructor_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSetIssueFieldValue(1, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}

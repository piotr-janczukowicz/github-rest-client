package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateMilestonePayloadTest {

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateMilestonePayloadBuilder.builder().title(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }
}

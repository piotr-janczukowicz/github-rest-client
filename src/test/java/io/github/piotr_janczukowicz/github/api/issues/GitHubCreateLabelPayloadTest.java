package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateLabelPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateLabelPayloadBuilder.builder().name(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}

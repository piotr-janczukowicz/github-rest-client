package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateReviewPayloadTest {

    @Test
    void constructor_shouldThrow_whenBodyBlank() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateReviewPayloadBuilder.builder().body(" ").build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("body");
    }
}

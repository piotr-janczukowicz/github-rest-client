package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEmailsPayloadTest {

    @Test
    void constructor_shouldThrow_whenEmailsEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateEmailsPayload(List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("emails");
    }

    @Test
    void constructor_shouldThrow_whenEmailsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateEmailsPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("emails");
    }
}

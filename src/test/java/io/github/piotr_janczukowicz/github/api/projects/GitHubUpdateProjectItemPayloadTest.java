package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateProjectItemPayloadTest {

    @Test
    void constructor_shouldThrow_whenFieldsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateProjectItemPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("fields");
    }

    @Test
    void constructor_shouldCopyFields_whenEmpty() {
        final GitHubUpdateProjectItemPayload payload = new GitHubUpdateProjectItemPayload(List.of());

        assertThat(payload.fields()).isEmpty();
    }
}

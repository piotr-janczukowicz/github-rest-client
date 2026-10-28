package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReplaceAllTopicsPayloadTest {

    @Test
    void constructor_shouldThrow_whenNamesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubReplaceAllTopicsPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("names");
    }

    @Test
    void constructor_shouldCopyNames_whenProvided() {
        final GitHubReplaceAllTopicsPayload payload = new GitHubReplaceAllTopicsPayload(List.of("api"));

        assertThat(payload.names()).containsExactly("api");
    }
}

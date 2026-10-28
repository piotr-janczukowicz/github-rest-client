package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssueSuggestionTest {

    @Test
    void constructor_shouldThrow_whenActionNull() {
        final Throwable thrown = catchThrowable(() -> GitHubIssueSuggestionBuilder.builder()
                .id(1L)
                .issueId(2L)
                .state(GitHubIssueSuggestionState.PENDING)
                .createdAt(Instant.parse("2020-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2020-01-01T00:00:00Z"))
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("action");
    }
}

package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddLabelsPayloadTest {

    @Test
    void constructor_shouldThrow_whenLabelsEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubAddLabelsPayload(List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }

    @Test
    void constructor_shouldCopyLabels_whenPresent() {
        final List<GitHubAddLabelsLabel> labels = new ArrayList<>(List.of(GitHubAddLabelsLabel.ofName("bug")));

        final GitHubAddLabelsPayload payload = new GitHubAddLabelsPayload(labels);
        labels.add(GitHubAddLabelsLabel.ofName("enhancement"));

        assertThat(payload.labels()).containsExactly(GitHubAddLabelsLabel.ofName("bug"));
    }
}

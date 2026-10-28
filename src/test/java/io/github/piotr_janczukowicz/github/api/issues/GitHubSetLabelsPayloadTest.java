package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetLabelsPayloadTest {

    @Test
    void constructor_shouldThrow_whenLabelsEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubSetLabelsPayload(List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }

    @Test
    void constructor_shouldCopyLabels_whenPresent() {
        final List<GitHubSetLabelsLabel> labels = new ArrayList<>(List.of(GitHubSetLabelsLabel.ofName("bug")));

        final GitHubSetLabelsPayload payload = new GitHubSetLabelsPayload(labels);
        labels.add(GitHubSetLabelsLabel.ofName("enhancement"));

        assertThat(payload.labels()).containsExactly(GitHubSetLabelsLabel.ofName("bug"));
    }
}

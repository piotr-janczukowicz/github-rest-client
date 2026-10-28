package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGenerateRunnerJitconfigForOrgPayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final int runnerGroupId = 1;
        final List<String> labels = List.of("labels-sample");
        final String workFolder = "workFolder-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubGenerateRunnerJitconfigForOrgPayload(null, runnerGroupId, labels, workFolder));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenLabelsTooLarge() {
        final String name = "name-sample";
        final int runnerGroupId = 1;
        final String workFolder = "workFolder-sample";
        final List<String> labelsOversized = Collections.nCopies(101, "x-sample");

        final Throwable thrown = catchThrowable(
                () -> new GitHubGenerateRunnerJitconfigForOrgPayload(name, runnerGroupId, labelsOversized, workFolder));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}

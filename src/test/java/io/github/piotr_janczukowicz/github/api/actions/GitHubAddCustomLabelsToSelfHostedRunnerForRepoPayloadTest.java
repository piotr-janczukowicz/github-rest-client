package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddCustomLabelsToSelfHostedRunnerForRepoPayloadTest {

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenLabelsTooLarge() {
        final List<String> labelsOversized = Collections.nCopies(101, "x-sample");

        final Throwable thrown =
                catchThrowable(() -> new GitHubAddCustomLabelsToSelfHostedRunnerForRepoPayload(labelsOversized));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}

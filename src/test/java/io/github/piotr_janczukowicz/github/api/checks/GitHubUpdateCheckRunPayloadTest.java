package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCheckRunPayloadTest {

    @Test
    void constructor_shouldThrow_whenStatusCompletedWithoutConclusion() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunPayloadBuilder.builder(ChecksCatalogFixtures.updateCheckRunPayload())
                        .status(GitHubUpdateCheckRunStatus.COMPLETED)
                        .conclusion(null)
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusion");
    }

    @Test
    void constructor_shouldAccept_whenStatusInProgressWithoutConclusion() {
        final GitHubUpdateCheckRunPayload payload = GitHubUpdateCheckRunPayloadBuilder.builder(
                        ChecksCatalogFixtures.updateCheckRunPayload())
                .status(GitHubUpdateCheckRunStatus.IN_PROGRESS)
                .conclusion(null)
                .build();

        assertThat(payload.conclusion()).isNull();
    }

    @Test
    void constructor_shouldAccept_whenStatusCompletedWithConclusion() {
        final GitHubUpdateCheckRunPayload payload = GitHubUpdateCheckRunPayloadBuilder.builder(
                        ChecksCatalogFixtures.updateCheckRunPayload())
                .status(GitHubUpdateCheckRunStatus.COMPLETED)
                .conclusion(GitHubUpdateCheckRunConclusion.SUCCESS)
                .build();

        assertThat(payload.conclusion()).isEqualTo(GitHubUpdateCheckRunConclusion.SUCCESS);
    }

    @Test
    void constructor_shouldThrow_whenActionsExceedMax() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunPayloadBuilder.builder(ChecksCatalogFixtures.updateCheckRunPayload())
                        .actions(IntStream.range(0, 4)
                                .mapToObj(index -> ChecksCatalogFixtures.updateCheckRunAction())
                                .toList())
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("actions");
    }

    @Test
    void constructor_shouldLeaveActionsNull_whenNull() {
        final GitHubUpdateCheckRunPayload payload = GitHubUpdateCheckRunPayloadBuilder.builder(
                        ChecksCatalogFixtures.updateCheckRunPayload())
                .actions(null)
                .build();

        assertThat(payload.actions()).isNull();
    }
}

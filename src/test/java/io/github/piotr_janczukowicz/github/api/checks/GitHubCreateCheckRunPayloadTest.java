package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateCheckRunPayloadTest {

    @Test
    void constructor_shouldThrow_whenStatusCompletedWithoutConclusion() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunPayloadBuilder.builder(ChecksCatalogFixtures.createCheckRunPayload())
                        .status(GitHubCreateCheckRunStatus.COMPLETED)
                        .conclusion(null)
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusion");
    }

    @Test
    void constructor_shouldAccept_whenStatusInProgressWithoutConclusion() {
        final GitHubCreateCheckRunPayload payload = GitHubCreateCheckRunPayloadBuilder.builder(
                        ChecksCatalogFixtures.createCheckRunPayload())
                .status(GitHubCreateCheckRunStatus.IN_PROGRESS)
                .conclusion(null)
                .build();

        assertThat(payload.conclusion()).isNull();
    }

    @Test
    void constructor_shouldAccept_whenStatusCompletedWithConclusion() {
        final GitHubCreateCheckRunPayload payload = GitHubCreateCheckRunPayloadBuilder.builder(
                        ChecksCatalogFixtures.createCheckRunPayload())
                .status(GitHubCreateCheckRunStatus.COMPLETED)
                .conclusion(GitHubCreateCheckRunConclusion.SUCCESS)
                .build();

        assertThat(payload.conclusion()).isEqualTo(GitHubCreateCheckRunConclusion.SUCCESS);
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunPayloadBuilder.builder(ChecksCatalogFixtures.createCheckRunPayload())
                        .name(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenHeadShaNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunPayloadBuilder.builder(ChecksCatalogFixtures.createCheckRunPayload())
                        .headSha(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("headSha");
    }

    @Test
    void constructor_shouldThrow_whenActionsExceedMax() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunPayloadBuilder.builder(ChecksCatalogFixtures.createCheckRunPayload())
                        .actions(IntStream.range(0, 4)
                                .mapToObj(index -> ChecksCatalogFixtures.createCheckRunAction())
                                .toList())
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("actions");
    }

    @Test
    void constructor_shouldLeaveActionsNull_whenNull() {
        final GitHubCreateCheckRunPayload payload = GitHubCreateCheckRunPayloadBuilder.builder(
                        ChecksCatalogFixtures.createCheckRunPayload())
                .actions(null)
                .build();

        assertThat(payload.actions()).isNull();
    }

    @Test
    void constructor_shouldCopyActions_whenProvided() {
        final List<GitHubCreateCheckRunAction> actions = List.of(ChecksCatalogFixtures.createCheckRunAction());

        final GitHubCreateCheckRunPayload payload = GitHubCreateCheckRunPayloadBuilder.builder(
                        ChecksCatalogFixtures.createCheckRunPayload())
                .actions(actions)
                .build();

        assertThat(payload.actions()).containsExactlyElementsOf(actions);
    }
}

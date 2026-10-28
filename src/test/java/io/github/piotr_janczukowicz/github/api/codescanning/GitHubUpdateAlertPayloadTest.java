package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAlertPayloadTest {

    @Test
    void constructor_shouldThrow_whenNeitherStateNorAssigneesGiven() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateAlertPayload(null, null, null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("state or assignees");
    }

    @Test
    void constructor_shouldThrow_whenDismissedCommentTooLong() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateAlertPayloadBuilder.builder(CodeScanningCatalogFixtures.updateAlertPayload())
                        .dismissedComment("x".repeat(281))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dismissedComment");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubUpdateAlertPayload built = GitHubUpdateAlertPayloadBuilder.builder(
                        CodeScanningCatalogFixtures.updateAlertPayload())
                .assignees(null)
                .build();

        assertThat(built.assignees()).isNull();
    }
}

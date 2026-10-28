package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCancelCopilotSeatAssignmentForUsersPayloadTest {

    @Test
    void constructor_shouldThrow_whenSelectedUsernamesNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCancelCopilotSeatAssignmentForUsersPayloadBuilder.builder(
                        CopilotCatalogFixtures.cancelCopilotSeatAssignmentForUsersPayload())
                .selectedUsernames(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("selectedUsernames");
    }

    @Test
    void constructor_shouldThrow_whenSelectedUsernamesEmpty() {
        final Throwable thrown = catchThrowable(() -> GitHubCancelCopilotSeatAssignmentForUsersPayloadBuilder.builder(
                        CopilotCatalogFixtures.cancelCopilotSeatAssignmentForUsersPayload())
                .selectedUsernames(List.of())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("selectedUsernames");
    }
}

package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotSeatDetailsTest {

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCopilotSeatDetailsBuilder.builder(CopilotCatalogFixtures.copilotSeatDetails())
                        .createdAt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldLeaveAssigneeNull_whenNull() {
        final GitHubCopilotSeatDetails value = GitHubCopilotSeatDetailsBuilder.builder(
                        CopilotCatalogFixtures.copilotSeatDetails())
                .assignee(null)
                .build();

        assertThat(value.assignee()).isNull();
    }

    @Test
    void constructor_shouldLeaveOrganizationNull_whenNull() {
        final GitHubCopilotSeatDetails value = GitHubCopilotSeatDetailsBuilder.builder(
                        CopilotCatalogFixtures.copilotSeatDetails())
                .organization(null)
                .build();

        assertThat(value.organization()).isNull();
    }

    @Test
    void constructor_shouldLeaveAssigningTeamNull_whenNull() {
        final GitHubCopilotSeatDetails value = GitHubCopilotSeatDetailsBuilder.builder(
                        CopilotCatalogFixtures.copilotSeatDetails())
                .assigningTeam(null)
                .build();

        assertThat(value.assigningTeam()).isNull();
    }

    @Test
    void constructor_shouldLeavePendingCancellationDateNull_whenNull() {
        final GitHubCopilotSeatDetails value = GitHubCopilotSeatDetailsBuilder.builder(
                        CopilotCatalogFixtures.copilotSeatDetails())
                .pendingCancellationDate(null)
                .build();

        assertThat(value.pendingCancellationDate()).isNull();
    }

    @Test
    void constructor_shouldLeaveUpdatedAtNull_whenNull() {
        final GitHubCopilotSeatDetails value = GitHubCopilotSeatDetailsBuilder.builder(
                        CopilotCatalogFixtures.copilotSeatDetails())
                .updatedAt(null)
                .build();

        assertThat(value.updatedAt()).isNull();
    }

    @Test
    void constructor_shouldLeavePlanTypeNull_whenNull() {
        final GitHubCopilotSeatDetails value = GitHubCopilotSeatDetailsBuilder.builder(
                        CopilotCatalogFixtures.copilotSeatDetails())
                .planType(null)
                .build();

        assertThat(value.planType()).isNull();
    }
}

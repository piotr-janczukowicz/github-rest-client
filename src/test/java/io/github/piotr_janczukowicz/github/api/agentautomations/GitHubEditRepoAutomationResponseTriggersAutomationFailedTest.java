package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTriggersAutomationFailedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooLarge() {
        final GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubEditRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubEditRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubEditRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubEditRepoAutomationResponseTriggersAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldAccept_whenReasonsRepeat() {
        final GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubEditRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubEditRepoAutomationResponseTriggersAutomationFailedReason.REPORTED));

        final GitHubEditRepoAutomationResponseTriggersAutomationFailed value = builder.build();

        assertThat(value.reasons()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenReasonsNull() {
        final GitHubEditRepoAutomationResponseTriggersAutomationFailed value =
                GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(null)
                        .build();

        assertThat(value.reasons()).isNull();
    }
}

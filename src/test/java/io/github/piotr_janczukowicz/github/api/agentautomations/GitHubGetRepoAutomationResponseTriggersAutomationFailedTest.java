package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersAutomationFailedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooLarge() {
        final GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubGetRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubGetRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubGetRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubGetRepoAutomationResponseTriggersAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldAccept_whenReasonsRepeat() {
        final GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubGetRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubGetRepoAutomationResponseTriggersAutomationFailedReason.REPORTED));

        final GitHubGetRepoAutomationResponseTriggersAutomationFailed value = builder.build();

        assertThat(value.reasons()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenReasonsNull() {
        final GitHubGetRepoAutomationResponseTriggersAutomationFailed value =
                GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(null)
                        .build();

        assertThat(value.reasons()).isNull();
    }
}

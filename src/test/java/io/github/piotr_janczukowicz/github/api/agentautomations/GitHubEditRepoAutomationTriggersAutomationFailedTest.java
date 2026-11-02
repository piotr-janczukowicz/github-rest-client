package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationTriggersAutomationFailedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubEditRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersAutomationFailed())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubEditRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersAutomationFailed())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubEditRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubEditRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooSmall() {
        final GitHubEditRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersAutomationFailed())
                        .reasons(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooLarge() {
        final GitHubEditRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubEditRepoAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubEditRepoAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubEditRepoAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubEditRepoAutomationTriggersAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsDuplicated() {
        final GitHubEditRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubEditRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubEditRepoAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubEditRepoAutomationTriggersAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldAcceptNull_whenReasonsNull() {
        final GitHubEditRepoAutomationTriggersAutomationFailed value =
                GitHubEditRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersAutomationFailed())
                        .reasons(null)
                        .build();

        assertThat(value.reasons()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenReasonsGiven() {
        final List<GitHubEditRepoAutomationTriggersAutomationFailedReason> source =
                new ArrayList<>(List.of(GitHubEditRepoAutomationTriggersAutomationFailedReason.REPORTED));
        final GitHubEditRepoAutomationTriggersAutomationFailed value =
                GitHubEditRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersAutomationFailed())
                        .reasons(source)
                        .build();

        source.add(GitHubEditRepoAutomationTriggersAutomationFailedReason.REPORTED);

        assertThat(value.reasons()).hasSize(source.size() - 1);
    }
}

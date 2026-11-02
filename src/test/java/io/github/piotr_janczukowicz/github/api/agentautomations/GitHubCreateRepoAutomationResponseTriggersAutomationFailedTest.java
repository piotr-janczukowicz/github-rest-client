package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTriggersAutomationFailedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooLarge() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubCreateRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubCreateRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubCreateRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubCreateRepoAutomationResponseTriggersAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldAccept_whenReasonsRepeat() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubCreateRepoAutomationResponseTriggersAutomationFailedReason.REPORTED,
                                GitHubCreateRepoAutomationResponseTriggersAutomationFailedReason.REPORTED));

        final GitHubCreateRepoAutomationResponseTriggersAutomationFailed value = builder.build();

        assertThat(value.reasons()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenReasonsNull() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationFailed value =
                GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersAutomationFailed())
                        .reasons(null)
                        .build();

        assertThat(value.reasons()).isNull();
    }
}

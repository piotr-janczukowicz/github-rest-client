package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsAutomationTriggersAutomationFailedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersAutomationFailed())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersAutomationFailed())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersAutomationFailed())
                        .reasons(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooLarge() {
        final GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubListRepoAutomationsAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubListRepoAutomationsAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubListRepoAutomationsAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubListRepoAutomationsAutomationTriggersAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldAccept_whenReasonsRepeat() {
        final GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubListRepoAutomationsAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubListRepoAutomationsAutomationTriggersAutomationFailedReason.REPORTED));

        final GitHubListRepoAutomationsAutomationTriggersAutomationFailed value = builder.build();

        assertThat(value.reasons()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenReasonsNull() {
        final GitHubListRepoAutomationsAutomationTriggersAutomationFailed value =
                GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersAutomationFailed())
                        .reasons(null)
                        .build();

        assertThat(value.reasons()).isNull();
    }
}

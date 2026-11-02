package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersAutomationFailedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubCreateRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersAutomationFailed())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubCreateRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersAutomationFailed())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubCreateRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubCreateRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooSmall() {
        final GitHubCreateRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersAutomationFailed())
                        .reasons(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooLarge() {
        final GitHubCreateRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubCreateRepoAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubCreateRepoAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubCreateRepoAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubCreateRepoAutomationTriggersAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsDuplicated() {
        final GitHubCreateRepoAutomationTriggersAutomationFailedBuilder builder =
                GitHubCreateRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubCreateRepoAutomationTriggersAutomationFailedReason.REPORTED,
                                GitHubCreateRepoAutomationTriggersAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldAcceptNull_whenReasonsNull() {
        final GitHubCreateRepoAutomationTriggersAutomationFailed value =
                GitHubCreateRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersAutomationFailed())
                        .reasons(null)
                        .build();

        assertThat(value.reasons()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenReasonsGiven() {
        final List<GitHubCreateRepoAutomationTriggersAutomationFailedReason> source =
                new ArrayList<>(List.of(GitHubCreateRepoAutomationTriggersAutomationFailedReason.REPORTED));
        final GitHubCreateRepoAutomationTriggersAutomationFailed value =
                GitHubCreateRepoAutomationTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersAutomationFailed())
                        .reasons(source)
                        .build();

        source.add(GitHubCreateRepoAutomationTriggersAutomationFailedReason.REPORTED);

        assertThat(value.reasons()).hasSize(source.size() - 1);
    }
}

package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersAutomationFailedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubListAutomationsItemTriggersAutomationFailedBuilder builder =
                GitHubListAutomationsItemTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationFailed())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubListAutomationsItemTriggersAutomationFailedBuilder builder =
                GitHubListAutomationsItemTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationFailed())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubListAutomationsItemTriggersAutomationFailedBuilder builder =
                GitHubListAutomationsItemTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubListAutomationsItemTriggersAutomationFailedBuilder builder =
                GitHubListAutomationsItemTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooSmall() {
        final GitHubListAutomationsItemTriggersAutomationFailedBuilder builder =
                GitHubListAutomationsItemTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationFailed())
                        .reasons(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooLarge() {
        final GitHubListAutomationsItemTriggersAutomationFailedBuilder builder =
                GitHubListAutomationsItemTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubListAutomationsItemTriggersAutomationFailedReason.REPORTED,
                                GitHubListAutomationsItemTriggersAutomationFailedReason.REPORTED,
                                GitHubListAutomationsItemTriggersAutomationFailedReason.REPORTED,
                                GitHubListAutomationsItemTriggersAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldAccept_whenReasonsRepeat() {
        final GitHubListAutomationsItemTriggersAutomationFailedBuilder builder =
                GitHubListAutomationsItemTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubListAutomationsItemTriggersAutomationFailedReason.REPORTED,
                                GitHubListAutomationsItemTriggersAutomationFailedReason.REPORTED));

        final GitHubListAutomationsItemTriggersAutomationFailed value = builder.build();

        assertThat(value.reasons()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenReasonsNull() {
        final GitHubListAutomationsItemTriggersAutomationFailed value =
                GitHubListAutomationsItemTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationFailed())
                        .reasons(null)
                        .build();

        assertThat(value.reasons()).isNull();
    }
}

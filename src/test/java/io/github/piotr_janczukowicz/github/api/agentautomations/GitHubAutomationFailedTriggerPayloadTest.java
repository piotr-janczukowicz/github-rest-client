package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationFailedTriggerPayloadTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubAutomationFailedTriggerPayloadBuilder builder = GitHubAutomationFailedTriggerPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTriggerPayload())
                .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubAutomationFailedTriggerPayloadBuilder builder = GitHubAutomationFailedTriggerPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTriggerPayload())
                .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubAutomationFailedTriggerPayloadBuilder builder = GitHubAutomationFailedTriggerPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTriggerPayload())
                .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubAutomationFailedTriggerPayloadBuilder builder = GitHubAutomationFailedTriggerPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTriggerPayload())
                .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooSmall() {
        final GitHubAutomationFailedTriggerPayloadBuilder builder = GitHubAutomationFailedTriggerPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTriggerPayload())
                .reasons(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooLarge() {
        final GitHubAutomationFailedTriggerPayloadBuilder builder = GitHubAutomationFailedTriggerPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTriggerPayload())
                .reasons(List.of(
                        GitHubAutomationFailedReasonInput.REPORTED,
                        GitHubAutomationFailedReasonInput.REPORTED,
                        GitHubAutomationFailedReasonInput.REPORTED,
                        GitHubAutomationFailedReasonInput.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsDuplicated() {
        final GitHubAutomationFailedTriggerPayloadBuilder builder = GitHubAutomationFailedTriggerPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTriggerPayload())
                .reasons(List.of(
                        GitHubAutomationFailedReasonInput.REPORTED, GitHubAutomationFailedReasonInput.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldAcceptNull_whenReasonsNull() {
        final GitHubAutomationFailedTriggerPayload value = GitHubAutomationFailedTriggerPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTriggerPayload())
                .reasons(null)
                .build();

        assertThat(value.reasons()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenReasonsGiven() {
        final List<GitHubAutomationFailedReasonInput> source =
                new ArrayList<>(List.of(GitHubAutomationFailedReasonInput.REPORTED));
        final GitHubAutomationFailedTriggerPayload value = GitHubAutomationFailedTriggerPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTriggerPayload())
                .reasons(source)
                .build();

        source.add(GitHubAutomationFailedReasonInput.REPORTED);

        assertThat(value.reasons()).hasSize(source.size() - 1);
    }
}

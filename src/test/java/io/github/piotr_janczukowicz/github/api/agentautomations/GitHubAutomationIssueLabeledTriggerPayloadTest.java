package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationIssueLabeledTriggerPayloadTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationIssueLabeledTriggerPayloadBuilder builder =
                GitHubAutomationIssueLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIssueLabeledTriggerPayload())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubAutomationIssueLabeledTriggerPayloadBuilder builder =
                GitHubAutomationIssueLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIssueLabeledTriggerPayload())
                        .labels(List.of("labels", "labels"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }

    @Test
    void constructor_shouldAcceptNull_whenLabelsNull() {
        final GitHubAutomationIssueLabeledTriggerPayload value =
                GitHubAutomationIssueLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIssueLabeledTriggerPayload())
                        .labels(null)
                        .build();

        assertThat(value.labels()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenLabelsGiven() {
        final List<String> source = new ArrayList<>(List.of("labels"));
        final GitHubAutomationIssueLabeledTriggerPayload value =
                GitHubAutomationIssueLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIssueLabeledTriggerPayload())
                        .labels(source)
                        .build();

        source.add("labels");

        assertThat(value.labels()).hasSize(source.size() - 1);
    }
}

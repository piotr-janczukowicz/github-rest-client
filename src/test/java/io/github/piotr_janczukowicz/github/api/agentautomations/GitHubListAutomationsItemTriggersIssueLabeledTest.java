package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersIssueLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationsItemTriggersIssueLabeledBuilder builder =
                GitHubListAutomationsItemTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersIssueLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubListAutomationsItemTriggersIssueLabeledBuilder builder =
                GitHubListAutomationsItemTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersIssueLabeled())
                        .labels(null);

        final GitHubListAutomationsItemTriggersIssueLabeled sut = builder.build();

        assertThat(sut.labels()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubListAutomationsItemTriggersIssueLabeledBuilder builder =
                GitHubListAutomationsItemTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersIssueLabeled())
                        .labels(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }
}

package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersPullRequestLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationsItemTriggersPullRequestLabeledBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubListAutomationsItemTriggersPullRequestLabeledBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestLabeled())
                        .paths(null);

        final GitHubListAutomationsItemTriggersPullRequestLabeled sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListAutomationsItemTriggersPullRequestLabeledBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestLabeled())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListAutomationsItemTriggersPullRequestLabeledBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestLabeled())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubListAutomationsItemTriggersPullRequestLabeledBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestLabeled())
                        .labels(null);

        final GitHubListAutomationsItemTriggersPullRequestLabeled sut = builder.build();

        assertThat(sut.labels()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubListAutomationsItemTriggersPullRequestLabeledBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestLabeled())
                        .labels(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }
}

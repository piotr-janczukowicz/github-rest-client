package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationTriggersPullRequestConvertedToDraftTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestConvertedToDraft())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubEditRepoAutomationTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestConvertedToDraft())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubEditRepoAutomationTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestConvertedToDraft())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubEditRepoAutomationTriggersPullRequestConvertedToDraft value =
                GitHubEditRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestConvertedToDraft())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenPathsGiven() {
        final List<String> source = new ArrayList<>(List.of("paths"));
        final GitHubEditRepoAutomationTriggersPullRequestConvertedToDraft value =
                GitHubEditRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestConvertedToDraft())
                        .paths(source)
                        .build();

        source.add("paths");

        assertThat(value.paths()).hasSize(source.size() - 1);
    }
}

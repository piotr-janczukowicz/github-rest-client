package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraftTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestConvertedToDraft())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestConvertedToDraft())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraftBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestConvertedToDraft())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraft value =
                GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestConvertedToDraft())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenPathsGiven() {
        final List<String> source = new ArrayList<>(List.of("paths"));
        final GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraft value =
                GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestConvertedToDraft())
                        .paths(source)
                        .build();

        source.add("paths");

        assertThat(value.paths()).hasSize(source.size() - 1);
    }
}

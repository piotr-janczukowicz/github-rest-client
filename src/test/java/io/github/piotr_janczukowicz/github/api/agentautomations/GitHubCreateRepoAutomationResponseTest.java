package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubCreateRepoAutomationResponseBuilder builder = GitHubCreateRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationResponse())
                .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreateRepoAutomationResponseBuilder builder = GitHubCreateRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationResponse())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPromptNull() {
        final GitHubCreateRepoAutomationResponseBuilder builder = GitHubCreateRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationResponse())
                .prompt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("prompt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubCreateRepoAutomationResponseBuilder builder = GitHubCreateRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationResponse())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubCreateRepoAutomationResponseBuilder builder = GitHubCreateRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationResponse())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedByNull() {
        final GitHubCreateRepoAutomationResponseBuilder builder = GitHubCreateRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationResponse())
                .createdBy(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdBy");
    }
}

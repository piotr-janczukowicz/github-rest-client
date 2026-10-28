package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsAutomationTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListRepoAutomationsAutomationBuilder builder = GitHubListRepoAutomationsAutomationBuilder.builder(
                        AgentAutomationsCatalogFixtures.listRepoAutomationsAutomation())
                .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubListRepoAutomationsAutomationBuilder builder = GitHubListRepoAutomationsAutomationBuilder.builder(
                        AgentAutomationsCatalogFixtures.listRepoAutomationsAutomation())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubListRepoAutomationsAutomationBuilder builder = GitHubListRepoAutomationsAutomationBuilder.builder(
                        AgentAutomationsCatalogFixtures.listRepoAutomationsAutomation())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubListRepoAutomationsAutomationBuilder builder = GitHubListRepoAutomationsAutomationBuilder.builder(
                        AgentAutomationsCatalogFixtures.listRepoAutomationsAutomation())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedByNull() {
        final GitHubListRepoAutomationsAutomationBuilder builder = GitHubListRepoAutomationsAutomationBuilder.builder(
                        AgentAutomationsCatalogFixtures.listRepoAutomationsAutomation())
                .createdBy(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdBy");
    }
}

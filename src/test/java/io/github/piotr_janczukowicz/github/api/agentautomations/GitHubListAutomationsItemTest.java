package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAutomationsItemBuilder builder = GitHubListAutomationsItemBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationsItem())
                .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryNull() {
        final GitHubListAutomationsItemBuilder builder = GitHubListAutomationsItemBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationsItem())
                .repository(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repository");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubListAutomationsItemBuilder builder = GitHubListAutomationsItemBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationsItem())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubListAutomationsItemBuilder builder = GitHubListAutomationsItemBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationsItem())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubListAutomationsItemBuilder builder = GitHubListAutomationsItemBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationsItem())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedByNull() {
        final GitHubListAutomationsItemBuilder builder = GitHubListAutomationsItemBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationsItem())
                .createdBy(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdBy");
    }
}

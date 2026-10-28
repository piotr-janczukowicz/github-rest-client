package io.github.piotr_janczukowicz.github.api.agents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsVariableTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubActionsVariableBuilder builder = GitHubActionsVariableBuilder.builder(
                        AgentsCatalogFixtures.actionsVariable())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenValueNull() {
        final GitHubActionsVariableBuilder builder = GitHubActionsVariableBuilder.builder(
                        AgentsCatalogFixtures.actionsVariable())
                .value(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubActionsVariableBuilder builder = GitHubActionsVariableBuilder.builder(
                        AgentsCatalogFixtures.actionsVariable())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubActionsVariableBuilder builder = GitHubActionsVariableBuilder.builder(
                        AgentsCatalogFixtures.actionsVariable())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }
}

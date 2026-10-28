package io.github.piotr_janczukowicz.github.api.agents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoVariablePayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreateRepoVariablePayloadBuilder builder = GitHubCreateRepoVariablePayloadBuilder.builder(
                        AgentsCatalogFixtures.createRepoVariablePayload())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenValueNull() {
        final GitHubCreateRepoVariablePayloadBuilder builder = GitHubCreateRepoVariablePayloadBuilder.builder(
                        AgentsCatalogFixtures.createRepoVariablePayload())
                .value(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}

package io.github.piotr_janczukowicz.github.api.agents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsPublicKeyTest {

    @Test
    void constructor_shouldThrow_whenKeyIdNull() {
        final GitHubActionsPublicKeyBuilder builder = GitHubActionsPublicKeyBuilder.builder(
                        AgentsCatalogFixtures.actionsPublicKey())
                .keyId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("keyId");
    }

    @Test
    void constructor_shouldThrow_whenKeyNull() {
        final GitHubActionsPublicKeyBuilder builder = GitHubActionsPublicKeyBuilder.builder(
                        AgentsCatalogFixtures.actionsPublicKey())
                .key(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("key");
    }
}

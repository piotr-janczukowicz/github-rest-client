package io.github.piotr_janczukowicz.github.api.agents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgActionsSecretsPageTest {

    @Test
    void constructor_shouldThrow_whenSecretsNull() {
        final GitHubOrgActionsSecretsPageBuilder builder = GitHubOrgActionsSecretsPageBuilder.builder(
                        AgentsCatalogFixtures.orgActionsSecretsPage())
                .secrets(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("secrets");
    }
}

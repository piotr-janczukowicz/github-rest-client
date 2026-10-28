package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotSecretsPageTest {

    @Test
    void constructor_shouldThrow_whenSecretsNull() {
        final GitHubDependabotSecretsPageBuilder builder = GitHubDependabotSecretsPageBuilder.builder(
                        DependabotCatalogFixtures.dependabotSecretsPage())
                .secrets(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("secrets");
    }
}

package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRuleMaxFilePathLengthParametersTest {

    @Test
    void constructor_shouldThrow_whenMaxFilePathLengthBelowMinimum() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleMaxFilePathLengthParameters(0));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("maxFilePathLength");
    }

    @Test
    void constructor_shouldThrow_whenMaxFilePathLengthAboveMaximum() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleMaxFilePathLengthParameters(32_768));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("maxFilePathLength");
    }

    @Test
    void constructor_shouldAcceptValue_whenAtDumpBounds() {
        assertThat(new GitHubRepositoryRuleMaxFilePathLengthParameters(1).maxFilePathLength())
                .isEqualTo(1);
        assertThat(RepositoriesCatalogFixtures.maxFilePathLengthParameters().maxFilePathLength())
                .isEqualTo(255);
        assertThat(new GitHubRepositoryRuleMaxFilePathLengthParameters(32_767).maxFilePathLength())
                .isEqualTo(32_767);
    }
}

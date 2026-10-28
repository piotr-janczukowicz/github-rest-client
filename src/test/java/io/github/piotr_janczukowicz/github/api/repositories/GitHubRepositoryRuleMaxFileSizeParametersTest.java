package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRuleMaxFileSizeParametersTest {

    @Test
    void constructor_shouldThrow_whenMaxFileSizeBelowMinimum() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleMaxFileSizeParameters(0));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("maxFileSize");
    }

    @Test
    void constructor_shouldThrow_whenMaxFileSizeAboveMaximum() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleMaxFileSizeParameters(101));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("maxFileSize");
    }

    @Test
    void constructor_shouldAcceptValue_whenAtDumpBounds() {
        assertThat(new GitHubRepositoryRuleMaxFileSizeParameters(1).maxFileSize())
                .isEqualTo(1);
        assertThat(RepositoriesCatalogFixtures.maxFileSizeParameters().maxFileSize())
                .isEqualTo(100);
    }
}

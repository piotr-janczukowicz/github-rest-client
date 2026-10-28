package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRuleCodeCoverageTest {

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleCodeCoverage(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldLeaveParametersNull_whenNull() {
        final GitHubRepositoryRuleCodeCoverage rule = GitHubRepositoryRuleCodeCoverageBuilder.builder(
                        RepositoriesCatalogFixtures.repositoryRuleCodeCoverage())
                .parameters(null)
                .build();

        assertThat(rule.parameters()).isNull();
    }
}

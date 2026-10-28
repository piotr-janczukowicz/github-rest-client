package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRuleSuiteCheckTest {

    @Test
    void constructor_shouldStoreContext_whenBuilt() {
        final GitHubRuleSuiteCheck check = RepositoriesCatalogFixtures.ruleSuiteCheck();

        assertThat(check.context()).isEqualTo("ci/build");
    }
}

package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRuleSuiteCheckAppTest {

    @Test
    void constructor_shouldStoreSlug_whenBuilt() {
        final GitHubRuleSuiteCheckApp app = new GitHubRuleSuiteCheckApp(15_368L, "github-actions", "GitHub Actions");

        assertThat(app.slug()).isEqualTo("github-actions");
    }
}

package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRuleSuitePullRequestUserTest {

    @Test
    void constructor_shouldStoreLogin_whenBuilt() {
        final GitHubRuleSuitePullRequestUser user = new GitHubRuleSuitePullRequestUser(1L, "octocat", "User");

        assertThat(user.login()).isEqualTo("octocat");
    }
}

package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRuleSuiteReviewUserTest {

    @Test
    void constructor_shouldStoreLogin_whenBuilt() {
        final GitHubRuleSuiteReviewUser user = new GitHubRuleSuiteReviewUser(2L, "hubot", "Bot");

        assertThat(user.login()).isEqualTo("hubot");
    }
}

package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRuleSuiteTest {

    @Test
    void constructor_shouldLeaveRuleEvaluationsNull_whenRuleEvaluationsNull() {
        final GitHubRuleSuite suite =
                new GitHubRuleSuite(null, null, null, null, null, null, null, null, null, null, null, null);

        assertThat(suite.ruleEvaluations()).isNull();
    }
}

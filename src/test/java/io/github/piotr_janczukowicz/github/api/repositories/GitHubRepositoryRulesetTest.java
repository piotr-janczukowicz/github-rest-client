package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRulesetTest {

    @Test
    void constructor_shouldLeaveBypassActorsAndRulesNull_whenBypassActorsAndRulesNull() {
        final GitHubRepositoryRuleset ruleset = new GitHubRepositoryRuleset(
                1L,
                "main",
                null,
                null,
                "owner/repo",
                GitHubRepositoryRuleEnforcement.ACTIVE,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        assertSoftly(softly -> {
            softly.assertThat(ruleset.bypassActors()).isNull();
            softly.assertThat(ruleset.rules()).isNull();
        });
    }
}

package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateRepoRulesetPayloadTest {

    @Test
    void constructor_shouldCopyLists_whenBypassActorsAndRulesPresent() {
        final List<GitHubRepositoryRulesetBypassActor> bypass =
                new ArrayList<>(List.of(RepositoriesCatalogFixtures.rulesetBypassActor()));
        final List<GitHubRepositoryRule> rules =
                new ArrayList<>(List.of(new GitHubRepositoryRuleCreation(GitHubRepositoryRuleCreationType.CREATION)));

        final GitHubUpdateRepoRulesetPayload payload =
                new GitHubUpdateRepoRulesetPayload("ruleset", null, null, bypass, null, rules);
        bypass.clear();
        rules.clear();

        assertSoftly(softly -> {
            softly.assertThat(payload.bypassActors()).hasSize(1);
            softly.assertThat(payload.rules()).hasSize(1);
            softly.assertThat(payload.name()).isEqualTo("ruleset");
        });
    }

    @Test
    void builder_shouldAcceptTarget_whenSet() {
        final GitHubUpdateRepoRulesetPayload payload = GitHubUpdateRepoRulesetPayloadBuilder.builder()
                .target(GitHubRepositoryRulesetPayloadTarget.PUSH)
                .build();

        assertThat(payload.target()).isEqualTo(GitHubRepositoryRulesetPayloadTarget.PUSH);
    }

    @Test
    void constructor_shouldAcceptName_whenLongerThan256Characters() {
        final String longName = "a".repeat(257);

        final GitHubUpdateRepoRulesetPayload payload =
                new GitHubUpdateRepoRulesetPayload(longName, null, null, null, null, null);

        assertThat(payload.name()).isEqualTo(longName);
    }
}

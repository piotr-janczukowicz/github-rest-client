package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubOrgRule;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleCreation;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleCreationType;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetBypassActor;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetTarget;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrgRulesetPayloadTest {

    @Test
    void constructor_shouldThrow_whenConditionsAreUnknown() {
        final GitHubOrgRulesetConditions unknown = new GitHubOrgRulesetConditions.Unknown(Map.of());

        final Throwable thrown =
                catchThrowable(() -> new GitHubUpdateOrgRulesetPayload(null, null, null, null, unknown, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conditions");
    }

    @Test
    void builder_shouldAcceptTarget_whenSet() {
        final GitHubUpdateOrgRulesetPayload payload = GitHubUpdateOrgRulesetPayloadBuilder.builder()
                .target(GitHubRepositoryRulesetTarget.TAG)
                .build();

        assertThat(payload.target()).isEqualTo(GitHubRepositoryRulesetTarget.TAG);
    }

    @Test
    void constructor_shouldCopyLists_whenBypassActorsAndRulesPresent() {
        final List<GitHubRepositoryRulesetBypassActor> bypass =
                new ArrayList<>(List.of(RepositoriesCatalogFixtures.rulesetBypassActor()));
        final List<GitHubOrgRule> rules =
                new ArrayList<>(List.of(new GitHubRepositoryRuleCreation(GitHubRepositoryRuleCreationType.CREATION)));

        final GitHubUpdateOrgRulesetPayload payload =
                new GitHubUpdateOrgRulesetPayload(null, null, null, bypass, null, rules);
        bypass.clear();
        rules.clear();

        assertSoftly(softly -> {
            softly.assertThat(payload.bypassActors()).hasSize(1);
            softly.assertThat(payload.rules()).hasSize(1);
        });
    }

    @Test
    void constructor_shouldKeepNullLists_whenOmitted() {
        final GitHubUpdateOrgRulesetPayload payload =
                new GitHubUpdateOrgRulesetPayload(null, null, null, null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(payload.bypassActors()).isNull();
            softly.assertThat(payload.rules()).isNull();
        });
    }
}

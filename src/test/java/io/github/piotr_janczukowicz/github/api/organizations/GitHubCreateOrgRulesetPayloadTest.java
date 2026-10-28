package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubOrgRule;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleCreation;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleCreationType;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetBypassActor;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetTarget;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrgRulesetPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateOrgRulesetPayload(
                null,
                GitHubRepositoryRulesetTarget.BRANCH,
                GitHubRepositoryRuleEnforcement.ACTIVE,
                null,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenEnforcementNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateOrgRulesetPayload(
                "ruleset", GitHubRepositoryRulesetTarget.BRANCH, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enforcement");
    }

    @Test
    void constructor_shouldThrow_whenConditionsAreUnknown() {
        final GitHubOrgRulesetConditions unknown = new GitHubOrgRulesetConditions.Unknown(Map.of());

        final Throwable thrown = catchThrowable(() -> new GitHubCreateOrgRulesetPayload(
                "ruleset",
                GitHubRepositoryRulesetTarget.BRANCH,
                GitHubRepositoryRuleEnforcement.ACTIVE,
                null,
                unknown,
                null,
                null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conditions");
    }

    @Test
    void builder_shouldAcceptTarget_whenSet() {
        final GitHubCreateOrgRulesetPayload payload = GitHubCreateOrgRulesetPayloadBuilder.builder()
                .name("ruleset")
                .target(GitHubRepositoryRulesetTarget.REPOSITORY)
                .enforcement(GitHubRepositoryRuleEnforcement.ACTIVE)
                .build();

        assertThat(payload.target()).isEqualTo(GitHubRepositoryRulesetTarget.REPOSITORY);
    }

    @Test
    void constructor_shouldCopyLists_whenBypassActorsAndRulesPresent() {
        final List<GitHubRepositoryRulesetBypassActor> bypass =
                new ArrayList<>(List.of(RepositoriesCatalogFixtures.rulesetBypassActor()));
        final List<GitHubOrgRule> rules =
                new ArrayList<>(List.of(new GitHubRepositoryRuleCreation(GitHubRepositoryRuleCreationType.CREATION)));

        final GitHubCreateOrgRulesetPayload payload = new GitHubCreateOrgRulesetPayload(
                "ruleset", null, GitHubRepositoryRuleEnforcement.ACTIVE, bypass, null, rules, null);
        bypass.clear();
        rules.clear();

        assertSoftly(softly -> {
            softly.assertThat(payload.bypassActors()).hasSize(1);
            softly.assertThat(payload.rules()).hasSize(1);
        });
    }
}

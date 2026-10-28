package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseRulesetPayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateEnterpriseRulesetPayload value =
                EnterpriseAdminCatalogFixtures.updateEnterpriseRulesetPayload();

        assertThat(value.name()).isEqualTo("sample-ruleset");
        assertThat(value.enforcement()).isEqualTo(GitHubRepositoryRuleEnforcement.ACTIVE);
        assertThat(value.bypassActors()).isNotEmpty();
        assertThat(value.conditions()).isNotNull();
        assertThat(value.rules()).isNotEmpty();
    }

    @Test
    void constructor_shouldLeaveBypassActorsNull_whenNull() {
        final GitHubUpdateEnterpriseRulesetPayload value = GitHubUpdateEnterpriseRulesetPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.updateEnterpriseRulesetPayload())
                .bypassActors(null)
                .build();

        assertThat(value.bypassActors()).isNull();
    }

    @Test
    void constructor_shouldLeaveRulesNull_whenNull() {
        final GitHubUpdateEnterpriseRulesetPayload value = GitHubUpdateEnterpriseRulesetPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.updateEnterpriseRulesetPayload())
                .rules(null)
                .build();

        assertThat(value.rules()).isNull();
    }
}

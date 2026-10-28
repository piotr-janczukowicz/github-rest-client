package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseActionsPolicyPayloadTest {

    @Test
    void constructor_shouldAcceptOmittedComponents_whenAllNull() {
        final GitHubUpdateEnterpriseActionsPolicyPayload value =
                new GitHubUpdateEnterpriseActionsPolicyPayload(null, null, null, null);

        assertThat(value.name()).isNull();
        assertThat(value.rules()).isNull();
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubUpdateEnterpriseActionsPolicyPayload value =
                ActionsCatalogFixtures.updateEnterpriseActionsPolicyPayload();

        assertThat(value.name()).isEqualTo("Require approved actors");
        assertThat(value.enforcement()).isEqualTo(GitHubRepositoryRuleEnforcement.ACTIVE);
        assertThat(value.conditions()).isEqualTo(ActionsCatalogFixtures.actionsPolicyEnterpriseConditions());
        assertThat(value.rules()).containsExactly(ActionsCatalogFixtures.actionsRuleRestrictActionsActors());
    }
}

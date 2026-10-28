package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateEnterpriseActionsPolicyPayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateEnterpriseActionsPolicyPayload(
                null, GitHubRepositoryRuleEnforcement.ACTIVE, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenEnforcementNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateEnterpriseActionsPolicyPayload("name-sample", null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enforcement");
    }

    @Test
    void constructor_shouldKeepRulesNull_whenRulesOmitted() {
        final GitHubCreateEnterpriseActionsPolicyPayload value = new GitHubCreateEnterpriseActionsPolicyPayload(
                "name-sample", GitHubRepositoryRuleEnforcement.ACTIVE, null, null);

        assertThat(value.rules()).isNull();
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubCreateEnterpriseActionsPolicyPayload value =
                ActionsCatalogFixtures.createEnterpriseActionsPolicyPayload();

        assertThat(value.name()).isEqualTo("Require approved actors");
        assertThat(value.enforcement()).isEqualTo(GitHubRepositoryRuleEnforcement.ACTIVE);
        assertThat(value.conditions()).isEqualTo(ActionsCatalogFixtures.actionsPolicyEnterpriseConditions());
        assertThat(value.rules()).containsExactly(ActionsCatalogFixtures.actionsRuleRestrictActionsActors());
    }
}

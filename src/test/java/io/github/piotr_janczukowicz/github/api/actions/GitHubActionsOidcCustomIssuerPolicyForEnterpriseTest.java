package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsOidcCustomIssuerPolicyForEnterpriseTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubActionsOidcCustomIssuerPolicyForEnterprise value =
                ActionsCatalogFixtures.actionsOidcCustomIssuerPolicyForEnterprise();

        assertThat(value.includeEnterpriseSlug()).isTrue();
    }
}

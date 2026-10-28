package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRulesetConditionsOrganizationIdTargetTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenOrganizationIdNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseRulesetConditionsOrganizationIdTargetBuilder.builder(
                                ActionsCatalogFixtures.enterpriseRulesetConditionsOrganizationIdTarget())
                        .organizationId(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organizationId");
    }
}

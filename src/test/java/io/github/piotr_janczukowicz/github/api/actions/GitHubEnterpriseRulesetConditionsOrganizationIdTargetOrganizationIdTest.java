package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationIdTest {

    @Test
    void constructor_shouldLeaveOrganizationIdsNull_whenNull() {
        final GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationId sut =
                GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationIdBuilder.builder(
                                ActionsCatalogFixtures.enterpriseRulesetConditionsOrganizationIdTargetOrganizationId())
                        .organizationIds(null)
                        .build();

        assertThat(sut.organizationIds()).isNull();
    }
}

package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationPropertyTest {

    @Test
    void constructor_shouldLeaveIncludeNull_whenNull() {
        final GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty sut =
                GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationPropertyBuilder.builder(
                                ActionsCatalogFixtures
                                        .enterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty())
                        .include(null)
                        .build();

        assertThat(sut.include()).isNull();
    }

    @Test
    void constructor_shouldLeaveExcludeNull_whenNull() {
        final GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty sut =
                GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationPropertyBuilder.builder(
                                ActionsCatalogFixtures
                                        .enterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty())
                        .exclude(null)
                        .build();

        assertThat(sut.exclude()).isNull();
    }
}

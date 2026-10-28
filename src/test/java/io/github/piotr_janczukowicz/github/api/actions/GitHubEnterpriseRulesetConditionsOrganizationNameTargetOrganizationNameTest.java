package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationNameTest {

    @Test
    void constructor_shouldLeaveIncludeNull_whenNull() {
        final GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationName sut =
                GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationNameBuilder.builder(
                                ActionsCatalogFixtures
                                        .enterpriseRulesetConditionsOrganizationNameTargetOrganizationName())
                        .include(null)
                        .build();

        assertThat(sut.include()).isNull();
    }

    @Test
    void constructor_shouldLeaveExcludeNull_whenNull() {
        final GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationName sut =
                GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationNameBuilder.builder(
                                ActionsCatalogFixtures
                                        .enterpriseRulesetConditionsOrganizationNameTargetOrganizationName())
                        .exclude(null)
                        .build();

        assertThat(sut.exclude()).isNull();
    }
}

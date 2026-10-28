package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRulesetConditionsOrganizationPropertySpecTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseRulesetConditionsOrganizationPropertySpecBuilder.builder(
                                ActionsCatalogFixtures.enterpriseRulesetConditionsOrganizationPropertySpec())
                        .name(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenPropertyValuesNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseRulesetConditionsOrganizationPropertySpecBuilder.builder(
                                ActionsCatalogFixtures.enterpriseRulesetConditionsOrganizationPropertySpec())
                        .propertyValues(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("propertyValues");
    }
}

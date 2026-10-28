package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenOrganizationPropertyNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetBuilder.builder(
                                ActionsCatalogFixtures.enterpriseRulesetConditionsOrganizationPropertyTarget())
                        .organizationProperty(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organizationProperty");
    }
}

package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRulesetConditionsOrganizationNameTargetTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenOrganizationNameNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseRulesetConditionsOrganizationNameTargetBuilder.builder(
                                ActionsCatalogFixtures.enterpriseRulesetConditionsOrganizationNameTarget())
                        .organizationName(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organizationName");
    }
}

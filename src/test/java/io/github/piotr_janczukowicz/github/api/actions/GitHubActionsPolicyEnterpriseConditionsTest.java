package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsPolicyEnterpriseConditionsTest {

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenNoOrganizationTargetSet() {
        final Throwable thrown = catchThrowable(() -> GitHubActionsPolicyEnterpriseConditionsBuilder.builder(
                        ActionsCatalogFixtures.actionsPolicyEnterpriseConditions())
                .organizationName(null)
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("organizationName");
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenMultipleOrganizationTargetsSet() {
        final Throwable thrown = catchThrowable(() -> GitHubActionsPolicyEnterpriseConditionsBuilder.builder(
                        ActionsCatalogFixtures.actionsPolicyEnterpriseConditions())
                .organizationId(ActionsCatalogFixtures.enterpriseRulesetConditionsOrganizationIdTargetOrganizationId())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("organizationName");
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenNoRepositoryTargetSet() {
        final Throwable thrown = catchThrowable(() -> GitHubActionsPolicyEnterpriseConditionsBuilder.builder(
                        ActionsCatalogFixtures.actionsPolicyEnterpriseConditions())
                .repositoryName(null)
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repositoryName");
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenMultipleRepositoryTargetsSet() {
        final Throwable thrown = catchThrowable(() -> GitHubActionsPolicyEnterpriseConditionsBuilder.builder(
                        ActionsCatalogFixtures.actionsPolicyEnterpriseConditions())
                .repositoryProperty(ActionsCatalogFixtures.actionsPolicyRepositoryProperty())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repositoryName");
    }

    @Test
    void constructor_shouldAcceptOrganizationProperty_whenOnlyPropertyTargetSet() {
        final GitHubActionsPolicyEnterpriseConditions sut = GitHubActionsPolicyEnterpriseConditionsBuilder.builder(
                        ActionsCatalogFixtures.actionsPolicyEnterpriseConditions())
                .organizationName(null)
                .organizationProperty(
                        ActionsCatalogFixtures
                                .enterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty())
                .build();

        assertThat(sut).hasAllNullFieldsOrPropertiesExcept("organizationProperty", "repositoryName", "workflowPath");
    }
}

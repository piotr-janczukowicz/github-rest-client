package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRulesetConditionsTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubEnterpriseRulesetConditions value = EnterpriseAdminCatalogFixtures.enterpriseRulesetConditions();

        assertThat(value.organizationName()).isNotNull();
        assertThat(value.organizationId()).isNull();
        assertThat(value.organizationProperty()).isNull();
        assertThat(value.repositoryName()).isNotNull();
        assertThat(value.repositoryProperty()).isNull();
        assertThat(value.refName()).isNotNull();
    }

    @Test
    void constructor_shouldAcceptOrganizationId_whenOnlyOrganizationIdSet() {
        final GitHubEnterpriseRulesetConditions value =
                EnterpriseAdminCatalogFixtures.enterpriseRulesetConditionsOrganizationIdAndRepositoryProperty();

        assertThat(value.organizationId()).isNotNull();
        assertThat(value.repositoryProperty()).isNotNull();
    }

    @Test
    void constructor_shouldAcceptOrganizationProperty_whenOnlyOrganizationPropertySet() {
        final GitHubEnterpriseRulesetConditions value =
                EnterpriseAdminCatalogFixtures.enterpriseRulesetConditionsOrganizationPropertyAndRepositoryName();

        assertThat(value.organizationProperty()).isNotNull();
        assertThat(value.repositoryName()).isNotNull();
    }

    @Test
    void constructor_shouldRejectMissingOrganizationTarget_whenNoneSet() {
        final Throwable thrown = catchThrowable(() -> GitHubEnterpriseRulesetConditionsBuilder.builder()
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldRejectTwoOrganizationTargets_whenNameAndIdSet() {
        final Throwable thrown = catchThrowable(() -> GitHubEnterpriseRulesetConditionsBuilder.builder()
                .organizationName(EnterpriseAdminCatalogFixtures.enterpriseRulesetConditions()
                        .organizationName())
                .organizationId(
                        EnterpriseAdminCatalogFixtures.enterpriseRulesetConditionsOrganizationIdAndRepositoryName()
                                .organizationId())
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldRejectMissingRepositoryTarget_whenNoneSet() {
        final Throwable thrown = catchThrowable(() -> GitHubEnterpriseRulesetConditionsBuilder.builder()
                .organizationName(EnterpriseAdminCatalogFixtures.enterpriseRulesetConditions()
                        .organizationName())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldRejectTwoRepositoryTargets_whenNameAndPropertySet() {
        final Throwable thrown = catchThrowable(() -> GitHubEnterpriseRulesetConditionsBuilder.builder()
                .organizationName(EnterpriseAdminCatalogFixtures.enterpriseRulesetConditions()
                        .organizationName())
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .repositoryProperty(RepositoriesCatalogFixtures.repositoryPropertyTargetProperty())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}

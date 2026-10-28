package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseOrgPropertyValuesTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubEnterpriseOrgPropertyValues value = EnterpriseAdminCatalogFixtures.enterpriseOrgPropertyValues();

        assertThat(GitHubEnterpriseOrgPropertyValuesBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenOrganizationLoginNull() {
        final Throwable thrown = catchThrowable(() -> GitHubEnterpriseOrgPropertyValuesBuilder.builder(
                        EnterpriseAdminCatalogFixtures.enterpriseOrgPropertyValues())
                .organizationLogin(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}

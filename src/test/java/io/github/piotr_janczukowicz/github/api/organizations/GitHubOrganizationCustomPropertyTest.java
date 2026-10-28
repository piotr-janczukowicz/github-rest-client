package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationCustomPropertyTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubOrganizationCustomProperty value = OrganizationsCatalogFixtures.organizationCustomProperty();

        assertThat(GitHubOrganizationCustomPropertyBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenPropertyNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubOrganizationCustomPropertyBuilder.builder(
                        OrganizationsCatalogFixtures.organizationCustomProperty())
                .propertyName(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenValueTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubOrganizationCustomPropertyBuilder.builder(
                        OrganizationsCatalogFixtures.organizationCustomProperty())
                .valueType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void constructor_shouldLeaveAllowedValuesNull_whenNull() {
        final GitHubOrganizationCustomProperty value = GitHubOrganizationCustomPropertyBuilder.builder(
                        OrganizationsCatalogFixtures.organizationCustomProperty())
                .allowedValues(null)
                .build();

        assertThat(value.allowedValues()).isNull();
    }

    @Test
    void constructor_shouldLeaveDefaultValueNull_whenNull() {
        final GitHubOrganizationCustomProperty value = GitHubOrganizationCustomPropertyBuilder.builder(
                        OrganizationsCatalogFixtures.organizationCustomProperty())
                .defaultValue(null)
                .build();

        assertThat(value.defaultValue()).isNull();
    }
}

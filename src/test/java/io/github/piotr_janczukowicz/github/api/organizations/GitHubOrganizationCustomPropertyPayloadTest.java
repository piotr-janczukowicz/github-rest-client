package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationCustomPropertyPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubOrganizationCustomPropertyPayload value =
                OrganizationsCatalogFixtures.organizationCustomPropertyPayload();

        assertThat(GitHubOrganizationCustomPropertyPayloadBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenValueTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubOrganizationCustomPropertyPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.organizationCustomPropertyPayload())
                .valueType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void constructor_shouldLeaveAllowedValuesNull_whenNull() {
        final GitHubOrganizationCustomPropertyPayload value = GitHubOrganizationCustomPropertyPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.organizationCustomPropertyPayload())
                .allowedValues(null)
                .build();

        assertThat(value.allowedValues()).isNull();
    }

    @Test
    void constructor_shouldLeaveDefaultValueNull_whenNull() {
        final GitHubOrganizationCustomPropertyPayload value = GitHubOrganizationCustomPropertyPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.organizationCustomPropertyPayload())
                .defaultValue(null)
                .build();

        assertThat(value.defaultValue()).isNull();
    }
}

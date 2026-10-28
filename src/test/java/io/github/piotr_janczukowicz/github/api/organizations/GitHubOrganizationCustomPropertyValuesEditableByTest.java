package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationCustomPropertyValuesEditableByTest {

    @Test
    void fromWire_shouldReturnEnterpriseActors_whenKnownValue() {
        assertThat(GitHubOrganizationCustomPropertyValuesEditableBy.fromWire("enterprise_actors"))
                .isEqualTo(GitHubOrganizationCustomPropertyValuesEditableBy.ENTERPRISE_ACTORS);
    }

    @Test
    void fromWire_shouldReturnEnterpriseAndOrgActors_whenKnownValue() {
        assertThat(GitHubOrganizationCustomPropertyValuesEditableBy.fromWire("enterprise_and_org_actors"))
                .isEqualTo(GitHubOrganizationCustomPropertyValuesEditableBy.ENTERPRISE_AND_ORG_ACTORS);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubOrganizationCustomPropertyValuesEditableBy.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}

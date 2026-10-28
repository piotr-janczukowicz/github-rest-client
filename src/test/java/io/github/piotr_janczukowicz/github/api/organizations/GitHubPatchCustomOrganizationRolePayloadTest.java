package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPatchCustomOrganizationRolePayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubPatchCustomOrganizationRolePayload value =
                OrganizationsCatalogFixtures.patchCustomOrganizationRolePayload();

        assertThat(value.baseRole()).isEqualTo(GitHubCustomOrganizationRoleUpdateBaseRole.NONE);
        assertThat(value.permissions()).containsExactly("read_audit_logs");
        assertThat(value).hasSameHashCodeAs(OrganizationsCatalogFixtures.patchCustomOrganizationRolePayload());
    }

    @Test
    void constructor_shouldKeepPermissionsNull_whenPermissionsNull() {
        final GitHubPatchCustomOrganizationRolePayload value =
                new GitHubPatchCustomOrganizationRolePayload(null, null, null, null);

        assertThat(value.permissions()).isNull();
    }
}

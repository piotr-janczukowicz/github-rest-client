package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCustomRolePayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateCustomRolePayload value = OrganizationsCatalogFixtures.updateCustomRolePayload();
        final GitHubUpdateCustomRolePayload expected = OrganizationsCatalogFixtures.updateCustomRolePayload();

        assertThat(value.name()).isEqualTo(expected.name());
        assertThat(value.description()).isEqualTo(expected.description());
        assertThat(value.baseRole()).isEqualTo(expected.baseRole());
        assertThat(value.permissions()).isEqualTo(expected.permissions());
    }

    @Test
    void constructor_shouldKeepPermissionsNull_whenPermissionsNull() {
        assertThat(new GitHubUpdateCustomRolePayload(null, null, null, null).permissions())
                .isNull();
    }
}

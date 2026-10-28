package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCustomRepoRolePayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateCustomRepoRolePayload value = OrganizationsCatalogFixtures.updateCustomRepoRolePayload();
        final GitHubUpdateCustomRepoRolePayload expected = OrganizationsCatalogFixtures.updateCustomRepoRolePayload();

        assertThat(value.name()).isEqualTo(expected.name());
        assertThat(value.description()).isEqualTo(expected.description());
        assertThat(value.baseRole()).isEqualTo(expected.baseRole());
        assertThat(value.permissions()).isEqualTo(expected.permissions());
    }

    @Test
    void constructor_shouldKeepPermissionsNull_whenPermissionsNull() {
        assertThat(new GitHubUpdateCustomRepoRolePayload(null, null, null, null).permissions())
                .isNull();
    }
}

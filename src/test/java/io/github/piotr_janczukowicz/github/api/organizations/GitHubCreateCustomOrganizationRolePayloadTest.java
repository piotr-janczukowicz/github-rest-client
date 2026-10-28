package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateCustomOrganizationRolePayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubCreateCustomOrganizationRolePayload value =
                OrganizationsCatalogFixtures.createCustomOrganizationRolePayload();

        assertThat(value.name()).isEqualTo("Auditor");
        assertThat(value.permissions()).containsExactly("read_audit_logs");
        assertThat(value.baseRole()).isEqualTo(GitHubCustomOrganizationRoleCreateBaseRole.READ);
        assertThat(value).hasSameHashCodeAs(OrganizationsCatalogFixtures.createCustomOrganizationRolePayload());
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubCreateCustomOrganizationRolePayload(null, null, List.of("read_audit_logs"), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPermissionsNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateCustomOrganizationRolePayload("Auditor", null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("permissions");
    }
}

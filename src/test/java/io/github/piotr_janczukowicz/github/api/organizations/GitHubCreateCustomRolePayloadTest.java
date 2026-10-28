package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateCustomRolePayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubCreateCustomRolePayload value = OrganizationsCatalogFixtures.createCustomRolePayload();
        final GitHubCreateCustomRolePayload expected = OrganizationsCatalogFixtures.createCustomRolePayload();

        assertThat(value.name()).isEqualTo(expected.name());
        assertThat(value.description()).isEqualTo(expected.description());
        assertThat(value.baseRole()).isEqualTo(expected.baseRole());
        assertThat(value.permissions()).isEqualTo(expected.permissions());
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateCustomRolePayload(null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}

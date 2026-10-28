package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimUserRoleTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimUserRole role = ScimCatalogFixtures.scimUserRole();

        assertThat(role.value()).isEqualTo("admin");
        assertThat(role.primary()).isFalse();
        assertThat(role.type()).isEqualTo("member");
        assertThat(role.display()).isEqualTo("Member");
    }
}

package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListCustomRepoRolesResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubListCustomRepoRolesResponse value = OrganizationsCatalogFixtures.listCustomRepoRolesResponse();
        final GitHubListCustomRepoRolesResponse expected = OrganizationsCatalogFixtures.listCustomRepoRolesResponse();

        assertThat(value.totalCount()).isEqualTo(expected.totalCount());
        assertThat(value.customRoles()).isEqualTo(expected.customRoles());
    }

    @Test
    void constructor_shouldKeepCustomRolesNull_whenNull() {
        assertThat(new GitHubListCustomRepoRolesResponse(null, null).customRoles())
                .isNull();
    }
}

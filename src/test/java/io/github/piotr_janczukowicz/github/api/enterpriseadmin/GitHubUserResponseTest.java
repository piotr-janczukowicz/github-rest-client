package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUserResponseTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubUserResponse value = EnterpriseAdminCatalogFixtures.userResponse();

        assertThat(GitHubUserResponseBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveRolesNull_whenNull() {
        final GitHubUserResponse value = GitHubUserResponseBuilder.builder(
                        EnterpriseAdminCatalogFixtures.userResponse())
                .roles(null)
                .build();

        assertThat(value.roles()).isNull();
    }
}

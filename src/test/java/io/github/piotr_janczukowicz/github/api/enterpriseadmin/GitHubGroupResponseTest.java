package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGroupResponseTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubGroupResponse value = EnterpriseAdminCatalogFixtures.groupResponse();

        assertThat(GitHubGroupResponseBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveMembersNull_whenNull() {
        final GitHubGroupResponse value = GitHubGroupResponseBuilder.builder(
                        EnterpriseAdminCatalogFixtures.groupResponse())
                .members(null)
                .build();

        assertThat(value.members()).isNull();
    }
}

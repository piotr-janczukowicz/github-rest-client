package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGroupResponseMemberTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubGroupResponseMember value = EnterpriseAdminCatalogFixtures.groupResponseMember();

        assertThat(GitHubGroupResponseMemberBuilder.builder(value).build()).isEqualTo(value);
    }
}

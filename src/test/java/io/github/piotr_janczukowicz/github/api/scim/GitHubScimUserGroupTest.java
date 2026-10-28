package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimUserGroupTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimUserGroup group = ScimCatalogFixtures.scimUserGroup();

        assertThat(group.value()).isEqualTo("engineering");
        assertThat(group.display()).isEqualTo("Engineering");
    }
}

package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimUserNameTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimUserName name = ScimCatalogFixtures.scimUserName();

        assertThat(name.givenName()).isEqualTo("Monalisa");
        assertThat(name.familyName()).isEqualTo("Octocat");
        assertThat(name.formatted()).isEqualTo("Monalisa Octocat");
    }
}

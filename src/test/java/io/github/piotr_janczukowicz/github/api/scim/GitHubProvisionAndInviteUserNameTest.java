package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProvisionAndInviteUserNameTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubProvisionAndInviteUserName name = ScimCatalogFixtures.provisionAndInviteUserName();

        assertThat(name.givenName()).isEqualTo("Monalisa");
        assertThat(name.familyName()).isEqualTo("Octocat");
        assertThat(name.formatted()).isEqualTo("Monalisa Octocat");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenGivenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubProvisionAndInviteUserName(null, "Octocat", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("givenName");
    }
}

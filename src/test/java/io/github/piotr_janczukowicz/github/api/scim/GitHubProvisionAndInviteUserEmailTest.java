package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProvisionAndInviteUserEmailTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubProvisionAndInviteUserEmail email = ScimCatalogFixtures.provisionAndInviteUserEmail();

        assertThat(email.type()).isEqualTo("work");
        assertThat(email.value()).isEqualTo("mona.octocat@okta.example.com");
        assertThat(email.primary()).isTrue();
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubProvisionAndInviteUserEmail("work", null, true));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}

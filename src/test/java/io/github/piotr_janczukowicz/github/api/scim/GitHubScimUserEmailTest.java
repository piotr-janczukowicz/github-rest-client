package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimUserEmailTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimUserEmail email = ScimCatalogFixtures.scimUserEmail();

        assertThat(email.value()).isEqualTo("mona.octocat@okta.example.com");
        assertThat(email.primary()).isTrue();
        assertThat(email.type()).isEqualTo("work");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubScimUserEmail(null, true, "work"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}

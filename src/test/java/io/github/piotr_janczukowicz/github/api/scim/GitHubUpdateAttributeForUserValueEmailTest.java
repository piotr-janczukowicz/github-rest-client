package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAttributeForUserValueEmailTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubUpdateAttributeForUserValueEmail email = ScimCatalogFixtures.updateAttributeForUserValueEmail();

        assertThat(email.value()).isEqualTo("mona.octocat@okta.example.com");
        assertThat(email.primary()).isTrue();
    }
}

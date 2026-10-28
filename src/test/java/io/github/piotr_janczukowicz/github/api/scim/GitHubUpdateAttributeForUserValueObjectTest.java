package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAttributeForUserValueObjectTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubUpdateAttributeForUserValueObject object = ScimCatalogFixtures.updateAttributeForUserValueObject();

        assertThat(object.active()).isEqualTo(OptionalNull.of(false));
        assertThat(object.userName()).isEqualTo(OptionalNull.of("mona.octocat@okta.example.com"));
        assertThat(object.externalId()).isEqualTo(OptionalNull.of("a7d0f98382"));
        assertThat(object.givenName()).isEqualTo(OptionalNull.of("Monalisa"));
        assertThat(object.familyName()).isEqualTo(OptionalNull.of("Octocat"));
    }
}

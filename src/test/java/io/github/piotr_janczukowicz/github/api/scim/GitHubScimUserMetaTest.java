package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimUserMetaTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimUserMeta meta = ScimCatalogFixtures.scimUserMeta();

        assertThat(meta.resourceType()).isEqualTo("User");
        assertThat(meta.created()).isEqualTo(Instant.parse("2017-03-09T21:11:13Z"));
        assertThat(meta.lastModified()).isEqualTo(Instant.parse("2017-03-09T21:11:13Z"));
    }
}

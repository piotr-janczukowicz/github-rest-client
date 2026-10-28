package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMetaResourceTypeTest {

    @Test
    void fromWire_shouldReturnUser_whenKnownValue() {
        assertThat(GitHubMetaResourceType.fromWire("User")).isEqualTo(GitHubMetaResourceType.USER);
    }

    @Test
    void fromWire_shouldReturnGroup_whenKnownValue() {
        assertThat(GitHubMetaResourceType.fromWire("Group")).isEqualTo(GitHubMetaResourceType.GROUP);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubMetaResourceType decoded = GitHubMetaResourceType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubMetaResourceType.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

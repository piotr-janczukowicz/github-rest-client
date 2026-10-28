package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGroupResponseSchemaUrnTest {

    @Test
    void fromWire_shouldReturnGroup_whenKnownValue() {
        assertThat(GitHubGroupResponseSchemaUrn.fromWire("urn:ietf:params:scim:schemas:core:2.0:Group"))
                .isEqualTo(GitHubGroupResponseSchemaUrn.GROUP);
    }

    @Test
    void fromWire_shouldReturnListResponse_whenKnownValue() {
        assertThat(GitHubGroupResponseSchemaUrn.fromWire("urn:ietf:params:scim:api:messages:2.0:ListResponse"))
                .isEqualTo(GitHubGroupResponseSchemaUrn.LIST_RESPONSE);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubGroupResponseSchemaUrn decoded = GitHubGroupResponseSchemaUrn.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubGroupResponseSchemaUrn.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

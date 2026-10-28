package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAttributeForUserPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubUpdateAttributeForUserPayload payload = ScimCatalogFixtures.updateAttributeForUserPayload();

        assertThat(payload.schemas()).containsExactly("urn:ietf:params:scim:schemas:core:2.0:PatchOp");
        assertThat(payload.operations()).hasSize(1);
    }

    @Test
    void constructor_shouldKeepSchemasAbsent_whenSchemasNull() {
        final GitHubUpdateAttributeForUserPayload payload = new GitHubUpdateAttributeForUserPayload(
                null, List.of(ScimCatalogFixtures.updateAttributeForUserOperation()));

        assertThat(payload.schemas()).isNull();
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenOperationsEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateAttributeForUserPayload(null, List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}

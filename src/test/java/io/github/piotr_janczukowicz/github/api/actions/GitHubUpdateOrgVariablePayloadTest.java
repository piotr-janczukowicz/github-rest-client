package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrgVariablePayloadTest {

    @Test
    void constructor_shouldLeaveSelectedRepositoryIdsNull_whenNull() {
        final GitHubUpdateOrgVariablePayload payload = GitHubUpdateOrgVariablePayloadBuilder.builder(
                        ActionsCatalogFixtures.updateOrgVariablePayload())
                .selectedRepositoryIds(null)
                .build();

        assertThat(payload.selectedRepositoryIds()).isNull();
    }
}

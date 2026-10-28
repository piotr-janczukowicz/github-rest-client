package io.github.piotr_janczukowicz.github.api.agents;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrgVariablePayloadTest {

    @Test
    void constructor_shouldCopySelectedRepositoryIds_whenMutableList() {
        final GitHubUpdateOrgVariablePayload source = AgentsCatalogFixtures.updateOrgVariablePayload();
        final int size = source.selectedRepositoryIds().size();
        final List<Long> mutable = new ArrayList<>(source.selectedRepositoryIds());
        final GitHubUpdateOrgVariablePayload sut = GitHubUpdateOrgVariablePayloadBuilder.builder(source)
                .selectedRepositoryIds(mutable)
                .build();
        mutable.clear();

        assertThat(sut.selectedRepositoryIds()).hasSize(size);
    }

    @Test
    void constructor_shouldLeaveSelectedRepositoryIdsNull_whenNull() {
        final GitHubUpdateOrgVariablePayloadBuilder builder = GitHubUpdateOrgVariablePayloadBuilder.builder(
                        AgentsCatalogFixtures.updateOrgVariablePayload())
                .selectedRepositoryIds(null);

        final GitHubUpdateOrgVariablePayload sut = builder.build();

        assertThat(sut.selectedRepositoryIds()).isNull();
    }
}

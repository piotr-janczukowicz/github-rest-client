package io.github.piotr_janczukowicz.github.api.agents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetSelectedReposForOrgVariablePayloadTest {

    @Test
    void constructor_shouldThrow_whenSelectedRepositoryIdsNull() {
        final GitHubSetSelectedReposForOrgVariablePayloadBuilder builder =
                GitHubSetSelectedReposForOrgVariablePayloadBuilder.builder(
                                AgentsCatalogFixtures.setSelectedReposForOrgVariablePayload())
                        .selectedRepositoryIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("selectedRepositoryIds");
    }

    @Test
    void constructor_shouldCopySelectedRepositoryIds_whenMutableList() {
        final GitHubSetSelectedReposForOrgVariablePayload source =
                AgentsCatalogFixtures.setSelectedReposForOrgVariablePayload();
        final int size = source.selectedRepositoryIds().size();
        final List<Long> mutable = new ArrayList<>(source.selectedRepositoryIds());
        final GitHubSetSelectedReposForOrgVariablePayload sut =
                GitHubSetSelectedReposForOrgVariablePayloadBuilder.builder(source)
                        .selectedRepositoryIds(mutable)
                        .build();
        mutable.clear();

        assertThat(sut.selectedRepositoryIds()).hasSize(size);
    }
}

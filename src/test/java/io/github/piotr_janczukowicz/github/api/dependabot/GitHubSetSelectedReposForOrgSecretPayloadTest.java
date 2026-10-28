package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetSelectedReposForOrgSecretPayloadTest {

    @Test
    void constructor_shouldThrow_whenSelectedRepositoryIdsNull() {
        final GitHubSetSelectedReposForOrgSecretPayloadBuilder builder =
                GitHubSetSelectedReposForOrgSecretPayloadBuilder.builder(
                                DependabotCatalogFixtures.setSelectedReposForOrgSecretPayload())
                        .selectedRepositoryIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("selectedRepositoryIds");
    }

    @Test
    void constructor_shouldCopySelectedRepositoryIds_whenMutableList() {
        final GitHubSetSelectedReposForOrgSecretPayload source =
                DependabotCatalogFixtures.setSelectedReposForOrgSecretPayload();
        final int size = source.selectedRepositoryIds().size();
        final List<Long> mutable = new ArrayList<>(source.selectedRepositoryIds());
        final GitHubSetSelectedReposForOrgSecretPayload sut = GitHubSetSelectedReposForOrgSecretPayloadBuilder.builder(
                        source)
                .selectedRepositoryIds(mutable)
                .build();
        mutable.clear();

        assertThat(sut.selectedRepositoryIds()).hasSize(size);
    }
}

package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateRepositoryAccessPayloadTest {

    @Test
    void constructor_shouldCopyRepositoryIdsToAdd_whenMutableList() {
        final GitHubUpdateRepositoryAccessPayload source = DependabotCatalogFixtures.updateRepositoryAccessPayload();
        final int size = source.repositoryIdsToAdd().size();
        final List<Long> mutable = new ArrayList<>(source.repositoryIdsToAdd());
        final GitHubUpdateRepositoryAccessPayload sut = GitHubUpdateRepositoryAccessPayloadBuilder.builder(source)
                .repositoryIdsToAdd(mutable)
                .build();
        mutable.clear();

        assertThat(sut.repositoryIdsToAdd()).hasSize(size);
    }

    @Test
    void constructor_shouldCopyRepositoryIdsToRemove_whenMutableList() {
        final GitHubUpdateRepositoryAccessPayload source = DependabotCatalogFixtures.updateRepositoryAccessPayload();
        final int size = source.repositoryIdsToRemove().size();
        final List<Long> mutable = new ArrayList<>(source.repositoryIdsToRemove());
        final GitHubUpdateRepositoryAccessPayload sut = GitHubUpdateRepositoryAccessPayloadBuilder.builder(source)
                .repositoryIdsToRemove(mutable)
                .build();
        mutable.clear();

        assertThat(sut.repositoryIdsToRemove()).hasSize(size);
    }

    @Test
    void constructor_shouldLeaveRepositoryIdsToAddNull_whenNull() {
        final GitHubUpdateRepositoryAccessPayloadBuilder builder = GitHubUpdateRepositoryAccessPayloadBuilder.builder(
                        DependabotCatalogFixtures.updateRepositoryAccessPayload())
                .repositoryIdsToAdd(null);

        final GitHubUpdateRepositoryAccessPayload sut = builder.build();

        assertThat(sut.repositoryIdsToAdd()).isNull();
    }

    @Test
    void constructor_shouldLeaveRepositoryIdsToRemoveNull_whenNull() {
        final GitHubUpdateRepositoryAccessPayloadBuilder builder = GitHubUpdateRepositoryAccessPayloadBuilder.builder(
                        DependabotCatalogFixtures.updateRepositoryAccessPayload())
                .repositoryIdsToRemove(null);

        final GitHubUpdateRepositoryAccessPayload sut = builder.build();

        assertThat(sut.repositoryIdsToRemove()).isNull();
    }
}

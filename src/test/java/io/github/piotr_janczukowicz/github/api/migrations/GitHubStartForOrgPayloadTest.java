package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubStartForOrgPayloadTest {

    @Test
    void constructor_shouldThrow_whenRepositoriesNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubStartForOrgPayload(null, null, null, null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }

    @Test
    void constructor_shouldAllowEmptyRepositories_whenListEmpty() {
        final GitHubStartForOrgPayload payload = GitHubStartForOrgPayloadBuilder.builder()
                .repositories(List.of())
                .build();

        assertThat(payload.repositories()).isEmpty();
    }

    @Test
    void constructor_shouldCopyRepositories_whenSourceMutatedAfterConstruction() {
        final List<String> repositories = new ArrayList<>(List.of("Hello-World"));

        final GitHubStartForOrgPayload payload = GitHubStartForOrgPayloadBuilder.builder()
                .repositories(repositories)
                .build();
        repositories.add("other-repo");

        assertThat(payload.repositories()).containsExactly("Hello-World");
    }

    @Test
    void constructor_shouldCopyExclude_whenSourceMutatedAfterConstruction() {
        final List<GitHubMigrationExclude> exclude = new ArrayList<>(List.of(GitHubMigrationExclude.REPOSITORIES));

        final GitHubStartForOrgPayload payload = GitHubStartForOrgPayloadBuilder.builder()
                .repositories(List.of("Hello-World"))
                .exclude(exclude)
                .build();
        exclude.clear();

        assertThat(payload.exclude()).containsExactly(GitHubMigrationExclude.REPOSITORIES);
    }
}

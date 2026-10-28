package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateCommitPayloadTest {

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCommitPayloadBuilder.builder(GitDatabaseCatalogFixtures.createCommitPayload())
                        .message(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }

    @Test
    void constructor_shouldThrow_whenTreeNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCommitPayloadBuilder.builder(GitDatabaseCatalogFixtures.createCommitPayload())
                        .tree(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tree");
    }

    @Test
    void constructor_shouldLeaveParentsNull_whenNull() {
        final GitHubCreateCommitPayload payload = GitHubCreateCommitPayloadBuilder.builder(
                        GitDatabaseCatalogFixtures.createCommitPayload())
                .parents(null)
                .build();

        assertThat(payload.parents()).isNull();
    }
}

package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateForAuthenticatedUserPayloadTest {

    @Test
    void constructor_shouldCopyRecentFolders_whenMutableList() {
        final List<String> mutable = new ArrayList<>(List.of("/workspaces/hello-world"));
        final GitHubUpdateForAuthenticatedUserPayload sut = GitHubUpdateForAuthenticatedUserPayloadBuilder.builder(
                        CodespacesCatalogFixtures.updateForAuthenticatedUserPayload())
                .recentFolders(mutable)
                .build();

        mutable.clear();

        assertThat(sut.recentFolders()).containsExactly("/workspaces/hello-world");
    }

    @Test
    void constructor_shouldLeaveRecentFoldersNull_whenNull() {
        final GitHubUpdateForAuthenticatedUserPayload sut = GitHubUpdateForAuthenticatedUserPayloadBuilder.builder(
                        CodespacesCatalogFixtures.updateForAuthenticatedUserPayload())
                .recentFolders(null)
                .build();

        assertThat(sut.recentFolders()).isNull();
    }
}

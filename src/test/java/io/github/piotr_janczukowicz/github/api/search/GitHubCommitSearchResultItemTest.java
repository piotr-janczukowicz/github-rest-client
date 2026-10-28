package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCommitSearchResultItemTest {

    @Test
    void compactConstructor_shouldCopyEmptyParents_whenParentsEmpty() {
        final GitHubCommitSearchResultItem item = GitHubCommitSearchResultItemBuilder.builder()
                .url("url")
                .sha("sha")
                .htmlUrl("html")
                .commentsUrl("comments")
                .commit(GitHubCommitSearchResultItemCommitBuilder.builder()
                        .author(new GitHubCommitSearchResultItem.CommitAuthor(
                                "name", "email", Instant.parse("2014-02-04T14:38:36-08:00")))
                        .commentCount(0)
                        .message("message")
                        .tree(new GitHubCommitSearchResultItem.CommitTree("tree-sha", "tree-url"))
                        .url("commit-url")
                        .build())
                .parents(List.of())
                .repository(SearchCatalogFixtures.spoonKnifeMinimalRepository())
                .score(1.0d)
                .nodeId("node")
                .build();

        assertThat(item.parents()).isEmpty();
    }
}

package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.issues.IssuesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectItemContent;
import io.github.piotr_janczukowicz.github.api.projects.ProjectsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectItemContentSerializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldWriteDraftIssue_whenContentIsDraft() {
        final GitHubProjectItemContent content =
                ProjectsCatalogFixtures.itemSimpleDraft().content();

        final String json = encode(content);

        assertThat(json).isEqualTo(readExpected("project-item-content-draft-issue.json"));
    }

    @Test
    void encode_shouldWriteIssue_whenContentIsIssue() {
        final GitHubProjectItemContent content = new GitHubProjectItemContent.Issue(IssuesCatalogFixtures.issue());

        final String json = encode(content);

        assertThat(json).isEqualTo(readExpected("project-item-content-issue.json"));
    }

    @Test
    void encode_shouldWritePullRequest_whenContentIsPullRequest() {
        final GitHubProjectItemContent content =
                new GitHubProjectItemContent.PullRequest(RepositoriesCatalogFixtures.pullRequestSimple());

        final String json = encode(content);

        assertThat(json).isEqualTo(readExpected("project-item-content-pull-request.json"));
    }

    @Test
    void encode_shouldWriteProperties_whenContentIsUnknown() {
        final GitHubProjectItemContent content = new GitHubProjectItemContent.Unknown(Map.of("id", 1));

        final String json = encode(content);

        assertThat(json).isEqualTo("{\"id\":1}");
    }

    private static String encode(final Object value) {
        return new String(CODEC.encode(value), StandardCharsets.UTF_8);
    }

    private static String readExpected(final String fileName) {
        return FileUtils.readString(GitHubProjectItemContentSerializerTest.class, fileName);
    }
}

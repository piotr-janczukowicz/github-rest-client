package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

@UnitTest
class GitHubPullRequestTest {

    private static final JsonMapper TREE = JsonMapper.builder().build();

    @Test
    void compactConstructor_shouldLeaveOptionalListsNull_whenOmitted() {
        final JsonNode tree = TREE.readTree(FileUtils.readBytes(GitHubPullRequest.class, "pull-request.json"));
        ((ObjectNode) tree).remove("assignees");
        ((ObjectNode) tree).remove("requested_reviewers");
        ((ObjectNode) tree).remove("requested_teams");

        final GitHubPullRequest pullRequest =
                GitHubJsonCodecs.defaults().decode(TREE.writeValueAsBytes(tree), GitHubPullRequest.class);

        assertSoftly(softly -> {
            softly.assertThat(pullRequest.assignees()).isNull();
            softly.assertThat(pullRequest.requestedReviewers()).isNull();
            softly.assertThat(pullRequest.requestedTeams()).isNull();
        });
    }
}

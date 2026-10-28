package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestMergeAsyncResultDetailsTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnAccepted_whenAllAcceptedFieldsPresent() {
        final String json = """
                {"message": "Merge request enqueued.", "uuid": "630b9d5e-3f2a-4f7e-8b0c-2d5f9a8c1e42",
                "merge_method": "merge", "merge_action": "default",
                "expected_head_sha": "6dcb09b5b57875f334f61aebed695e2e4193db5e", "bypass_rules": true}""";

        final GitHubPullRequestMergeAsyncResultDetails details = decode(json);

        assertThat(details)
                .isEqualTo(PullrequestsCatalogFixtures.pullRequestMergeAsyncResultPending()
                        .details());
    }

    @Test
    void decode_shouldReturnMerged_whenMessageAndShaPresent() {
        final String json = """
                {"message": "Pull request is already merged.", "sha": "6dcb09b5b57875f334f61aebed695e2e4193db5e"}""";

        final GitHubPullRequestMergeAsyncResultDetails details = decode(json);

        assertThat(details).isEqualTo(PullrequestsCatalogFixtures.pullRequestMergeAsyncResultDetails());
    }

    @Test
    void decode_shouldReturnMessage_whenOnlyMessagePresent() {
        final String json = """
                {"message": "Merge failed."}""";

        final GitHubPullRequestMergeAsyncResultDetails details = decode(json);

        assertThat(details).isEqualTo(new GitHubPullRequestMergeAsyncResultDetailsMessage("Merge failed."));
    }

    private static GitHubPullRequestMergeAsyncResultDetails decode(final String json) {
        return CODEC.decode(json.getBytes(StandardCharsets.UTF_8), GitHubPullRequestMergeAsyncResultDetails.class);
    }
}

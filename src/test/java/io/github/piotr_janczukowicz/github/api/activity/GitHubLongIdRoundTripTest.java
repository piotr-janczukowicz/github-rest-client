package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLongIdRoundTripTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldRoundTripInReplyToId_whenIdAboveIntMax() {
        final GitHubPullRequestReviewCommentEventComment comment =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .inReplyToId(3_000_000_000L)
                        .build();

        final GitHubPullRequestReviewCommentEventComment decoded =
                CODEC.decode(CODEC.encode(comment), GitHubPullRequestReviewCommentEventComment.class);

        assertThat(decoded).isEqualTo(comment);
    }
}

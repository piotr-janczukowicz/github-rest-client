package io.github.piotr_janczukowicz.github.api.reactions;

import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;

final class ReactionsCatalogFixtures {

    private ReactionsCatalogFixtures() {}

    static GitHubCreateForCommitCommentPayload createForCommitCommentPayload() {
        return new GitHubCreateForCommitCommentPayload(GitHubReactionContent.HEART);
    }

    static GitHubCreateForReleasePayload createForReleasePayload() {
        return new GitHubCreateForReleasePayload(GitHubReleaseReactionContent.HEART);
    }

    static GitHubReaction reaction() {
        return new GitHubReaction(
                1L,
                "MDg6UmVhY3Rpb24x",
                UsersCatalogFixtures.octocat(),
                GitHubReactionContent.HEART,
                Instant.parse("2016-05-20T20:09:31Z"));
    }
}

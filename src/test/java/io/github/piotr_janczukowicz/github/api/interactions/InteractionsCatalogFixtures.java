package io.github.piotr_janczukowicz.github.api.interactions;

import java.time.Instant;
import java.util.List;

final class InteractionsCatalogFixtures {

    private InteractionsCatalogFixtures() {}

    static GitHubInteractionLimit interactionLimit() {
        return GitHubInteractionLimitBuilder.builder()
                .limit(GitHubInteractionGroup.COLLABORATORS_ONLY)
                .expiry(GitHubInteractionExpiry.ONE_MONTH)
                .build();
    }

    static GitHubInteractionLimitResponse interactionLimitResponse() {
        return GitHubInteractionLimitResponseBuilder.builder()
                .limit(GitHubInteractionGroup.COLLABORATORS_ONLY)
                .origin("organization")
                .expiresAt(Instant.parse("2018-08-17T04:18:39Z"))
                .build();
    }

    static GitHubInteractionLimitPullRequestBypassList interactionLimitPullRequestBypassList() {
        return GitHubInteractionLimitPullRequestBypassListBuilder.builder()
                .users(List.of("octocat", "monalisa"))
                .build();
    }

    static GitHubPullRequestCreationCap pullRequestCreationCap() {
        return GitHubPullRequestCreationCapBuilder.builder()
                .enabled(true)
                .maxOpenPullRequests(1)
                .includeDrafts(true)
                .build();
    }

    static GitHubUpdatePullRequestCreationCapForOrgPayload updatePullRequestCreationCapForOrgPayload() {
        return GitHubUpdatePullRequestCreationCapForOrgPayloadBuilder.builder()
                .enabled(true)
                .maxOpenPullRequests(1)
                .includeDrafts(true)
                .build();
    }
}

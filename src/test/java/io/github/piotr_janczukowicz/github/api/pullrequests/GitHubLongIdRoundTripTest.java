package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLongIdRoundTripTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldRoundTripId_whenIdAboveIntMax() {
        final GitHubPullRequestStackMinimal value = GitHubPullRequestStackMinimalBuilder.builder(
                        PullrequestsCatalogFixtures.pullRequestStackMinimal())
                .id(3_000_000_000L)
                .build();

        final GitHubPullRequestStackMinimal decoded =
                CODEC.decode(CODEC.encode(value), GitHubPullRequestStackMinimal.class);

        assertThat(decoded).isEqualTo(value);
    }
}

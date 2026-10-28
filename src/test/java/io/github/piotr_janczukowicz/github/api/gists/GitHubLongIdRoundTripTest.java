package io.github.piotr_janczukowicz.github.api.gists;

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
        final GitHubGistComment value = GitHubGistCommentBuilder.builder(GistsCatalogFixtures.gistComment())
                .id(3_000_000_000L)
                .build();

        final GitHubGistComment decoded = CODEC.decode(CODEC.encode(value), GitHubGistComment.class);

        assertThat(decoded).isEqualTo(value);
    }
}

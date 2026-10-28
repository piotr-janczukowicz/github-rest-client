package io.github.piotr_janczukowicz.github.api.issues;

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
        final GitHubIssueEventProjectCard value = GitHubIssueEventProjectCardBuilder.builder(
                        IssuesCatalogFixtures.issueEventProjectCard())
                .id(3_000_000_000L)
                .build();

        final GitHubIssueEventProjectCard decoded =
                CODEC.decode(CODEC.encode(value), GitHubIssueEventProjectCard.class);

        assertThat(decoded).isEqualTo(value);
    }

    @Test
    void encode_shouldRoundTripIssueTypeId_whenIdAboveIntMax() {
        final GitHubIssueReference.IssueType value = GitHubIssueReferenceIssueTypeBuilder.builder(
                        IssuesCatalogFixtures.issueReferenceIssueType())
                .id(3_000_000_000L)
                .build();

        final GitHubIssueReference.IssueType decoded =
                CODEC.decode(CODEC.encode(value), GitHubIssueReference.IssueType.class);

        assertThat(decoded).isEqualTo(value);
    }
}

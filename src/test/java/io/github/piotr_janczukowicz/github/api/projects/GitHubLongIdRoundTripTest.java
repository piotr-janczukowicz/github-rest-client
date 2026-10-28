package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLongIdRoundTripTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldRoundTripId_whenIdAboveIntMax() {
        final GitHubProjectView value = GitHubProjectViewBuilder.builder(ProjectsCatalogFixtures.projectView())
                .id(3_000_000_000L)
                .build();

        final GitHubProjectView decoded = CODEC.decode(CODEC.encode(value), GitHubProjectView.class);

        assertThat(decoded).isEqualTo(value);
    }

    @Test
    void encode_shouldRoundTripFieldIds_whenFieldIdsAboveIntMax() {
        final GitHubProjectView value = GitHubProjectViewBuilder.builder(ProjectsCatalogFixtures.projectView())
                .visibleFields(List.of(3_000_000_000L))
                .groupBy(List.of(3_000_000_000L))
                .verticalGroupBy(List.of(3_000_000_000L))
                .build();

        final GitHubProjectView decoded = CODEC.decode(CODEC.encode(value), GitHubProjectView.class);

        assertThat(decoded).isEqualTo(value);
    }

    @Test
    void encode_shouldRoundTripSortFieldId_whenFieldIdAboveIntMax() {
        final GitHubProjectViewSort value =
                new GitHubProjectViewSort(3_000_000_000L, GitHubProjectViewSortDirection.ASC);

        final GitHubProjectViewSort decoded = CODEC.decode(CODEC.encode(value), GitHubProjectViewSort.class);

        assertThat(decoded).isEqualTo(value);
    }

    @Test
    void encode_shouldRoundTripIssueFieldId_whenIssueFieldIdAboveIntMax() {
        final GitHubAddProjectFieldPayload value = GitHubAddProjectFieldPayload.issueField(3_000_000_000L);

        final GitHubAddProjectFieldPayload decoded =
                CODEC.decode(CODEC.encode(value), GitHubAddProjectFieldPayload.class);

        assertThat(decoded).isEqualTo(value);
    }

    @Test
    void encode_shouldWriteFieldIds_whenCreateViewPayloadAboveIntMax() {
        final GitHubCreateProjectViewPayload value = new GitHubCreateProjectViewPayload(
                "Board", GitHubProjectViewLayout.BOARD, null, List.of(3_000_000_000L), null, null, null);

        final Map<String, Object> encoded = CODEC.decode(CODEC.encode(value), JsonTypes.mapOf(Object.class));

        assertThat(encoded).containsEntry("visible_fields", List.of(3_000_000_000L));
    }
}

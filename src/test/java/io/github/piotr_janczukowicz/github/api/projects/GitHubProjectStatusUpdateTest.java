package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectStatusUpdateTest {

    @Test
    void constructor_shouldKeepComponents_whenCalled() {
        final Instant createdAt = Instant.parse("2022-04-28T12:00:00Z");
        final Instant updatedAt = Instant.parse("2022-04-29T12:00:00Z");

        final GitHubProjectStatusUpdate update = new GitHubProjectStatusUpdate(
                9L,
                "PVTSU_node",
                "PVT_project",
                null,
                createdAt,
                updatedAt,
                GitHubProjectStatus.ON_TRACK,
                "2022-04-01",
                "2022-04-30",
                "Shipped");

        assertSoftly(softly -> {
            softly.assertThat(update.id()).isEqualTo(9L);
            softly.assertThat(update.nodeId()).isEqualTo("PVTSU_node");
            softly.assertThat(update.projectNodeId()).isEqualTo("PVT_project");
            softly.assertThat(update.creator()).isNull();
            softly.assertThat(update.createdAt()).isEqualTo(createdAt);
            softly.assertThat(update.updatedAt()).isEqualTo(updatedAt);
            softly.assertThat(update.status()).isEqualTo(GitHubProjectStatus.ON_TRACK);
            softly.assertThat(update.startDate()).isEqualTo("2022-04-01");
            softly.assertThat(update.targetDate()).isEqualTo("2022-04-30");
            softly.assertThat(update.body()).isEqualTo("Shipped");
        });
    }
}

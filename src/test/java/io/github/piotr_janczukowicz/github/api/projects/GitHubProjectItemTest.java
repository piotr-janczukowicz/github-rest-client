package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectItemTest {

    @Test
    void constructor_shouldCopyMaps_whenPresent() {
        final GitHubProjectItem item = new GitHubProjectItem(
                1L,
                null,
                null,
                GitHubProjectItemContentType.ISSUE,
                Map.of("title", "Bug"),
                null,
                Instant.parse("2022-04-28T12:00:00Z"),
                Instant.parse("2022-04-28T12:00:00Z"),
                OptionalNull.ofNull(),
                null,
                List.of(Map.of("id", 123)));

        assertSoftly(softly -> {
            softly.assertThat(item.content()).containsEntry("title", "Bug");
            softly.assertThat(item.fields()).hasSize(1);
        });
    }

    @Test
    void constructor_shouldKeepNullMaps_whenAbsent() {
        final GitHubProjectItem item = new GitHubProjectItem(
                1L,
                null,
                null,
                GitHubProjectItemContentType.ISSUE,
                null,
                null,
                Instant.parse("2022-04-28T12:00:00Z"),
                Instant.parse("2022-04-28T12:00:00Z"),
                OptionalNull.ofNull(),
                null,
                null);

        assertSoftly(softly -> {
            softly.assertThat(item.content()).isNull();
            softly.assertThat(item.fields()).isNull();
        });
    }
}

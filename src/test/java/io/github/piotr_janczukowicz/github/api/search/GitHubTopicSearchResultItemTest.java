package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTopicSearchResultItemTest {

    @Test
    void compactConstructor_shouldLeaveListFieldsNull_whenListFieldsNull() {
        final GitHubTopicSearchResultItem item = GitHubTopicSearchResultItemBuilder.builder()
                .name("ruby")
                .createdAt(Instant.parse("2016-11-28T22:03:59Z"))
                .updatedAt(Instant.parse("2017-10-30T18:16:32Z"))
                .featured(false)
                .curated(false)
                .score(1.0d)
                .build();

        assertSoftly(softly -> {
            softly.assertThat(item.textMatches()).isNull();
            softly.assertThat(item.related()).isNull();
            softly.assertThat(item.aliases()).isNull();
        });
    }
}

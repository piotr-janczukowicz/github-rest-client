package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeSearchResultItemTest {

    @Test
    void compactConstructor_shouldLeaveListFieldsNull_whenListFieldsNull() {
        final GitHubCodeSearchResultItem item = GitHubCodeSearchResultItemBuilder.builder()
                .name("name")
                .path("path")
                .sha("sha")
                .url("url")
                .gitUrl("git")
                .htmlUrl("html")
                .repository(SearchCatalogFixtures.jqueryMinimalRepository())
                .score(1.0d)
                .build();

        assertSoftly(softly -> {
            softly.assertThat(item.lineNumbers()).isNull();
            softly.assertThat(item.textMatches()).isNull();
        });
    }
}

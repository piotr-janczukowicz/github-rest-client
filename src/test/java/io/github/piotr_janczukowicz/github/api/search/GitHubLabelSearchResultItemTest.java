package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLabelSearchResultItemTest {

    @Test
    void compactConstructor_shouldLeaveTextMatchesNull_whenTextMatchesNull() {
        final GitHubLabelSearchResultItem item = GitHubLabelSearchResultItemBuilder.builder()
                .id(1L)
                .nodeId("node")
                .url("url")
                .name("name")
                .color("color")
                .isDefault(false)
                .score(1.0d)
                .build();

        assertThat(item.textMatches()).isNull();
    }
}

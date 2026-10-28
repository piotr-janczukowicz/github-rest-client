package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSearchTextMatchItemTest {

    @Test
    void compactConstructor_shouldLeaveMatchesNull_whenMatchesNull() {
        final GitHubSearchTextMatchItem item =
                new GitHubSearchTextMatchItem("url", "type", "property", "fragment", null);

        assertThat(item.matches()).isNull();
    }
}

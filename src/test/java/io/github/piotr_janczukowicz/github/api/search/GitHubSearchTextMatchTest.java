package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSearchTextMatchTest {

    @Test
    void compactConstructor_shouldLeaveIndicesNull_whenIndicesNull() {
        final GitHubSearchTextMatch match = new GitHubSearchTextMatch("text", null);

        assertThat(match.indices()).isNull();
    }
}

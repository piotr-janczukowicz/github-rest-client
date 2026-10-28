package io.github.piotr_janczukowicz.github.api.meta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetOctocatFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        final GitHubGetOctocatFilter filter = GitHubGetOctocatFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(filter.s()).isNull();
            softly.assertThat(query).isEmpty();
        });
    }

    @Test
    void toQueryParameters_shouldPutS_whenSet() {
        final GitHubGetOctocatFilter filter =
                GitHubGetOctocatFilterBuilder.builder().s("Hello").build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsExactly(Map.entry("s", "Hello"));
    }
}

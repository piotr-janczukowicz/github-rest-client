package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetClonesFilterTest {

    @Test
    void toQueryParameters_shouldIncludePer_whenSet() {
        final GitHubGetClonesFilter filter =
                GitHubGetClonesFilterBuilder.builder().per(GitHubTrafficPer.DAY).build();

        assertThat(filter.toQueryParameters()).containsEntry("per", "day");
    }

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        assertThat(GitHubGetClonesFilter.empty().toQueryParameters()).isEmpty();
    }
}

package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListContributorsFilterTest {

    @Test
    void empty_shouldReturnNullAnon_whenCalled() {
        final GitHubListContributorsFilter filter = GitHubListContributorsFilter.empty();

        assertThat(filter.anon()).isNull();
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenAnonNull() {
        final Map<String, String> query = GitHubListContributorsFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldReturnAnonFlag_whenAnonSet() {
        final Map<String, String> enabled =
                GitHubListContributorsFilterBuilder.builder().anon(true).build().toQueryParameters();
        final Map<String, String> disabled = GitHubListContributorsFilterBuilder.builder()
                .anon(false)
                .build()
                .toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(enabled).containsEntry("anon", "1");
            softly.assertThat(disabled).isEmpty();
        });
    }
}

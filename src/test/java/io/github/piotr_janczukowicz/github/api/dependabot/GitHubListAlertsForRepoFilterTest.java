package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAlertsForRepoFilterTest {

    @Test
    void empty_shouldReturnEmptyQueryParameters_whenCalled() {
        final Map<String, String> query = GitHubListAlertsForRepoFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllSetFields_whenCalled() {
        final GitHubListAlertsForRepoFilter filter = DependabotCatalogFixtures.listAlertsForRepoFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsExactly(
                        entry("classification", "general,malware"),
                        entry("state", "open,fixed"),
                        entry("severity", "high,critical"),
                        entry("ecosystem", "npm,pip"),
                        entry("package", "django,lodash"),
                        entry("manifest", "path/to/requirements.txt"),
                        entry("epss_percentage", ">0.5"),
                        entry("has", "patch"),
                        entry("assignee", "octocat"),
                        entry("scope", "runtime"),
                        entry("relationship", "direct"),
                        entry("sort", "created"),
                        entry("direction", "desc"));
    }

    @Test
    void constructor_shouldCopyHas_whenMutableList() {
        final List<String> mutable = new ArrayList<>(List.of("patch"));
        final GitHubListAlertsForRepoFilter sut =
                GitHubListAlertsForRepoFilterBuilder.builder().has(mutable).build();
        mutable.clear();

        assertThat(sut.has()).containsExactly("patch");
    }
}

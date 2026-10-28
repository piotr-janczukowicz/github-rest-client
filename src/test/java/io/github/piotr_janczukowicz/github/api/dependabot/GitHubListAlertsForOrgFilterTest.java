package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAlertsForOrgFilterTest {

    @Test
    void empty_shouldReturnEmptyQueryParameters_whenCalled() {
        final Map<String, String> query = GitHubListAlertsForOrgFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllSetFields_whenCalled() {
        final GitHubListAlertsForOrgFilter filter = DependabotCatalogFixtures.listAlertsForOrgFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsExactly(
                        entry("classification", "general,malware"),
                        entry("state", "open,fixed"),
                        entry("severity", "high,critical"),
                        entry("ecosystem", "npm,pip"),
                        entry("package", "django,lodash"),
                        entry("epss_percentage", ">0.5"),
                        entry("artifact_registry_url", "https://octo.jfrog.io/artifactory"),
                        entry("artifact_registry", "jfrog-artifactory"),
                        entry("has", "patch,deployment"),
                        entry("assignee", "octocat"),
                        entry("runtime_risk", "internet-exposed,sensitive-data"),
                        entry("scope", "development"),
                        entry("relationship", "transitive"),
                        entry("sort", "updated"),
                        entry("direction", "desc"));
    }

    @Test
    void constructor_shouldCopyHas_whenMutableList() {
        final List<String> mutable = new ArrayList<>(List.of("patch"));
        final GitHubListAlertsForOrgFilter sut =
                GitHubListAlertsForOrgFilterBuilder.builder().has(mutable).build();
        mutable.clear();

        assertThat(sut.has()).containsExactly("patch");
    }
}

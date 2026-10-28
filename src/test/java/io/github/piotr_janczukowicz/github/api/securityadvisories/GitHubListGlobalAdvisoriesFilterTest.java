package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListGlobalAdvisoriesFilterTest {

    @Test
    void empty_shouldReturnAllNulls_whenCalled() {
        final GitHubListGlobalAdvisoriesFilter filter = GitHubListGlobalAdvisoriesFilter.empty();

        assertSoftly(softly -> {
            softly.assertThat(filter.ghsaId()).isNull();
            softly.assertThat(filter.type()).isNull();
            softly.assertThat(filter.cveId()).isNull();
            softly.assertThat(filter.ecosystem()).isNull();
            softly.assertThat(filter.severity()).isNull();
            softly.assertThat(filter.cwes()).isNull();
            softly.assertThat(filter.isWithdrawn()).isNull();
            softly.assertThat(filter.affects()).isNull();
            softly.assertThat(filter.published()).isNull();
            softly.assertThat(filter.updated()).isNull();
            softly.assertThat(filter.modified()).isNull();
            softly.assertThat(filter.epssPercentage()).isNull();
            softly.assertThat(filter.epssPercentile()).isNull();
            softly.assertThat(filter.direction()).isNull();
            softly.assertThat(filter.sort()).isNull();
        });
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenAllNull() {
        final Map<String, String> query =
                GitHubListGlobalAdvisoriesFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldOmitNullsAndJoinLists_whenSomeFieldsSet() {
        final GitHubListGlobalAdvisoriesFilter filter = GitHubListGlobalAdvisoriesFilterBuilder.builder()
                .ghsaId("GHSA-xxxx")
                .type(GitHubGlobalAdvisoryType.REVIEWED)
                .cveId("CVE-2024-0001")
                .ecosystem(GitHubSecurityAdvisoryEcosystem.NPM)
                .severity(GitHubSecurityAdvisorySeverity.HIGH)
                .cwes(List.of("79", "284"))
                .isWithdrawn(true)
                .affects(List.of("pkg1", "pkg2@1.0.0"))
                .published("2024-01-01")
                .updated("2024-02-01")
                .modified("2024-03-01")
                .epssPercentage(">0.5")
                .epssPercentile("<0.9")
                .direction(GitHubDirection.DESC)
                .sort(GitHubGlobalAdvisoriesSort.UPDATED)
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsEntry("ghsa_id", "GHSA-xxxx")
                .containsEntry("type", "reviewed")
                .containsEntry("cve_id", "CVE-2024-0001")
                .containsEntry("ecosystem", "npm")
                .containsEntry("severity", "high")
                .containsEntry("cwes", "79,284")
                .containsEntry("is_withdrawn", "true")
                .containsEntry("affects", "pkg1,pkg2@1.0.0")
                .containsEntry("published", "2024-01-01")
                .containsEntry("updated", "2024-02-01")
                .containsEntry("modified", "2024-03-01")
                .containsEntry("epss_percentage", ">0.5")
                .containsEntry("epss_percentile", "<0.9")
                .containsEntry("direction", "desc")
                .containsEntry("sort", "updated");
    }

    @Test
    void constructor_shouldThrow_whenAffectsExceedsMaxItems() {
        final List<String> affects = Collections.nCopies(1001, "pkg");

        final Throwable thrown = catchThrowable(() -> new GitHubListGlobalAdvisoriesFilter(
                null, null, null, null, null, null, null, affects, null, null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("affects");
    }

    @Test
    void constructor_shouldCopyLists_whenPresent() {
        final List<String> cwes = new ArrayList<>(List.of("79"));
        final List<String> affects = new ArrayList<>(List.of("pkg"));

        final GitHubListGlobalAdvisoriesFilter filter = new GitHubListGlobalAdvisoriesFilter(
                null, null, null, null, null, cwes, null, affects, null, null, null, null, null, null, null);
        cwes.add("284");
        affects.add("other");

        assertSoftly(softly -> {
            softly.assertThat(filter.cwes()).containsExactly("79");
            softly.assertThat(filter.affects()).containsExactly("pkg");
        });
    }
}

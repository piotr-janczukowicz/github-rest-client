package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListOrgDismissalRequestsFilterTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubListOrgDismissalRequestsFilter sut = SecretScanningCatalogFixtures.listOrgDismissalRequestsFilter();

        assertSoftly(softly -> {
            softly.assertThat(sut.repositoryName()).isNotNull();
            softly.assertThat(sut.reviewer()).isNotNull();
            softly.assertThat(sut.requester()).isNotNull();
            softly.assertThat(sut.timePeriod()).isNotNull();
            softly.assertThat(sut.requestStatus()).isNotNull();
        });
    }

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        assertThat(GitHubListOrgDismissalRequestsFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllSetFields_whenCalled() {
        assertThat(SecretScanningCatalogFixtures.listOrgDismissalRequestsFilter()
                        .toQueryParameters())
                .hasSize(5)
                .containsEntry("reviewer", "octocat");
    }
}

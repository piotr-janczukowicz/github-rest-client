package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoDismissalRequestsFilterTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubListRepoDismissalRequestsFilter sut =
                SecretScanningCatalogFixtures.listRepoDismissalRequestsFilter();

        assertSoftly(softly -> {
            softly.assertThat(sut.reviewer()).isNotNull();
            softly.assertThat(sut.requester()).isNotNull();
            softly.assertThat(sut.timePeriod()).isNotNull();
            softly.assertThat(sut.requestStatus()).isNotNull();
        });
    }

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        assertThat(GitHubListRepoDismissalRequestsFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllSetFields_whenCalled() {
        assertThat(SecretScanningCatalogFixtures.listRepoDismissalRequestsFilter()
                        .toQueryParameters())
                .hasSize(4)
                .containsEntry("reviewer", "octocat");
    }
}

package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoBypassRequestsFilterTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubListRepoBypassRequestsFilter sut = SecretScanningCatalogFixtures.listRepoBypassRequestsFilter();

        assertSoftly(softly -> {
            softly.assertThat(sut.reviewer()).isNotNull();
            softly.assertThat(sut.requester()).isNotNull();
            softly.assertThat(sut.timePeriod()).isNotNull();
            softly.assertThat(sut.requestStatus()).isNotNull();
        });
    }

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        assertThat(GitHubListRepoBypassRequestsFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllSetFields_whenCalled() {
        assertThat(SecretScanningCatalogFixtures.listRepoBypassRequestsFilter().toQueryParameters())
                .hasSize(4)
                .containsEntry("reviewer", "octocat");
    }
}

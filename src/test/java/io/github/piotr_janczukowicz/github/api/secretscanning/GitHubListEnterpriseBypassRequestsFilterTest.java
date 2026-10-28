package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListEnterpriseBypassRequestsFilterTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubListEnterpriseBypassRequestsFilter sut =
                SecretScanningCatalogFixtures.listEnterpriseBypassRequestsFilter();

        assertSoftly(softly -> {
            softly.assertThat(sut.organizationName()).isNotNull();
            softly.assertThat(sut.reviewer()).isNotNull();
            softly.assertThat(sut.requester()).isNotNull();
            softly.assertThat(sut.timePeriod()).isNotNull();
            softly.assertThat(sut.requestStatus()).isNotNull();
        });
    }

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        assertThat(GitHubListEnterpriseBypassRequestsFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllSetFields_whenCalled() {
        assertThat(SecretScanningCatalogFixtures.listEnterpriseBypassRequestsFilter()
                        .toQueryParameters())
                .hasSize(5)
                .containsEntry("reviewer", "octocat");
    }
}

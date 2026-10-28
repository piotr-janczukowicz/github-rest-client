package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationsOutsideCollaboratorsFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubOrganizationsOutsideCollaboratorsFilter filter =
                GitHubOrganizationsOutsideCollaboratorsFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeFilter_whenSet() {
        final GitHubOrganizationsOutsideCollaboratorsFilter filter =
                GitHubOrganizationsOutsideCollaboratorsFilterBuilder.builder()
                        .filter(GitHubOrganizationOutsideCollaboratorsKind.TWO_FA_DISABLED)
                        .build();

        assertThat(filter.toQueryParameters()).containsEntry("filter", "2fa_disabled");
    }
}

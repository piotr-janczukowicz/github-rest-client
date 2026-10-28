package io.github.piotr_janczukowicz.github.api.collaborators;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListCollaboratorsFilterTest {

    @Test
    void toQueryParameters_shouldIncludeAffiliationAndPermission_whenBothSet() {
        final GitHubListCollaboratorsFilter filter = GitHubListCollaboratorsFilterBuilder.builder()
                .affiliation(GitHubCollaboratorAffiliation.OUTSIDE)
                .permission(GitHubCollaboratorPermission.ADMIN)
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("affiliation", "outside")
                .containsEntry("permission", "admin");
    }

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        assertThat(GitHubListCollaboratorsFilter.empty().toQueryParameters()).isEmpty();
    }
}

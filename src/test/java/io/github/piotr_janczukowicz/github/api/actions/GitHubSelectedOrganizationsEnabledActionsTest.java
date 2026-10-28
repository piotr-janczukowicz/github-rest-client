package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSelectedOrganizationsEnabledActionsTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenOrganizationsNull() {
        final int totalCount = 1;

        final Throwable thrown = catchThrowable(() -> new GitHubSelectedOrganizationsEnabledActions(totalCount, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organizations");
    }

    @Test
    void accessors_shouldReturnValues_whenConstructed() {
        final List<GitHubOrganizationSimple> organizations = List.of(OrganizationsCatalogFixtures.organizationSimple());

        final GitHubSelectedOrganizationsEnabledActions actions =
                new GitHubSelectedOrganizationsEnabledActions(1, organizations);

        assertSoftly(softly -> {
            softly.assertThat(actions.totalCount()).isEqualTo(1);
            softly.assertThat(actions.organizations()).containsExactlyElementsOf(organizations);
        });
    }
}

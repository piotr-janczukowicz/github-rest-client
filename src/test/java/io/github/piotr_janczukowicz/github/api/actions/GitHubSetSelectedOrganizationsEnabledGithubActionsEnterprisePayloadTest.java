package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetSelectedOrganizationsEnabledGithubActionsEnterprisePayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenSelectedOrganizationIdsNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubSetSelectedOrganizationsEnabledGithubActionsEnterprisePayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("selectedOrganizationIds");
    }

    @Test
    void accessors_shouldReturnValues_whenConstructed() {
        final GitHubSetSelectedOrganizationsEnabledGithubActionsEnterprisePayload payload =
                new GitHubSetSelectedOrganizationsEnabledGithubActionsEnterprisePayload(List.of(1L));

        assertThat(payload.selectedOrganizationIds()).containsExactly(1L);
    }
}

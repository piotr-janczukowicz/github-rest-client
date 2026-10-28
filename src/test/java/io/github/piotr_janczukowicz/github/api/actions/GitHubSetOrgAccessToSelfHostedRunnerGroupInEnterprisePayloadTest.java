package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetOrgAccessToSelfHostedRunnerGroupInEnterprisePayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenSelectedOrganizationIdsNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubSetOrgAccessToSelfHostedRunnerGroupInEnterprisePayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("selectedOrganizationIds");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubSetOrgAccessToSelfHostedRunnerGroupInEnterprisePayload value =
                ActionsCatalogFixtures.setOrgAccessToSelfHostedRunnerGroupInEnterprisePayload();

        assertThat(value.selectedOrganizationIds()).containsExactly(1L);
    }
}

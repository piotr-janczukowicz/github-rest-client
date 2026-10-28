package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSelfHostedRunnersPermissionsForEnterpriseTest {

    @Test
    void accessor_shouldReturnFlag_whenConstructed() {
        final GitHubSelfHostedRunnersPermissionsForEnterprise permissions =
                new GitHubSelfHostedRunnersPermissionsForEnterprise(true);

        assertThat(permissions.disableSelfHostedRunnersForAllOrgs()).isTrue();
    }
}

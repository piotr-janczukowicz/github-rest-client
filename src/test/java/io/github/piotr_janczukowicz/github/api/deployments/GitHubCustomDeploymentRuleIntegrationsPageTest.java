package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomDeploymentRuleIntegrationsPageTest {

    @Test
    void constructor_shouldLeaveIntegrationsNull_whenNull() {
        final GitHubCustomDeploymentRuleIntegrationsPage page = new GitHubCustomDeploymentRuleIntegrationsPage(0, null);

        assertThat(page.availableCustomDeploymentRuleIntegrations()).isNull();
    }
}

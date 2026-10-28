package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeploymentProtectionRulesPageTest {

    @Test
    void constructor_shouldLeaveRulesNull_whenNull() {
        final GitHubDeploymentProtectionRulesPage page = new GitHubDeploymentProtectionRulesPage(0, null);

        assertThat(page.customDeploymentProtectionRules()).isNull();
    }
}

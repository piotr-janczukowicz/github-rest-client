package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubVisualStudioSubscriptionAssignmentTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubVisualStudioSubscriptionAssignment value =
                EnterpriseAdminCatalogFixtures.visualStudioSubscriptionAssignment();

        assertThat(value.visualStudioSubscriptionEmail()).isEqualTo("sample-visual_studio_subscription_email");
        assertThat(value.subscriptionId()).isEqualTo("sample-subscription_id");
        assertThat(value.username()).isEqualTo("sample-username");
        assertThat(value.manualMatch()).isTrue();
    }
}

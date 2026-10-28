package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRemoveOrganizationsFromEnterpriseCodingAgentPolicyPayloadTest {

    @Test
    void constructor_shouldLeaveOrganizationsNull_whenNull() {
        final GitHubRemoveOrganizationsFromEnterpriseCodingAgentPolicyPayload value =
                GitHubRemoveOrganizationsFromEnterpriseCodingAgentPolicyPayloadBuilder.builder(
                                CopilotCatalogFixtures.removeOrganizationsFromEnterpriseCodingAgentPolicyPayload())
                        .organizations(null)
                        .build();

        assertThat(value.organizations()).isNull();
    }

    @Test
    void constructor_shouldLeaveCustomPropertiesNull_whenNull() {
        final GitHubRemoveOrganizationsFromEnterpriseCodingAgentPolicyPayload value =
                GitHubRemoveOrganizationsFromEnterpriseCodingAgentPolicyPayloadBuilder.builder(
                                CopilotCatalogFixtures.removeOrganizationsFromEnterpriseCodingAgentPolicyPayload())
                        .customProperties(null)
                        .build();

        assertThat(value.customProperties()).isNull();
    }
}

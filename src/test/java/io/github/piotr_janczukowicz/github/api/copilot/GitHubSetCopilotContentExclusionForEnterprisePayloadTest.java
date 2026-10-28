package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetCopilotContentExclusionForEnterprisePayloadTest {

    @Test
    void fromJson_shouldWrapRules_whenCalled() {
        final Map<String, List<GitHubCopilotEnterpriseContentExclusionRule>> rules =
                Map.of("octo-repo", List.of(CopilotCatalogFixtures.copilotEnterpriseContentExclusionRule()));

        final GitHubSetCopilotContentExclusionForEnterprisePayload payload =
                GitHubSetCopilotContentExclusionForEnterprisePayload.fromJson(rules);

        assertThat(payload.rules()).isEqualTo(rules);
    }

    @Test
    void toJson_shouldReturnRules_whenCalled() {
        final GitHubSetCopilotContentExclusionForEnterprisePayload payload =
                CopilotCatalogFixtures.setCopilotContentExclusionForEnterprisePayload();

        final Map<String, List<GitHubCopilotEnterpriseContentExclusionRule>> json = payload.toJson();

        assertThat(json).isEqualTo(payload.rules());
    }

    @Test
    void constructor_shouldThrow_whenRulesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSetCopilotContentExclusionForEnterprisePayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("rules");
    }
}

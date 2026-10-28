package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetCopilotContentExclusionForOrganizationPayloadTest {

    @Test
    void constructor_shouldThrow_whenRulesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSetCopilotContentExclusionForOrganizationPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("rules");
    }

    @Test
    void fromJson_shouldKeepRules_whenPresent() {
        final GitHubSetCopilotContentExclusionForOrganizationPayload payload =
                GitHubSetCopilotContentExclusionForOrganizationPayload.fromJson(
                        Map.of("octo-repo", List.of(CopilotCatalogFixtures.copilotContentExclusionRule())));

        assertThat(payload).isEqualTo(CopilotCatalogFixtures.setCopilotContentExclusionForOrganizationPayload());
        assertThat(payload.toJson()).isEqualTo(payload.rules());
    }
}

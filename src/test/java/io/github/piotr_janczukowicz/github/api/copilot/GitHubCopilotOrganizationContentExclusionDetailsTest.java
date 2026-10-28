package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotOrganizationContentExclusionDetailsTest {

    @Test
    void constructor_shouldThrow_whenRulesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotOrganizationContentExclusionDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("rules");
    }

    @Test
    void fromJson_shouldWrapRules_whenCalled() {
        final Map<String, List<String>> rules = Map.of("octo-repo", List.of("/src/secret.txt"));

        final GitHubCopilotOrganizationContentExclusionDetails details =
                GitHubCopilotOrganizationContentExclusionDetails.fromJson(rules);

        assertThat(details.rules()).isEqualTo(rules);
    }

    @Test
    void toJson_shouldReturnRules_whenCalled() {
        final Map<String, List<String>> rules = Map.of("octo-repo", List.of("/src/secret.txt"));

        final GitHubCopilotOrganizationContentExclusionDetails details =
                new GitHubCopilotOrganizationContentExclusionDetails(rules);

        assertThat(details.toJson()).isEqualTo(rules);
    }
}

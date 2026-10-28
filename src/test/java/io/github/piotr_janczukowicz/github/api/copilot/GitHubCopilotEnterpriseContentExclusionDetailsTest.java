package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotEnterpriseContentExclusionDetailsTest {

    @Test
    void fromJson_shouldWrapRules_whenCalled() {
        final Map<String, List<String>> rules = Map.of("octo-repo", List.of("/src/some-dir/kernel.rs"));

        final GitHubCopilotEnterpriseContentExclusionDetails details =
                GitHubCopilotEnterpriseContentExclusionDetails.fromJson(rules);

        assertThat(details.rules()).isEqualTo(rules);
    }

    @Test
    void toJson_shouldReturnRules_whenCalled() {
        final GitHubCopilotEnterpriseContentExclusionDetails details =
                CopilotCatalogFixtures.copilotEnterpriseContentExclusionDetails();

        final Map<String, List<String>> json = details.toJson();

        assertThat(json).isEqualTo(details.rules());
    }

    @Test
    void constructor_shouldThrow_whenRulesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotEnterpriseContentExclusionDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("rules");
    }
}

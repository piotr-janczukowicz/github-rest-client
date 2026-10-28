package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateIssueFieldPayloadTest {

    @Test
    void constructor_shouldConstruct_whenBuilderEmpty() {
        final GitHubUpdateIssueFieldPayload payload =
                GitHubUpdateIssueFieldPayloadBuilder.builder().build();

        assertThat(payload).isNotNull();
    }

    @Test
    void constructor_shouldCopyOptions_whenOptionsProvided() {
        final GitHubUpdateIssueFieldOption option = OrganizationsCatalogFixtures.updateIssueFieldOption();
        final GitHubUpdateIssueFieldPayload payload = GitHubUpdateIssueFieldPayloadBuilder.builder()
                .options(List.of(option))
                .build();

        assertThat(payload.options()).containsExactly(option);
    }
}

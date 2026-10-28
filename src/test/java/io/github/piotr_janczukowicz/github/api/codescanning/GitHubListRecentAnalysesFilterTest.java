package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRecentAnalysesFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubListRecentAnalysesFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubListRecentAnalysesFilter filter = GitHubListRecentAnalysesFilterBuilder.builder()
                .toolName("CodeQL")
                .toolGuid("0a9b3c7d-e8f1-4b2a-9c3d-5e6f7a8b9c0d")
                .pr(123)
                .ref("refs/heads/main")
                .sarifId("47177e22-5642-11ec-8d3d-0242ac130003")
                .direction(GitHubDirection.DESC)
                .sort(GitHubListRecentAnalysesSort.CREATED)
                .build();

        assertThat(filter.toQueryParameters())
                .isEqualTo(Map.of(
                        "tool_name", "CodeQL",
                        "tool_guid", "0a9b3c7d-e8f1-4b2a-9c3d-5e6f7a8b9c0d",
                        "pr", "123",
                        "ref", "refs/heads/main",
                        "sarif_id", "47177e22-5642-11ec-8d3d-0242ac130003",
                        "direction", "desc",
                        "sort", "created"));
    }
}

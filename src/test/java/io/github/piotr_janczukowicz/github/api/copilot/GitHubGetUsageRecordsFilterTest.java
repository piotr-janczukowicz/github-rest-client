package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetUsageRecordsFilterTest {

    @Test
    void toQueryParameters_shouldBeEmpty_whenFilterEmpty() {
        assertThat(GitHubGetUsageRecordsFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeEverySetField_whenFilterFull() {
        final GitHubGetUsageRecordsFilter filter = CopilotCatalogFixtures.getUsageRecordsFilter();

        assertThat(filter.toQueryParameters())
                .isEqualTo(Map.of("phrase", "type:request", "order", GitHubGetUsageRecordsSortOrder.DESC.wireValue()));
    }
}

package io.github.piotr_janczukowicz.github.api.campaigns;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListOrgCampaignsFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubListOrgCampaignsFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubListOrgCampaignsFilter filter = CampaignsCatalogFixtures.listOrgCampaignsFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEqualTo(Map.of("direction", "desc", "state", "open", "sort", "ends_at"));
    }
}

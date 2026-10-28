package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetUsageRecordsItemTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubGetUsageRecordsItem value = CopilotCatalogFixtures.getUsageRecordsItem();

        assertThat(value.type()).isEqualTo("sample-type");
        assertThat(value.userId()).isEqualTo(1L);
        assertThat(value.enterpriseId()).isEqualTo(1L);
        assertThat(value.githubRequestId()).isEqualTo("sample-github_request_id");
        assertThat(value.endpoint()).isEqualTo("sample-endpoint");
        assertThat(value.body()).isEqualTo("sample-body");
        assertThat(value.timestamp()).isEqualTo(1L);
        assertThat(value.eventId()).isEqualTo("sample-event_id");
    }
}

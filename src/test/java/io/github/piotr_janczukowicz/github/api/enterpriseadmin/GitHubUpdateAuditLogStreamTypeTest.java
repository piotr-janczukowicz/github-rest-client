package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAuditLogStreamTypeTest {

    @Test
    void fromWire_shouldReturnAzureBlobStorage_whenKnownValue() {
        assertThat(GitHubUpdateAuditLogStreamType.fromWire("Azure Blob Storage"))
                .isEqualTo(GitHubUpdateAuditLogStreamType.AZURE_BLOB_STORAGE);
    }

    @Test
    void fromWire_shouldReturnAzureEventHubs_whenKnownValue() {
        assertThat(GitHubUpdateAuditLogStreamType.fromWire("Azure Event Hubs"))
                .isEqualTo(GitHubUpdateAuditLogStreamType.AZURE_EVENT_HUBS);
    }

    @Test
    void fromWire_shouldReturnAmazonS3_whenKnownValue() {
        assertThat(GitHubUpdateAuditLogStreamType.fromWire("Amazon S3"))
                .isEqualTo(GitHubUpdateAuditLogStreamType.AMAZON_S3);
    }

    @Test
    void fromWire_shouldReturnSplunk_whenKnownValue() {
        assertThat(GitHubUpdateAuditLogStreamType.fromWire("Splunk")).isEqualTo(GitHubUpdateAuditLogStreamType.SPLUNK);
    }

    @Test
    void fromWire_shouldReturnHttpsEventCollector_whenKnownValue() {
        assertThat(GitHubUpdateAuditLogStreamType.fromWire("HTTPS Event Collector"))
                .isEqualTo(GitHubUpdateAuditLogStreamType.HTTPS_EVENT_COLLECTOR);
    }

    @Test
    void fromWire_shouldReturnGoogleCloudStorage_whenKnownValue() {
        assertThat(GitHubUpdateAuditLogStreamType.fromWire("Google Cloud Storage"))
                .isEqualTo(GitHubUpdateAuditLogStreamType.GOOGLE_CLOUD_STORAGE);
    }

    @Test
    void fromWire_shouldReturnDatadog_whenKnownValue() {
        assertThat(GitHubUpdateAuditLogStreamType.fromWire("Datadog"))
                .isEqualTo(GitHubUpdateAuditLogStreamType.DATADOG);
    }

    @Test
    void fromWire_shouldReturnMicrosoftAgent365_whenKnownValue() {
        assertThat(GitHubUpdateAuditLogStreamType.fromWire("Microsoft Agent 365"))
                .isEqualTo(GitHubUpdateAuditLogStreamType.MICROSOFT_AGENT_365);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateAuditLogStreamType.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}

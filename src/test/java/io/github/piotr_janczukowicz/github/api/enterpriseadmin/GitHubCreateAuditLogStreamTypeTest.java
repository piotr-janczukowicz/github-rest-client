package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateAuditLogStreamTypeTest {

    @Test
    void fromWire_shouldReturnAzureBlobStorage_whenKnownValue() {
        assertThat(GitHubCreateAuditLogStreamType.fromWire("Azure Blob Storage"))
                .isEqualTo(GitHubCreateAuditLogStreamType.AZURE_BLOB_STORAGE);
    }

    @Test
    void fromWire_shouldReturnAzureEventHubs_whenKnownValue() {
        assertThat(GitHubCreateAuditLogStreamType.fromWire("Azure Event Hubs"))
                .isEqualTo(GitHubCreateAuditLogStreamType.AZURE_EVENT_HUBS);
    }

    @Test
    void fromWire_shouldReturnAmazonS3_whenKnownValue() {
        assertThat(GitHubCreateAuditLogStreamType.fromWire("Amazon S3"))
                .isEqualTo(GitHubCreateAuditLogStreamType.AMAZON_S3);
    }

    @Test
    void fromWire_shouldReturnSplunk_whenKnownValue() {
        assertThat(GitHubCreateAuditLogStreamType.fromWire("Splunk")).isEqualTo(GitHubCreateAuditLogStreamType.SPLUNK);
    }

    @Test
    void fromWire_shouldReturnHttpsEventCollector_whenKnownValue() {
        assertThat(GitHubCreateAuditLogStreamType.fromWire("HTTPS Event Collector"))
                .isEqualTo(GitHubCreateAuditLogStreamType.HTTPS_EVENT_COLLECTOR);
    }

    @Test
    void fromWire_shouldReturnGoogleCloudStorage_whenKnownValue() {
        assertThat(GitHubCreateAuditLogStreamType.fromWire("Google Cloud Storage"))
                .isEqualTo(GitHubCreateAuditLogStreamType.GOOGLE_CLOUD_STORAGE);
    }

    @Test
    void fromWire_shouldReturnDatadog_whenKnownValue() {
        assertThat(GitHubCreateAuditLogStreamType.fromWire("Datadog"))
                .isEqualTo(GitHubCreateAuditLogStreamType.DATADOG);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateAuditLogStreamType.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}

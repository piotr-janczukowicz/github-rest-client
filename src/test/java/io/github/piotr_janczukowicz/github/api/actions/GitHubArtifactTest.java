package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubArtifactTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNodeIdNull() {
        final int id = 1;
        final String name = "name-sample";
        final int sizeInBytes = 1;
        final String url = "https://api.github.com/example";
        final String archiveDownloadUrl = "https://api.github.com/example";
        final boolean expired = true;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant expiresAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String digest = "digest-sample";
        final GitHubArtifactWorkflowRun workflowRun = ActionsCatalogFixtures.artifactWorkflowRun();

        final Throwable thrown = catchThrowable(() -> new GitHubArtifact(
                id,
                null,
                name,
                sizeInBytes,
                url,
                archiveDownloadUrl,
                expired,
                createdAt,
                expiresAt,
                updatedAt,
                digest,
                workflowRun));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final int id = 1;
        final String nodeId = "nodeId-sample";
        final int sizeInBytes = 1;
        final String url = "https://api.github.com/example";
        final String archiveDownloadUrl = "https://api.github.com/example";
        final boolean expired = true;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant expiresAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String digest = "digest-sample";
        final GitHubArtifactWorkflowRun workflowRun = ActionsCatalogFixtures.artifactWorkflowRun();

        final Throwable thrown = catchThrowable(() -> new GitHubArtifact(
                id,
                nodeId,
                null,
                sizeInBytes,
                url,
                archiveDownloadUrl,
                expired,
                createdAt,
                expiresAt,
                updatedAt,
                digest,
                workflowRun));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenUrlNull() {
        final int id = 1;
        final String nodeId = "nodeId-sample";
        final String name = "name-sample";
        final int sizeInBytes = 1;
        final String archiveDownloadUrl = "https://api.github.com/example";
        final boolean expired = true;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant expiresAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String digest = "digest-sample";
        final GitHubArtifactWorkflowRun workflowRun = ActionsCatalogFixtures.artifactWorkflowRun();

        final Throwable thrown = catchThrowable(() -> new GitHubArtifact(
                id,
                nodeId,
                name,
                sizeInBytes,
                null,
                archiveDownloadUrl,
                expired,
                createdAt,
                expiresAt,
                updatedAt,
                digest,
                workflowRun));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenArchiveDownloadUrlNull() {
        final int id = 1;
        final String nodeId = "nodeId-sample";
        final String name = "name-sample";
        final int sizeInBytes = 1;
        final String url = "https://api.github.com/example";
        final boolean expired = true;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant expiresAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String digest = "digest-sample";
        final GitHubArtifactWorkflowRun workflowRun = ActionsCatalogFixtures.artifactWorkflowRun();

        final Throwable thrown = catchThrowable(() -> new GitHubArtifact(
                id,
                nodeId,
                name,
                sizeInBytes,
                url,
                null,
                expired,
                createdAt,
                expiresAt,
                updatedAt,
                digest,
                workflowRun));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("archiveDownloadUrl");
    }
}

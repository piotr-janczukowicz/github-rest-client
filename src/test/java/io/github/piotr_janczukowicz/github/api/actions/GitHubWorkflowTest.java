package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubWorkflowTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNodeIdNull() {
        final int id = 1;
        final String name = "name-sample";
        final String path = "path-sample";
        final GitHubWorkflowState state = GitHubWorkflowState.ACTIVE;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final String badgeUrl = "https://api.github.com/example";
        final Instant deletedAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflow(
                id, null, name, path, state, createdAt, updatedAt, url, htmlUrl, badgeUrl, deletedAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final int id = 1;
        final String nodeId = "nodeId-sample";
        final String path = "path-sample";
        final GitHubWorkflowState state = GitHubWorkflowState.ACTIVE;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final String badgeUrl = "https://api.github.com/example";
        final Instant deletedAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflow(
                id, nodeId, null, path, state, createdAt, updatedAt, url, htmlUrl, badgeUrl, deletedAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenPathNull() {
        final int id = 1;
        final String nodeId = "nodeId-sample";
        final String name = "name-sample";
        final GitHubWorkflowState state = GitHubWorkflowState.ACTIVE;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final String badgeUrl = "https://api.github.com/example";
        final Instant deletedAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflow(
                id, nodeId, name, null, state, createdAt, updatedAt, url, htmlUrl, badgeUrl, deletedAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStateNull() {
        final int id = 1;
        final String nodeId = "nodeId-sample";
        final String name = "name-sample";
        final String path = "path-sample";
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final String badgeUrl = "https://api.github.com/example";
        final Instant deletedAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflow(
                id, nodeId, name, path, null, createdAt, updatedAt, url, htmlUrl, badgeUrl, deletedAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenCreatedAtNull() {
        final int id = 1;
        final String nodeId = "nodeId-sample";
        final String name = "name-sample";
        final String path = "path-sample";
        final GitHubWorkflowState state = GitHubWorkflowState.ACTIVE;
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final String badgeUrl = "https://api.github.com/example";
        final Instant deletedAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown = catchThrowable(() ->
                new GitHubWorkflow(id, nodeId, name, path, state, null, updatedAt, url, htmlUrl, badgeUrl, deletedAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenUpdatedAtNull() {
        final int id = 1;
        final String nodeId = "nodeId-sample";
        final String name = "name-sample";
        final String path = "path-sample";
        final GitHubWorkflowState state = GitHubWorkflowState.ACTIVE;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final String badgeUrl = "https://api.github.com/example";
        final Instant deletedAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown = catchThrowable(() ->
                new GitHubWorkflow(id, nodeId, name, path, state, createdAt, null, url, htmlUrl, badgeUrl, deletedAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenUrlNull() {
        final int id = 1;
        final String nodeId = "nodeId-sample";
        final String name = "name-sample";
        final String path = "path-sample";
        final GitHubWorkflowState state = GitHubWorkflowState.ACTIVE;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String htmlUrl = "https://github.com/example";
        final String badgeUrl = "https://api.github.com/example";
        final Instant deletedAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflow(
                id, nodeId, name, path, state, createdAt, updatedAt, null, htmlUrl, badgeUrl, deletedAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenHtmlUrlNull() {
        final int id = 1;
        final String nodeId = "nodeId-sample";
        final String name = "name-sample";
        final String path = "path-sample";
        final GitHubWorkflowState state = GitHubWorkflowState.ACTIVE;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String url = "https://api.github.com/example";
        final String badgeUrl = "https://api.github.com/example";
        final Instant deletedAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown = catchThrowable(() -> new GitHubWorkflow(
                id, nodeId, name, path, state, createdAt, updatedAt, url, null, badgeUrl, deletedAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenBadgeUrlNull() {
        final int id = 1;
        final String nodeId = "nodeId-sample";
        final String name = "name-sample";
        final String path = "path-sample";
        final GitHubWorkflowState state = GitHubWorkflowState.ACTIVE;
        final Instant createdAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant updatedAt = Instant.parse("2024-01-02T03:04:05Z");
        final String url = "https://api.github.com/example";
        final String htmlUrl = "https://github.com/example";
        final Instant deletedAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown = catchThrowable(() ->
                new GitHubWorkflow(id, nodeId, name, path, state, createdAt, updatedAt, url, htmlUrl, null, deletedAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("badgeUrl");
    }
}

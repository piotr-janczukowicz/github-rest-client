package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAlertLinkedIssueTest {

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final GitHubCodeScanningAlertLinkedIssueBuilder builder = GitHubCodeScanningAlertLinkedIssueBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAlertLinkedIssue())
                .title(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubCodeScanningAlertLinkedIssueBuilder builder = GitHubCodeScanningAlertLinkedIssueBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAlertLinkedIssue())
                .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubCodeScanningAlertLinkedIssueBuilder builder = GitHubCodeScanningAlertLinkedIssueBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAlertLinkedIssue())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubCodeScanningAlertLinkedIssueBuilder builder = GitHubCodeScanningAlertLinkedIssueBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAlertLinkedIssue())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryNull() {
        final GitHubCodeScanningAlertLinkedIssueBuilder builder = GitHubCodeScanningAlertLinkedIssueBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAlertLinkedIssue())
                .repository(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repository");
    }
}

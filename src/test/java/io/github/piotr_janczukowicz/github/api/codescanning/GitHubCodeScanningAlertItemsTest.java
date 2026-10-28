package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAlertItemsTest {

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAlertItemsBuilder.builder(CodeScanningCatalogFixtures.codeScanningAlertItems())
                        .createdAt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAlertItemsBuilder.builder(CodeScanningCatalogFixtures.codeScanningAlertItems())
                        .url(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAlertItemsBuilder.builder(CodeScanningCatalogFixtures.codeScanningAlertItems())
                        .htmlUrl(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenInstancesUrlNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAlertItemsBuilder.builder(CodeScanningCatalogFixtures.codeScanningAlertItems())
                        .instancesUrl(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("instancesUrl");
    }

    @Test
    void constructor_shouldThrow_whenDismissedCommentTooLong() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAlertItemsBuilder.builder(CodeScanningCatalogFixtures.codeScanningAlertItems())
                        .dismissedComment("x".repeat(281))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dismissedComment");
    }

    @Test
    void constructor_shouldThrow_whenRuleNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAlertItemsBuilder.builder(CodeScanningCatalogFixtures.codeScanningAlertItems())
                        .rule(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("rule");
    }

    @Test
    void constructor_shouldThrow_whenToolNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAlertItemsBuilder.builder(CodeScanningCatalogFixtures.codeScanningAlertItems())
                        .tool(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tool");
    }

    @Test
    void constructor_shouldThrow_whenMostRecentInstanceNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAlertItemsBuilder.builder(CodeScanningCatalogFixtures.codeScanningAlertItems())
                        .mostRecentInstance(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("mostRecentInstance");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubCodeScanningAlertItems built = GitHubCodeScanningAlertItemsBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAlertItems())
                .assignees(null)
                .build();

        assertThat(built.assignees()).isNull();
    }
}

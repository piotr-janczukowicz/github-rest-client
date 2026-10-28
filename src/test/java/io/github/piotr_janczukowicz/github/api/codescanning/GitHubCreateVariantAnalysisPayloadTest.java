package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateVariantAnalysisPayloadTest {

    @Test
    void constructor_shouldThrow_whenLanguageNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateVariantAnalysisPayloadBuilder.builder(
                        CodeScanningCatalogFixtures.createVariantAnalysisPayload())
                .language(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("language");
    }

    @Test
    void constructor_shouldThrow_whenQueryPackNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateVariantAnalysisPayloadBuilder.builder(
                        CodeScanningCatalogFixtures.createVariantAnalysisPayload())
                .queryPack(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("queryPack");
    }

    @Test
    void constructor_shouldThrow_whenNoRepositoryBranchSet() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateVariantAnalysisPayloadBuilder.builder(
                        CodeScanningCatalogFixtures.createVariantAnalysisPayload())
                .repositories(null)
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one of");
    }

    @Test
    void constructor_shouldThrow_whenMultipleRepositoryBranchesSet() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateVariantAnalysisPayloadBuilder.builder(
                        CodeScanningCatalogFixtures.createVariantAnalysisPayload())
                .repositoryLists(List.of("octo-org/list"))
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one of");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryListsTooManyItems() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateVariantAnalysisPayloadBuilder.builder(
                        CodeScanningCatalogFixtures.createVariantAnalysisPayloadByRepositoryLists())
                .repositoryLists(List.of("0", "1"))
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repositoryLists");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryOwnersTooManyItems() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateVariantAnalysisPayloadBuilder.builder(
                        CodeScanningCatalogFixtures.createVariantAnalysisPayloadByRepositoryOwners())
                .repositoryOwners(List.of("0", "1"))
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repositoryOwners");
    }
}

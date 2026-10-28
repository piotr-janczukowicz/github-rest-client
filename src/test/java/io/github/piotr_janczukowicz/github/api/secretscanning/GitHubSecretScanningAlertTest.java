package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningAlertTest {

    @Test
    void constructor_shouldAccept_whenOptionalRepositoryAlertFieldsNull() {
        final GitHubSecretScanningAlert alert = repositoryAlert(null, null, null, null, null, null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(alert.createdAt()).isNull();
            softly.assertThat(alert.secret()).isNull();
        });
    }

    @Test
    void constructor_shouldAccept_whenOptionalAlertFieldsNullAndMetadataSet() {
        final GitHubSecretScanningAlertWithMetadata alert =
                alertWithMetadata(null, null, null, null, null, null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(alert.createdAt()).isNull();
            softly.assertThat(alert.secret()).isNull();
            softly.assertThat(alert.metadata()).isNull();
        });
    }

    @Test
    void constructor_shouldAccept_whenOptionalOrganizationAlertFieldsNull() {
        final GitHubOrganizationSecretScanningAlert alert =
                organizationAlert(null, null, null, null, null, null, null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(alert.createdAt()).isNull();
            softly.assertThat(alert.repository()).isNull();
        });
    }

    private static GitHubOrganizationSecretScanningAlert organizationAlert(
            final Instant createdAt,
            final String url,
            final String htmlUrl,
            final String locationsUrl,
            final GitHubSecretScanningAlertState state,
            final String secretType,
            final String secretTypeDisplayName,
            final String secret,
            final GitHubSimpleRepository repository) {
        return new GitHubOrganizationSecretScanningAlert(
                2,
                createdAt,
                null,
                url,
                htmlUrl,
                locationsUrl,
                state,
                null,
                null,
                null,
                secretType,
                secretTypeDisplayName,
                null,
                null,
                secret,
                repository,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    private static GitHubSecretScanningAlert repositoryAlert(
            final Instant createdAt,
            final String url,
            final String htmlUrl,
            final String locationsUrl,
            final GitHubSecretScanningAlertState state,
            final String secretType,
            final String secretTypeDisplayName,
            final String secret) {
        return new GitHubSecretScanningAlert(
                2,
                createdAt,
                null,
                url,
                htmlUrl,
                locationsUrl,
                state,
                null,
                null,
                null,
                null,
                secretType,
                secretTypeDisplayName,
                null,
                null,
                secret,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    private static GitHubSecretScanningAlertWithMetadata alertWithMetadata(
            final Instant createdAt,
            final String url,
            final String htmlUrl,
            final String locationsUrl,
            final GitHubSecretScanningAlertState state,
            final String secretType,
            final String secretTypeDisplayName,
            final String secret) {
        return new GitHubSecretScanningAlertWithMetadata(
                2,
                createdAt,
                null,
                url,
                htmlUrl,
                locationsUrl,
                state,
                null,
                null,
                null,
                null,
                secretType,
                secretTypeDisplayName,
                null,
                null,
                secret,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    @Test
    void constructor_shouldThrow_whenMetadataKeyNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningAlertMetadata(null, "value"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("key");
    }

    @Test
    void constructor_shouldThrow_whenMetadataValueNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningAlertMetadata("key", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void toString_shouldRedactSecret_whenRepositoryAlertPopulated() {
        final GitHubSecretScanningAlert alert = SecretScanningCatalogFixtures.repoSecretScanningAlert();
        final String secret = alert.secret();

        final String text = alert.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("<redacted>");
            softly.assertThat(text).doesNotContain(secret);
        });
    }

    @Test
    void toString_shouldRedactSecret_whenOrganizationAlertPopulated() {
        final GitHubOrganizationSecretScanningAlert alert =
                SecretScanningCatalogFixtures.organizationSecretScanningAlert();
        final String secret = alert.secret();

        final String text = alert.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("<redacted>");
            softly.assertThat(text).doesNotContain(secret);
        });
    }

    @Test
    void toString_shouldRedactSecret_whenAlertWithMetadataPopulated() {
        final GitHubSecretScanningAlertWithMetadata alert =
                SecretScanningCatalogFixtures.secretScanningAlertWithMetadata();
        final String secret = alert.secret();

        final String text = alert.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("<redacted>");
            softly.assertThat(text).doesNotContain(secret);
        });
    }
}

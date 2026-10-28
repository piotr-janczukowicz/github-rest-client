package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningDismissalRequestTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubSecretScanningDismissalRequest sut = SecretScanningCatalogFixtures.secretScanningDismissalRequest();

        assertSoftly(softly -> {
            softly.assertThat(sut.id()).isNotNull();
            softly.assertThat(sut.number()).isNotNull();
            softly.assertThat(sut.repository()).isNotNull();
            softly.assertThat(sut.organization()).isNotNull();
            softly.assertThat(sut.requester()).isNotNull();
            softly.assertThat(sut.requestType()).isNotNull();
            softly.assertThat(sut.data()).isNotNull();
            softly.assertThat(sut.resourceIdentifier()).isNotNull();
            softly.assertThat(sut.status()).isNotNull();
            softly.assertThat(sut.requesterComment()).isNotNull();
            softly.assertThat(sut.expiresAt()).isNotNull();
            softly.assertThat(sut.createdAt()).isNotNull();
            softly.assertThat(sut.responses()).isNotNull();
            softly.assertThat(sut.url()).isNotNull();
            softly.assertThat(sut.htmlUrl()).isNotNull();
        });
    }

    @Test
    void constructor_shouldLeaveDataNull_whenNull() {
        final GitHubSecretScanningDismissalRequest sut = GitHubSecretScanningDismissalRequestBuilder.builder(
                        SecretScanningCatalogFixtures.secretScanningDismissalRequest())
                .data(null)
                .build();

        assertThat(sut.data()).isNull();
    }

    @Test
    void constructor_shouldLeaveResponsesNull_whenNull() {
        final GitHubSecretScanningDismissalRequest sut = GitHubSecretScanningDismissalRequestBuilder.builder(
                        SecretScanningCatalogFixtures.secretScanningDismissalRequest())
                .responses(null)
                .build();

        assertThat(sut.responses()).isNull();
    }
}

package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateOrgSecretPayloadTest {

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenEncryptedValueDoesNotMatch() {
        final String keyId = "keyId-sample";
        final GitHubOrgSecretVisibility visibility = GitHubOrgSecretVisibility.ALL;
        final List<Long> selectedRepositoryIds = List.of(1L);

        final Throwable thrown = catchThrowable(() ->
                new GitHubCreateOrUpdateOrgSecretPayload("!not valid!", keyId, visibility, selectedRepositoryIds));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenKeyIdNull() {
        final String encryptedValue = "YWJjZA==";
        final GitHubOrgSecretVisibility visibility = GitHubOrgSecretVisibility.ALL;
        final List<Long> selectedRepositoryIds = List.of(1L);

        final Throwable thrown = catchThrowable(() ->
                new GitHubCreateOrUpdateOrgSecretPayload(encryptedValue, null, visibility, selectedRepositoryIds));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("keyId");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenVisibilityNull() {
        final String encryptedValue = "YWJjZA==";
        final String keyId = "keyId-sample";
        final List<Long> selectedRepositoryIds = List.of(1L);

        final Throwable thrown = catchThrowable(
                () -> new GitHubCreateOrUpdateOrgSecretPayload(encryptedValue, keyId, null, selectedRepositoryIds));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("visibility");
    }

    @Test
    void constructor_shouldLeaveSelectedRepositoryIdsNull_whenNull() {
        final GitHubCreateOrUpdateOrgSecretPayload payload = GitHubCreateOrUpdateOrgSecretPayloadBuilder.builder(
                        ActionsCatalogFixtures.createOrUpdateOrgSecretPayload())
                .selectedRepositoryIds(null)
                .build();

        assertThat(payload.selectedRepositoryIds()).isNull();
    }

    @Test
    void toString_shouldRedactEncryptedValue() {
        final GitHubCreateOrUpdateOrgSecretPayload payload = ActionsCatalogFixtures.createOrUpdateOrgSecretPayload();

        final String text = payload.toString();

        assertThat(text).contains("encryptedValue=<redacted>");
    }
}

package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateOrgSecretPayloadTest {

    @Test
    void constructor_shouldThrow_whenVisibilityNull() {
        final GitHubCreateOrUpdateOrgSecretPayloadBuilder builder = GitHubCreateOrUpdateOrgSecretPayloadBuilder.builder(
                        CodespacesCatalogFixtures.createOrUpdateOrgSecretPayload())
                .visibility(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("visibility");
    }

    @Test
    void constructor_shouldCopySelectedRepositoryIds_whenMutableList() {
        final GitHubCreateOrUpdateOrgSecretPayload source = CodespacesCatalogFixtures.createOrUpdateOrgSecretPayload();
        final int size = source.selectedRepositoryIds().size();
        final List<Long> mutable = new ArrayList<>(source.selectedRepositoryIds());
        final GitHubCreateOrUpdateOrgSecretPayload sut = GitHubCreateOrUpdateOrgSecretPayloadBuilder.builder(source)
                .selectedRepositoryIds(mutable)
                .build();

        mutable.clear();

        assertThat(sut.selectedRepositoryIds()).hasSize(size);
    }

    @Test
    void constructor_shouldLeaveSelectedRepositoryIdsNull_whenNull() {
        final GitHubCreateOrUpdateOrgSecretPayloadBuilder builder = GitHubCreateOrUpdateOrgSecretPayloadBuilder.builder(
                        CodespacesCatalogFixtures.createOrUpdateOrgSecretPayload())
                .selectedRepositoryIds(null);

        final GitHubCreateOrUpdateOrgSecretPayload sut = builder.build();

        assertThat(sut.selectedRepositoryIds()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenEncryptedValueNotBase64() {
        final GitHubCreateOrUpdateOrgSecretPayloadBuilder builder = GitHubCreateOrUpdateOrgSecretPayloadBuilder.builder(
                        CodespacesCatalogFixtures.createOrUpdateOrgSecretPayload())
                .encryptedValue("not-valid-base64!");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("encryptedValue");
    }

    @Test
    void constructor_shouldLeaveEncryptedValueNull_whenNull() {
        final GitHubCreateOrUpdateOrgSecretPayloadBuilder builder = GitHubCreateOrUpdateOrgSecretPayloadBuilder.builder(
                        CodespacesCatalogFixtures.createOrUpdateOrgSecretPayload())
                .encryptedValue(null);

        final GitHubCreateOrUpdateOrgSecretPayload sut = builder.build();

        assertThat(sut.encryptedValue()).isNull();
    }

    @Test
    void toString_shouldRedactEncryptedValue_whenCalled() {
        final GitHubCreateOrUpdateOrgSecretPayload payload = CodespacesCatalogFixtures.createOrUpdateOrgSecretPayload();

        final String text = payload.toString();

        assertThat(text).contains("<redacted>").doesNotContain(payload.encryptedValue());
    }
}

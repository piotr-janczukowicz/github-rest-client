package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetCustomOidcSubClaimForRepoPayloadTest {

    @Test
    void constructor_shouldCopyIncludeClaimKeys_whenIncludeClaimKeysGiven() {
        final List<String> source = new ArrayList<>(
                ActionsCatalogFixtures.setCustomOidcSubClaimForRepoPayload().includeClaimKeys());
        final int size = source.size();
        final GitHubSetCustomOidcSubClaimForRepoPayload payload =
                GitHubSetCustomOidcSubClaimForRepoPayloadBuilder.builder(
                                ActionsCatalogFixtures.setCustomOidcSubClaimForRepoPayload())
                        .includeClaimKeys(source)
                        .build();

        source.add(source.getFirst());
        final List<String> includeClaimKeys = payload.includeClaimKeys();
        final String first = source.getFirst();

        final Throwable thrown = catchThrowable(() -> includeClaimKeys.add(first));

        assertSoftly(softly -> {
            softly.assertThat(payload.includeClaimKeys()).hasSize(size);
            softly.assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
        });
    }

    @Test
    void constructor_shouldAcceptNullIncludeClaimKeys_whenIncludeClaimKeysNull() {
        final GitHubSetCustomOidcSubClaimForRepoPayload payload =
                GitHubSetCustomOidcSubClaimForRepoPayloadBuilder.builder(
                                ActionsCatalogFixtures.setCustomOidcSubClaimForRepoPayload())
                        .includeClaimKeys(null)
                        .build();

        assertThat(payload.includeClaimKeys()).isNull();
    }
}

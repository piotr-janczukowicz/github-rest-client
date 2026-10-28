package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetImmutableReleasesSelectedRepositoriesPayloadTest {

    @Test
    void constructor_shouldThrow_whenSelectedRepositoryIdsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSetImmutableReleasesSelectedRepositoriesPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("selectedRepositoryIds");
    }
}

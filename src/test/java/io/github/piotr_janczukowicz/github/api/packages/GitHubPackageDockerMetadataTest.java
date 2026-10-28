package io.github.piotr_janczukowicz.github.api.packages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPackageDockerMetadataTest {

    @Test
    void constructor_shouldThrow_whenTagsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPackageDockerMetadata(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tags");
    }
}

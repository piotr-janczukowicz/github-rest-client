package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManifestTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubManifestBuilder builder = builder().name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenMetadataHasMoreThanEightKeys() {
        final Map<String, String> metadata = new HashMap<>();
        for (int index = 0; index < 9; index++) {
            metadata.put("key" + index, "value");
        }
        final GitHubManifestBuilder builder = builder().metadata(metadata);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("metadata");
    }

    @Test
    void constructor_shouldLeaveOptionalFieldsNull_whenNull() {
        final GitHubManifest manifest =
                builder().file(null).metadata(null).resolved(null).build();

        assertThat(manifest).hasAllNullFieldsOrPropertiesExcept("name");
    }

    private static GitHubManifestBuilder builder() {
        return GitHubManifestBuilder.builder(DependencyGraphCatalogFixtures.manifest());
    }
}

package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependencyTest {

    @Test
    void constructor_shouldThrow_whenPackageUrlDoesNotMatchPattern() {
        final GitHubDependencyBuilder builder = builder().packageUrl("maven/commons-io");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("packageUrl");
    }

    @Test
    void constructor_shouldThrow_whenMetadataHasMoreThanEightKeys() {
        final Map<String, String> metadata = new HashMap<>();
        for (int index = 0; index < 9; index++) {
            metadata.put("key" + index, "value");
        }
        final GitHubDependencyBuilder builder = builder().metadata(metadata);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("metadata");
    }

    @Test
    void constructor_shouldLeaveOptionalFieldsNull_whenNull() {
        final GitHubDependency dependency = builder()
                .packageUrl(null)
                .metadata(null)
                .relationship(null)
                .scope(null)
                .dependencies(null)
                .build();

        assertThat(dependency).hasAllNullFieldsOrProperties();
    }

    private static GitHubDependencyBuilder builder() {
        return GitHubDependencyBuilder.builder(DependencyGraphCatalogFixtures.dependency());
    }
}

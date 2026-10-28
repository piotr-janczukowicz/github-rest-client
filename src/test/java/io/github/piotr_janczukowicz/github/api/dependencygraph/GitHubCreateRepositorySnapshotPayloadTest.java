package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCreateRepositorySnapshotPayloadTest {

    @ParameterizedTest
    @MethodSource("nullRequiredFields")
    void constructor_shouldThrow_whenRequiredFieldNull(
            final GitHubCreateRepositorySnapshotPayloadBuilder builder, final String field) {
        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(field);
    }

    @ParameterizedTest
    @MethodSource("invalidShaOrRef")
    void constructor_shouldThrow_whenShaOrRefInvalid(
            final GitHubCreateRepositorySnapshotPayloadBuilder builder, final String field) {
        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(field);
    }

    @Test
    void constructor_shouldThrow_whenMetadataHasMoreThanEightKeys() {
        final Map<String, String> metadata = new HashMap<>();
        for (int index = 0; index < 9; index++) {
            metadata.put("key" + index, "value");
        }
        final GitHubCreateRepositorySnapshotPayloadBuilder builder = builder().metadata(metadata);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("metadata");
    }

    @Test
    void constructor_shouldAccept_whenRefInRemotesNamespace() {
        final GitHubCreateRepositorySnapshotPayload payload =
                builder().ref("refs/remotes/x").build();

        assertThat(payload.ref()).isEqualTo("refs/remotes/x");
    }

    @Test
    void constructor_shouldLeaveOptionalFieldsNull_whenNull() {
        final GitHubCreateRepositorySnapshotPayload payload =
                builder().metadata(null).manifests(null).build();

        assertThat(payload).hasAllNullFieldsOrPropertiesExcept("version", "job", "sha", "ref", "detector", "scanned");
    }

    private static Stream<Arguments> nullRequiredFields() {
        return Stream.of(
                Arguments.of(builder().job(null), "job"),
                Arguments.of(builder().detector(null), "detector"),
                Arguments.of(builder().scanned(null), "scanned"));
    }

    private static Stream<Arguments> invalidShaOrRef() {
        return Stream.of(
                Arguments.of(builder().sha("abc"), "sha"),
                Arguments.of(builder().sha("a".repeat(65)), "sha"),
                Arguments.of(builder().ref("heads/main"), "ref"));
    }

    private static GitHubCreateRepositorySnapshotPayloadBuilder builder() {
        return GitHubCreateRepositorySnapshotPayloadBuilder.builder(
                DependencyGraphCatalogFixtures.createRepositorySnapshotPayload());
    }
}

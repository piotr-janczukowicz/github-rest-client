package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.net.URI;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCreateArtifactStorageRecordPayloadTest {

    private static final String DIGEST = "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72";

    @Test
    void constructor_shouldAcceptPayload_whenRequiredFieldsPresent() {
        final GitHubCreateArtifactStorageRecordPayload payload =
                validPayloadBuilder().build();

        assertThat(payload.registryUrl()).hasToString("https://reg.example.com/artifactory/");
    }

    @Test
    void constructor_shouldAcceptOptionalFields_whenPresent() {
        final GitHubCreateArtifactStorageRecordPayload payload = validPayloadBuilder()
                .version("1.2.3")
                .artifactUrl(URI.create("https://reg.example.com/artifactory/bar/libfoo-1.2.3"))
                .path(URI.create("bar/libfoo"))
                .repository("bar")
                .status(GitHubArtifactStorageStatus.ACTIVE)
                .githubRepository("my-github-repo")
                .returnRecords(true)
                .build();

        assertThat(payload.status()).isEqualTo(GitHubArtifactStorageStatus.ACTIVE);
    }

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenInvalid(
            final ConstructorCall call, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidConstructors() {
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactStorageRecordPayloadBuilder.builder()
                                .digest(DIGEST)
                                .registryUrl(URI.create("https://reg.example.com/artifactory/"))
                                .build(),
                        NullPointerException.class,
                        "name"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactStorageRecordPayloadBuilder.builder()
                                .name("libfoo")
                                .registryUrl(URI.create("https://reg.example.com/artifactory/"))
                                .build(),
                        NullPointerException.class,
                        "digest"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactStorageRecordPayloadBuilder.builder()
                                .name("libfoo")
                                .digest(DIGEST)
                                .build(),
                        NullPointerException.class,
                        "registryUrl"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactStorageRecordPayloadBuilder.builder()
                                .name("libfoo")
                                .digest("sha256:invalid")
                                .registryUrl(URI.create("https://reg.example.com/artifactory/"))
                                .build(),
                        IllegalArgumentException.class,
                        "digest"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactStorageRecordPayloadBuilder.builder()
                                .name("libfoo")
                                .digest(DIGEST)
                                .registryUrl(URI.create("http://reg.example.com/artifactory/"))
                                .build(),
                        IllegalArgumentException.class,
                        "registryUrl"),
                Arguments.of(
                        (ConstructorCall) () -> validPayloadBuilder()
                                .artifactUrl(URI.create("http://reg.example.com/artifactory/bar"))
                                .build(),
                        IllegalArgumentException.class,
                        "artifactUrl"),
                Arguments.of(
                        (ConstructorCall) () -> validPayloadBuilder()
                                .githubRepository("invalid repo")
                                .build(),
                        IllegalArgumentException.class,
                        "githubRepository"));
    }

    @Test
    void constructor_shouldAcceptWhitespace_whenMinLengthOneFieldsAreBlank() {
        final GitHubCreateArtifactStorageRecordPayload payload =
                GitHubCreateArtifactStorageRecordPayloadBuilder.builder(
                                OrganizationsCatalogFixtures.createArtifactStorageRecordPayload())
                        .name(" ")
                        .version(" ")
                        .githubRepository(null)
                        .build();

        assertThat(payload.name()).isEqualTo(" ");
        assertThat(payload.version()).isEqualTo(" ");
    }

    @Test
    void constructor_shouldLeaveGithubRepositoryNull_whenNull() {
        final GitHubCreateArtifactStorageRecordPayload payload =
                GitHubCreateArtifactStorageRecordPayloadBuilder.builder(
                                OrganizationsCatalogFixtures.createArtifactStorageRecordPayload())
                        .githubRepository(null)
                        .build();

        assertThat(payload.githubRepository()).isNull();
    }

    @ParameterizedTest
    @MethodSource("emptyMinLengthFields")
    void constructor_shouldThrow_whenMinLengthFieldEmpty(final ConstructorCall call, final String field) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(field);
    }

    private static Stream<Arguments> emptyMinLengthFields() {
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactStorageRecordPayloadBuilder.builder(
                                        OrganizationsCatalogFixtures.createArtifactStorageRecordPayload())
                                .name("")
                                .build(),
                        "name"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactStorageRecordPayloadBuilder.builder(
                                        OrganizationsCatalogFixtures.createArtifactStorageRecordPayload())
                                .version("")
                                .build(),
                        "version"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactStorageRecordPayloadBuilder.builder(
                                        OrganizationsCatalogFixtures.createArtifactStorageRecordPayload())
                                .githubRepository("")
                                .build(),
                        "githubRepository"));
    }

    private static GitHubCreateArtifactStorageRecordPayloadBuilder validPayloadBuilder() {
        return GitHubCreateArtifactStorageRecordPayloadBuilder.builder()
                .name("libfoo")
                .digest(DIGEST)
                .registryUrl(URI.create("https://reg.example.com/artifactory/"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}

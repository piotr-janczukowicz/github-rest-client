package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCreateArtifactDeploymentRecordPayloadTest {

    private static final String DIGEST = "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72";

    @Test
    void constructor_shouldAcceptPayload_whenRequiredFieldsPresent() {
        final GitHubCreateArtifactDeploymentRecordPayload payload =
                validPayloadBuilder().build();

        assertThat(payload.name()).isEqualTo("awesome-image");
    }

    @Test
    void constructor_shouldAcceptOptionalFields_whenPresent() {
        final GitHubCreateArtifactDeploymentRecordPayload payload = validPayloadBuilder()
                .version("1.2.3")
                .physicalEnvironment("pacific-east")
                .cluster("moda-1")
                .tags(Map.of("team", "platform"))
                .runtimeRisks(List.of(GitHubArtifactRuntimeRisk.SENSITIVE_DATA))
                .githubRepository("my-github-repo")
                .returnRecords(true)
                .build();

        assertThat(payload.version()).isEqualTo("1.2.3");
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
                        (ConstructorCall) () -> GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder()
                                .digest(DIGEST)
                                .status(GitHubArtifactDeploymentStatus.DEPLOYED)
                                .logicalEnvironment("prod")
                                .deploymentName("deployment-pod")
                                .build(),
                        NullPointerException.class,
                        "name"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder()
                                .name("awesome-image")
                                .status(GitHubArtifactDeploymentStatus.DEPLOYED)
                                .logicalEnvironment("prod")
                                .deploymentName("deployment-pod")
                                .build(),
                        NullPointerException.class,
                        "digest"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder()
                                .name("awesome-image")
                                .digest(DIGEST)
                                .logicalEnvironment("prod")
                                .deploymentName("deployment-pod")
                                .build(),
                        NullPointerException.class,
                        "status"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder()
                                .name("awesome-image")
                                .digest(DIGEST)
                                .status(GitHubArtifactDeploymentStatus.DEPLOYED)
                                .deploymentName("deployment-pod")
                                .build(),
                        NullPointerException.class,
                        "logicalEnvironment"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder()
                                .name("awesome-image")
                                .digest(DIGEST)
                                .status(GitHubArtifactDeploymentStatus.DEPLOYED)
                                .logicalEnvironment("prod")
                                .build(),
                        NullPointerException.class,
                        "deploymentName"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder()
                                .name("awesome-image")
                                .digest("sha256:invalid")
                                .status(GitHubArtifactDeploymentStatus.DEPLOYED)
                                .logicalEnvironment("prod")
                                .deploymentName("deployment-pod")
                                .build(),
                        IllegalArgumentException.class,
                        "digest"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder()
                                .name("awesome-image")
                                .digest(DIGEST)
                                .status(GitHubArtifactDeploymentStatus.DEPLOYED)
                                .logicalEnvironment("prod")
                                .deploymentName("deployment-pod")
                                .tags(tooManyTags())
                                .build(),
                        IllegalArgumentException.class,
                        "tags"),
                Arguments.of(
                        (ConstructorCall) () -> validPayloadBuilder()
                                .runtimeRisks(List.of(
                                        GitHubArtifactRuntimeRisk.SENSITIVE_DATA,
                                        GitHubArtifactRuntimeRisk.SENSITIVE_DATA))
                                .build(),
                        IllegalArgumentException.class,
                        "runtimeRisks"),
                Arguments.of(
                        (ConstructorCall) () -> validPayloadBuilder()
                                .runtimeRisks(List.of(
                                        GitHubArtifactRuntimeRisk.SENSITIVE_DATA,
                                        GitHubArtifactRuntimeRisk.CRITICAL_RESOURCE,
                                        GitHubArtifactRuntimeRisk.INTERNET_EXPOSED,
                                        GitHubArtifactRuntimeRisk.LATERAL_MOVEMENT,
                                        GitHubArtifactRuntimeRisk.CRITICAL_RESOURCE))
                                .build(),
                        IllegalArgumentException.class,
                        "runtimeRisks"),
                Arguments.of(
                        (ConstructorCall) () -> validPayloadBuilder()
                                .githubRepository("invalid repo")
                                .build(),
                        IllegalArgumentException.class,
                        "githubRepository"));
    }

    @Test
    void constructor_shouldAcceptWhitespace_whenMinLengthOneFieldsAreBlank() {
        final GitHubCreateArtifactDeploymentRecordPayload payload =
                GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder(
                                OrganizationsCatalogFixtures.createArtifactDeploymentRecordPayload())
                        .name(" ")
                        .logicalEnvironment(" ")
                        .version(" ")
                        .githubRepository(null)
                        .build();

        assertThat(payload.name()).isEqualTo(" ");
        assertThat(payload.logicalEnvironment()).isEqualTo(" ");
        assertThat(payload.version()).isEqualTo(" ");
    }

    @Test
    void constructor_shouldLeaveGithubRepositoryNull_whenNull() {
        final GitHubCreateArtifactDeploymentRecordPayload payload =
                GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder(
                                OrganizationsCatalogFixtures.createArtifactDeploymentRecordPayload())
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
                        (ConstructorCall) () -> GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder(
                                        OrganizationsCatalogFixtures.createArtifactDeploymentRecordPayload())
                                .name("")
                                .build(),
                        "name"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder(
                                        OrganizationsCatalogFixtures.createArtifactDeploymentRecordPayload())
                                .logicalEnvironment("")
                                .build(),
                        "logicalEnvironment"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder(
                                        OrganizationsCatalogFixtures.createArtifactDeploymentRecordPayload())
                                .version("")
                                .build(),
                        "version"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder(
                                        OrganizationsCatalogFixtures.createArtifactDeploymentRecordPayload())
                                .githubRepository("")
                                .build(),
                        "githubRepository"));
    }

    private static GitHubCreateArtifactDeploymentRecordPayloadBuilder validPayloadBuilder() {
        return GitHubCreateArtifactDeploymentRecordPayloadBuilder.builder()
                .name("awesome-image")
                .digest(DIGEST)
                .status(GitHubArtifactDeploymentStatus.DEPLOYED)
                .logicalEnvironment("prod")
                .deploymentName("deployment-pod");
    }

    private static Map<String, String> tooManyTags() {
        final Map<String, String> tags = new LinkedHashMap<>();
        IntStream.rangeClosed(1, 6).forEach(i -> tags.put("key" + i, "value"));
        return tags;
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}

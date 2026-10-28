package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubClusterDeploymentRecordItemTest {

    private static final String DIGEST = "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72";

    @Test
    void constructor_shouldAcceptItem_whenRequiredFieldsPresent() {
        final GitHubClusterDeploymentRecordItem item = validItemBuilder().build();

        assertThat(item.name()).isEqualTo("awesome-image");
    }

    @Test
    void constructor_shouldAcceptOptionalFields_whenPresent() {
        final GitHubClusterDeploymentRecordItem item = validItemBuilder()
                .version("1.2.3")
                .status(GitHubArtifactDeploymentStatus.DEPLOYED)
                .githubRepository("my-github-repo")
                .tags(Map.of("team", "platform"))
                .runtimeRisks(List.of(GitHubArtifactRuntimeRisk.SENSITIVE_DATA))
                .build();

        assertThat(item.version()).isEqualTo("1.2.3");
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
                        (ConstructorCall) () -> GitHubClusterDeploymentRecordItemBuilder.builder()
                                .digest(DIGEST)
                                .deploymentName("deployment-pod")
                                .build(),
                        NullPointerException.class,
                        "name"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubClusterDeploymentRecordItemBuilder.builder()
                                .name("awesome-image")
                                .deploymentName("deployment-pod")
                                .build(),
                        NullPointerException.class,
                        "digest"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubClusterDeploymentRecordItemBuilder.builder()
                                .name("awesome-image")
                                .digest(DIGEST)
                                .build(),
                        NullPointerException.class,
                        "deploymentName"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubClusterDeploymentRecordItemBuilder.builder()
                                .name("awesome-image")
                                .digest("sha256:invalid")
                                .deploymentName("deployment-pod")
                                .build(),
                        IllegalArgumentException.class,
                        "digest"),
                Arguments.of(
                        (ConstructorCall) () -> validItemBuilder()
                                .githubRepository("invalid repo")
                                .build(),
                        IllegalArgumentException.class,
                        "githubRepository"),
                Arguments.of(
                        (ConstructorCall) () -> validItemBuilder()
                                .runtimeRisks(List.of(
                                        GitHubArtifactRuntimeRisk.SENSITIVE_DATA,
                                        GitHubArtifactRuntimeRisk.SENSITIVE_DATA))
                                .build(),
                        IllegalArgumentException.class,
                        "runtimeRisks"));
    }

    @Test
    void constructor_shouldThrow_whenRuntimeRisksContainDuplicates() {
        final Throwable thrown = catchThrowable(() -> GitHubClusterDeploymentRecordItemBuilder.builder(
                        OrganizationsCatalogFixtures.clusterDeploymentRecordItem())
                .runtimeRisks(
                        List.of(GitHubArtifactRuntimeRisk.SENSITIVE_DATA, GitHubArtifactRuntimeRisk.SENSITIVE_DATA))
                .build());

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("runtimeRisks")
                .hasStackTraceContaining("requireUniqueItems");
    }

    @Test
    void constructor_shouldThrow_whenRuntimeRisksExceedMaxItems() {
        final Throwable thrown = catchThrowable(() -> GitHubClusterDeploymentRecordItemBuilder.builder(
                        OrganizationsCatalogFixtures.clusterDeploymentRecordItem())
                .runtimeRisks(List.of(
                        GitHubArtifactRuntimeRisk.CRITICAL_RESOURCE,
                        GitHubArtifactRuntimeRisk.INTERNET_EXPOSED,
                        GitHubArtifactRuntimeRisk.LATERAL_MOVEMENT,
                        GitHubArtifactRuntimeRisk.SENSITIVE_DATA,
                        GitHubArtifactRuntimeRisk.CRITICAL_RESOURCE))
                .build());

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("runtimeRisks")
                .hasStackTraceContaining("requireSizeAtMost");
        assertThat(thrown.getStackTrace())
                .extracting(StackTraceElement::getMethodName)
                .doesNotContain("requireUniqueItems");
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
                        (ConstructorCall) () -> GitHubClusterDeploymentRecordItemBuilder.builder(
                                        OrganizationsCatalogFixtures.clusterDeploymentRecordItem())
                                .name("")
                                .build(),
                        "name"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubClusterDeploymentRecordItemBuilder.builder(
                                        OrganizationsCatalogFixtures.clusterDeploymentRecordItem())
                                .deploymentName("")
                                .build(),
                        "deploymentName"));
    }

    @Test
    void constructor_shouldAcceptWhitespace_whenNameAndDeploymentNameHaveMinLengthOne() {
        final GitHubClusterDeploymentRecordItem item = GitHubClusterDeploymentRecordItemBuilder.builder(
                        OrganizationsCatalogFixtures.clusterDeploymentRecordItem())
                .name(" ")
                .deploymentName(" ")
                .build();

        assertThat(item.name()).isEqualTo(" ");
        assertThat(item.deploymentName()).isEqualTo(" ");
    }

    @Test
    void constructor_shouldLeaveRuntimeRisksNull_whenNull() {
        final GitHubClusterDeploymentRecordItem item = GitHubClusterDeploymentRecordItemBuilder.builder(
                        OrganizationsCatalogFixtures.clusterDeploymentRecordItem())
                .runtimeRisks(null)
                .build();

        assertThat(item.runtimeRisks()).isNull();
    }

    private static GitHubClusterDeploymentRecordItemBuilder validItemBuilder() {
        return GitHubClusterDeploymentRecordItemBuilder.builder()
                .name("awesome-image")
                .digest(DIGEST)
                .deploymentName("deployment-pod");
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}

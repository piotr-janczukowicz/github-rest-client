package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCreateClusterDeploymentRecordsJobPayloadTest {

    @Test
    void constructor_shouldAcceptPayload_whenRequiredFieldsPresent() {
        final GitHubCreateClusterDeploymentRecordsJobPayload payload =
                validPayloadBuilder().build();

        assertThat(payload.deployments()).hasSize(1);
    }

    @Test
    void constructor_shouldAcceptEmptyDeployments_whenPresent() {
        final GitHubCreateClusterDeploymentRecordsJobPayload payload =
                GitHubCreateClusterDeploymentRecordsJobPayloadBuilder.builder()
                        .logicalEnvironment("prod")
                        .deployments(List.of())
                        .build();

        assertThat(payload.deployments()).isEmpty();
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
                        (ConstructorCall) () -> GitHubCreateClusterDeploymentRecordsJobPayloadBuilder.builder()
                                .deployments(List.of(OrganizationsCatalogFixtures.clusterDeploymentRecordItem()))
                                .build(),
                        NullPointerException.class,
                        "logicalEnvironment"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateClusterDeploymentRecordsJobPayloadBuilder.builder()
                                .logicalEnvironment("prod")
                                .build(),
                        NullPointerException.class,
                        "deployments"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateClusterDeploymentRecordsJobPayloadBuilder.builder()
                                .logicalEnvironment("prod")
                                .deployments(tooManyDeployments(5001))
                                .build(),
                        IllegalArgumentException.class,
                        "deployments"));
    }

    @Test
    void constructor_shouldAcceptWhitespace_whenLogicalEnvironmentHasMinLengthOne() {
        final GitHubCreateClusterDeploymentRecordsJobPayload payload =
                GitHubCreateClusterDeploymentRecordsJobPayloadBuilder.builder(
                                OrganizationsCatalogFixtures.createClusterDeploymentRecordsJobPayload())
                        .logicalEnvironment(" ")
                        .build();

        assertThat(payload.logicalEnvironment()).isEqualTo(" ");
    }

    @Test
    void constructor_shouldThrow_whenLogicalEnvironmentEmpty() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateClusterDeploymentRecordsJobPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.createClusterDeploymentRecordsJobPayload())
                .logicalEnvironment("")
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("logicalEnvironment");
    }

    private static GitHubCreateClusterDeploymentRecordsJobPayloadBuilder validPayloadBuilder() {
        return GitHubCreateClusterDeploymentRecordsJobPayloadBuilder.builder()
                .logicalEnvironment("prod")
                .deployments(List.of(OrganizationsCatalogFixtures.clusterDeploymentRecordItem()));
    }

    private static List<GitHubClusterDeploymentRecordItem> tooManyDeployments(final int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> OrganizationsCatalogFixtures.clusterDeploymentRecordItem())
                .toList();
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}

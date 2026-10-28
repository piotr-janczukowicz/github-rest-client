package io.github.piotr_janczukowicz.github.api.privateregistries;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCreateOrgPrivateRegistryPayloadTest {

    @Test
    void constructor_shouldAcceptPayload_whenEncryptedValueMatchesPattern() {
        final GitHubCreateOrgPrivateRegistryPayload payload = GitHubCreateOrgPrivateRegistryPayloadBuilder.builder()
                .registryType(GitHubPrivateRegistryType.MAVEN_REPOSITORY)
                .url(URI.create("https://maven.pkg.github.com/organization/"))
                .visibility(GitHubPrivateRegistryVisibility.PRIVATE)
                .encryptedValue("c2VjcmV0")
                .build();

        assertThat(payload.encryptedValue()).isEqualTo("c2VjcmV0");
    }

    @Test
    void constructor_shouldCopySelectedRepositoryIds_whenProvided() {
        final GitHubCreateOrgPrivateRegistryPayload payload = GitHubCreateOrgPrivateRegistryPayloadBuilder.builder()
                .registryType(GitHubPrivateRegistryType.MAVEN_REPOSITORY)
                .url(URI.create("https://maven.pkg.github.com/organization/"))
                .visibility(GitHubPrivateRegistryVisibility.SELECTED)
                .selectedRepositoryIds(List.of(1_296_269L, 1_296_280L))
                .build();

        assertThat(payload.selectedRepositoryIds()).containsExactly(1_296_269L, 1_296_280L);
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
                        (ConstructorCall) () -> GitHubCreateOrgPrivateRegistryPayloadBuilder.builder()
                                .url(URI.create("https://maven.pkg.github.com/organization/"))
                                .visibility(GitHubPrivateRegistryVisibility.PRIVATE)
                                .build(),
                        NullPointerException.class,
                        "registryType"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateOrgPrivateRegistryPayloadBuilder.builder()
                                .registryType(GitHubPrivateRegistryType.MAVEN_REPOSITORY)
                                .visibility(GitHubPrivateRegistryVisibility.PRIVATE)
                                .build(),
                        NullPointerException.class,
                        "url"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateOrgPrivateRegistryPayloadBuilder.builder()
                                .registryType(GitHubPrivateRegistryType.MAVEN_REPOSITORY)
                                .url(URI.create("https://maven.pkg.github.com/organization/"))
                                .build(),
                        NullPointerException.class,
                        "visibility"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateOrgPrivateRegistryPayloadBuilder.builder()
                                .registryType(GitHubPrivateRegistryType.MAVEN_REPOSITORY)
                                .url(URI.create("https://maven.pkg.github.com/organization/"))
                                .visibility(GitHubPrivateRegistryVisibility.PRIVATE)
                                .encryptedValue("not-valid-base64!")
                                .build(),
                        IllegalArgumentException.class,
                        "encryptedValue"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }

    @Test
    void toString_shouldRedactSecret_whenPopulated() {
        final GitHubCreateOrgPrivateRegistryPayload subject = GitHubCreateOrgPrivateRegistryPayloadBuilder.builder(
                        PrivateRegistriesCatalogFixtures.createOrgPrivateRegistryPayload())
                .encryptedValue("dG9wLXNlY3JldA==")
                .build();

        final String text = subject.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("<redacted>");
            softly.assertThat(text).doesNotContain("dG9wLXNlY3JldA==");
        });
    }
}

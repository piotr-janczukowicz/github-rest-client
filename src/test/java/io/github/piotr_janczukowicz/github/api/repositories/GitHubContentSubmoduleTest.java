package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubContentSubmoduleTest {

    @Test
    void constructor_shouldAcceptRequiredFields_whenValid() {
        final GitHubContentSubmodule submodule = RepositoriesCatalogFixtures.contentSubmodule();

        assertSoftly(softly -> {
            softly.assertThat(submodule.type()).isEqualTo(GitHubContentSubmoduleType.SUBMODULE);
            softly.assertThat(submodule.name()).isEqualTo("Hello-World");
        });
    }

    @ParameterizedTest
    @MethodSource("nullRequiredCases")
    void constructor_shouldThrow_whenRequiredFieldNull(final Runnable construction, final String field) {
        final Throwable thrown = catchThrowable(construction::run);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(field);
    }

    private static Stream<Arguments> nullRequiredCases() {
        final GitHubContentSubmodule base = RepositoriesCatalogFixtures.contentSubmodule();
        return Stream.of(
                Arguments.of(
                        (Runnable) () -> new GitHubContentSubmodule(
                                null,
                                base.submoduleGitUrl(),
                                base.size(),
                                base.name(),
                                base.path(),
                                base.sha(),
                                base.url(),
                                base.gitUrl(),
                                base.htmlUrl(),
                                base.downloadUrl(),
                                base.links()),
                        "type"),
                Arguments.of(
                        (Runnable) () -> new GitHubContentSubmodule(
                                base.type(),
                                null,
                                base.size(),
                                base.name(),
                                base.path(),
                                base.sha(),
                                base.url(),
                                base.gitUrl(),
                                base.htmlUrl(),
                                base.downloadUrl(),
                                base.links()),
                        "submoduleGitUrl"),
                Arguments.of(
                        (Runnable) () -> new GitHubContentSubmodule(
                                base.type(),
                                base.submoduleGitUrl(),
                                base.size(),
                                null,
                                base.path(),
                                base.sha(),
                                base.url(),
                                base.gitUrl(),
                                base.htmlUrl(),
                                base.downloadUrl(),
                                base.links()),
                        "name"),
                Arguments.of(
                        (Runnable) () -> new GitHubContentSubmodule(
                                base.type(),
                                base.submoduleGitUrl(),
                                base.size(),
                                base.name(),
                                null,
                                base.sha(),
                                base.url(),
                                base.gitUrl(),
                                base.htmlUrl(),
                                base.downloadUrl(),
                                base.links()),
                        "path"),
                Arguments.of(
                        (Runnable) () -> new GitHubContentSubmodule(
                                base.type(),
                                base.submoduleGitUrl(),
                                base.size(),
                                base.name(),
                                base.path(),
                                null,
                                base.url(),
                                base.gitUrl(),
                                base.htmlUrl(),
                                base.downloadUrl(),
                                base.links()),
                        "sha"),
                Arguments.of(
                        (Runnable) () -> new GitHubContentSubmodule(
                                base.type(),
                                base.submoduleGitUrl(),
                                base.size(),
                                base.name(),
                                base.path(),
                                base.sha(),
                                null,
                                base.gitUrl(),
                                base.htmlUrl(),
                                base.downloadUrl(),
                                base.links()),
                        "url"),
                Arguments.of(
                        (Runnable) () -> new GitHubContentSubmodule(
                                base.type(),
                                base.submoduleGitUrl(),
                                base.size(),
                                base.name(),
                                base.path(),
                                base.sha(),
                                base.url(),
                                base.gitUrl(),
                                base.htmlUrl(),
                                base.downloadUrl(),
                                null),
                        "links"));
    }
}

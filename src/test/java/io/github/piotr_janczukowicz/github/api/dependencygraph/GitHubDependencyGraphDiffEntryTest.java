package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubDependencyGraphDiffEntryTest {

    @ParameterizedTest
    @MethodSource("nullRequiredFields")
    void constructor_shouldThrow_whenRequiredFieldNull(
            final GitHubDependencyGraphDiffEntryBuilder builder, final String field) {
        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(field);
    }

    @Test
    void constructor_shouldLeaveNullableFieldsNull_whenNull() {
        final GitHubDependencyGraphDiffEntry entry = GitHubDependencyGraphDiffEntryBuilder.builder(
                        DependencyGraphCatalogFixtures.dependencyGraphDiffEntry())
                .packageUrl(null)
                .license(null)
                .sourceRepositoryUrl(null)
                .build();

        assertThat(entry)
                .hasAllNullFieldsOrPropertiesExcept(
                        "changeType", "manifest", "ecosystem", "name", "version", "vulnerabilities", "scope");
    }

    private static Stream<Arguments> nullRequiredFields() {
        return Stream.of(
                Arguments.of(builder().changeType(null), "changeType"),
                Arguments.of(builder().manifest(null), "manifest"),
                Arguments.of(builder().ecosystem(null), "ecosystem"),
                Arguments.of(builder().name(null), "name"),
                Arguments.of(builder().version(null), "version"),
                Arguments.of(builder().vulnerabilities(null), "vulnerabilities"),
                Arguments.of(builder().scope(null), "scope"));
    }

    private static GitHubDependencyGraphDiffEntryBuilder builder() {
        return GitHubDependencyGraphDiffEntryBuilder.builder(DependencyGraphCatalogFixtures.dependencyGraphDiffEntry());
    }
}

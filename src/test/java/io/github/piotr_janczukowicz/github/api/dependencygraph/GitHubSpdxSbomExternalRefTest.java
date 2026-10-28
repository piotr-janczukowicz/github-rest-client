package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubSpdxSbomExternalRefTest {

    @ParameterizedTest
    @MethodSource("nullRequiredFields")
    void constructor_shouldThrow_whenRequiredFieldNull(
            final GitHubSpdxSbomExternalRefBuilder builder, final String field) {
        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(field);
    }

    private static Stream<Arguments> nullRequiredFields() {
        return Stream.of(
                Arguments.of(builder().referenceCategory(null), "referenceCategory"),
                Arguments.of(builder().referenceLocator(null), "referenceLocator"),
                Arguments.of(builder().referenceType(null), "referenceType"));
    }

    private static GitHubSpdxSbomExternalRefBuilder builder() {
        return GitHubSpdxSbomExternalRefBuilder.builder(DependencyGraphCatalogFixtures.spdxSbomExternalRef());
    }
}

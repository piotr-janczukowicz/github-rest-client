package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("deprecation")
@UnitTest
class GitHubSpdxSbomTest {

    @ParameterizedTest
    @MethodSource("nullRequiredFields")
    void constructor_shouldThrow_whenRequiredFieldNull(final GitHubSpdxSbomBuilder builder, final String field) {
        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(field);
    }

    @Test
    void constructor_shouldLeaveOptionalFieldsNull_whenNull() {
        final GitHubSpdxSbom value = builder().comment(null).relationships(null).build();

        assertThat(value)
                .hasAllNullFieldsOrPropertiesExcept(
                        "spdxId",
                        "spdxVersion",
                        "creationInfo",
                        "name",
                        "dataLicense",
                        "documentNamespace",
                        "packages");
    }

    private static Stream<Arguments> nullRequiredFields() {
        return Stream.of(
                Arguments.of(builder().spdxId(null), "spdxId"),
                Arguments.of(builder().spdxVersion(null), "spdxVersion"),
                Arguments.of(builder().creationInfo(null), "creationInfo"),
                Arguments.of(builder().name(null), "name"),
                Arguments.of(builder().dataLicense(null), "dataLicense"),
                Arguments.of(builder().documentNamespace(null), "documentNamespace"),
                Arguments.of(builder().packages(null), "packages"));
    }

    private static GitHubSpdxSbomBuilder builder() {
        return GitHubSpdxSbomBuilder.builder(DependencyGraphCatalogFixtures.spdxSbom());
    }
}

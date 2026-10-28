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
class GitHubSpdxSbomPackageTest {

    @ParameterizedTest
    @MethodSource("nullRequiredFields")
    void constructor_shouldThrow_whenRequiredFieldNull(final GitHubSpdxSbomPackageBuilder builder, final String field) {
        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(field);
    }

    @Test
    void constructor_shouldLeaveOptionalFieldsNull_whenNull() {
        final GitHubSpdxSbomPackage value = builder()
                .licenseConcluded(null)
                .licenseDeclared(null)
                .supplier(null)
                .copyrightText(null)
                .externalRefs(null)
                .build();

        assertThat(value)
                .hasAllNullFieldsOrPropertiesExcept(
                        "spdxId", "name", "versionInfo", "downloadLocation", "filesAnalyzed");
    }

    private static Stream<Arguments> nullRequiredFields() {
        return Stream.of(
                Arguments.of(builder().spdxId(null), "spdxId"),
                Arguments.of(builder().name(null), "name"),
                Arguments.of(builder().versionInfo(null), "versionInfo"),
                Arguments.of(builder().downloadLocation(null), "downloadLocation"));
    }

    private static GitHubSpdxSbomPackageBuilder builder() {
        return GitHubSpdxSbomPackageBuilder.builder(DependencyGraphCatalogFixtures.spdxSbomPackage());
    }
}

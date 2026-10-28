package io.github.piotr_janczukowicz.github.api.packages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListPackagesFilterTest {

    @Test
    void constructor_shouldThrow_whenPackageTypeNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubListPackagesFilter(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("packageType");
    }

    @Test
    void of_shouldEmitPackageType_whenCalled() {
        final GitHubListPackagesFilter filter = GitHubListPackagesFilter.of(GitHubPackageType.CONTAINER);

        assertThat(filter.toQueryParameters()).containsEntry("package_type", "container");
    }

    @Test
    void toQueryParameters_shouldIncludeVisibility_whenSet() {
        final GitHubListPackagesFilter filter = GitHubListPackagesFilterBuilder.builder()
                .packageType(GitHubPackageType.CONTAINER)
                .visibility(GitHubPackageListVisibility.INTERNAL)
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("package_type", "container")
                .containsEntry("visibility", "internal");
    }
}

package io.github.piotr_janczukowicz.github.api.packages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPackageVersionMetadataTest {

    @Test
    void constructor_shouldRetainFields_whenBuilt() {
        final GitHubPackageVersionMetadata container = PackagesCatalogFixtures.containerMetadata();
        final GitHubPackageVersionMetadata docker = PackagesCatalogFixtures.dockerMetadata();
        final GitHubPackageVersionMetadata sameContainer = PackagesCatalogFixtures.containerMetadata();
        final GitHubPackageVersionMetadata sameDocker = PackagesCatalogFixtures.dockerMetadata();

        assertSoftly(softly -> {
            softly.assertThat(container.packageType()).isEqualTo(GitHubPackageType.CONTAINER);
            softly.assertThat(container.container()).isEqualTo(new GitHubPackageContainerMetadata(List.of("latest")));
            softly.assertThat(container.docker()).isNull();
            softly.assertThat(container).isEqualTo(sameContainer);
            softly.assertThat(container.hashCode()).isEqualTo(sameContainer.hashCode());
            softly.assertThat(container.toString()).contains("CONTAINER");
            softly.assertThat(docker.packageType()).isEqualTo(GitHubPackageType.DOCKER);
            softly.assertThat(docker.container()).isNull();
            softly.assertThat(docker.docker()).isEqualTo(new GitHubPackageDockerMetadata(List.of("latest")));
            softly.assertThat(docker).isEqualTo(sameDocker);
            softly.assertThat(docker.hashCode()).isEqualTo(sameDocker.hashCode());
            softly.assertThat(docker.toString()).contains("DOCKER");
        });
    }

    @Test
    void constructor_shouldAllowNullNestedMetadata_whenAbsent() {
        final GitHubPackageVersionMetadata metadata =
                new GitHubPackageVersionMetadata(GitHubPackageType.NPM, null, null);

        assertThat(metadata.container()).isNull();
        assertThat(metadata.docker()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPackageTypeNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPackageVersionMetadata(null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("packageType");
    }
}

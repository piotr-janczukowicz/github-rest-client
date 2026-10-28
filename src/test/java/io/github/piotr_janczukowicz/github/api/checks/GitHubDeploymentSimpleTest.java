package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeploymentSimpleTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubDeploymentSimpleBuilder.builder(ChecksCatalogFixtures.deploymentSimple())
                        .url(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubDeploymentSimpleBuilder.builder(ChecksCatalogFixtures.deploymentSimple())
                        .nodeId(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenTaskNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubDeploymentSimpleBuilder.builder(ChecksCatalogFixtures.deploymentSimple())
                        .task(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("task");
    }

    @Test
    void constructor_shouldThrow_whenEnvironmentNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubDeploymentSimpleBuilder.builder(ChecksCatalogFixtures.deploymentSimple())
                        .environment(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("environment");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubDeploymentSimpleBuilder.builder(ChecksCatalogFixtures.deploymentSimple())
                        .createdAt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubDeploymentSimpleBuilder.builder(ChecksCatalogFixtures.deploymentSimple())
                        .updatedAt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenStatusesUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubDeploymentSimpleBuilder.builder(ChecksCatalogFixtures.deploymentSimple())
                        .statusesUrl(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("statusesUrl");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubDeploymentSimpleBuilder.builder(ChecksCatalogFixtures.deploymentSimple())
                        .repositoryUrl(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryUrl");
    }
}

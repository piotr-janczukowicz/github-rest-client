package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateInstallationAccessTokenPayloadTest {

    @Test
    void constructor_shouldKeepNullRepositories_whenNull() {
        final GitHubCreateInstallationAccessTokenPayloadBuilder builder =
                GitHubCreateInstallationAccessTokenPayloadBuilder.builder(
                                AppsCatalogFixtures.createInstallationAccessTokenPayload())
                        .repositories(null);

        final GitHubCreateInstallationAccessTokenPayload value = builder.build();

        assertThat(value.repositories()).isNull();
    }

    @Test
    void constructor_shouldKeepNullRepositoryIds_whenNull() {
        final GitHubCreateInstallationAccessTokenPayloadBuilder builder =
                GitHubCreateInstallationAccessTokenPayloadBuilder.builder(
                                AppsCatalogFixtures.createInstallationAccessTokenPayload())
                        .repositoryIds(null);

        final GitHubCreateInstallationAccessTokenPayload value = builder.build();

        assertThat(value.repositoryIds()).isNull();
    }
}

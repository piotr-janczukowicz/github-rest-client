package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPublicKeyFullTest {

    @Test
    void constructor_shouldThrow_whenKeyNull() {
        final GitHubPublicKeyFullBuilder builder = GitHubPublicKeyFullBuilder.builder(
                        EnterpriseAdminCatalogFixtures.publicKeyFull())
                .key(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("key");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubPublicKeyFullBuilder builder = GitHubPublicKeyFullBuilder.builder(
                        EnterpriseAdminCatalogFixtures.publicKeyFull())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final GitHubPublicKeyFullBuilder builder = GitHubPublicKeyFullBuilder.builder(
                        EnterpriseAdminCatalogFixtures.publicKeyFull())
                .title(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubPublicKeyFullBuilder builder = GitHubPublicKeyFullBuilder.builder(
                        EnterpriseAdminCatalogFixtures.publicKeyFull())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }
}

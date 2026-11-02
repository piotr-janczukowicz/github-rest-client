package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryAdvisoryCommentTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubRepositoryAdvisoryCommentBuilder builder = GitHubRepositoryAdvisoryCommentBuilder.builder(
                        SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment())
                .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubRepositoryAdvisoryCommentBuilder builder = GitHubRepositoryAdvisoryCommentBuilder.builder(
                        SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubRepositoryAdvisoryCommentBuilder builder = GitHubRepositoryAdvisoryCommentBuilder.builder(
                        SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final GitHubRepositoryAdvisoryCommentBuilder builder = GitHubRepositoryAdvisoryCommentBuilder.builder(
                        SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment())
                .body(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubRepositoryAdvisoryCommentBuilder builder = GitHubRepositoryAdvisoryCommentBuilder.builder(
                        SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubRepositoryAdvisoryCommentBuilder builder = GitHubRepositoryAdvisoryCommentBuilder.builder(
                        SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenAdvisoryUrlNull() {
        final GitHubRepositoryAdvisoryCommentBuilder builder = GitHubRepositoryAdvisoryCommentBuilder.builder(
                        SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment())
                .advisoryUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("advisoryUrl");
    }

    @Test
    void constructor_shouldThrow_whenAuthorAssociationNull() {
        final GitHubRepositoryAdvisoryCommentBuilder builder = GitHubRepositoryAdvisoryCommentBuilder.builder(
                        SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment())
                .authorAssociation(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("authorAssociation");
    }
}

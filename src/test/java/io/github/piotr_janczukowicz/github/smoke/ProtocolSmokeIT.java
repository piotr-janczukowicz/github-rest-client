package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.GitHub;
import io.github.piotr_janczukowicz.github.api.actions.GitHubConcurrencyGroupListEntry;
import io.github.piotr_janczukowicz.github.api.issues.GitHubCreateLabelPayload;
import io.github.piotr_janczukowicz.github.api.issues.GitHubCreatePayload;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssue;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueApi;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueUpdateState;
import io.github.piotr_janczukowicz.github.api.issues.GitHubUpdatePayload;
import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicensesApi;
import io.github.piotr_janczukowicz.github.api.markdown.GitHubMarkdownMode;
import io.github.piotr_janczukowicz.github.api.markdown.GitHubMarkdownRawContentType;
import io.github.piotr_janczukowicz.github.api.markdown.GitHubRenderMarkdownPayload;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubFullRepository;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryApi;
import io.github.piotr_janczukowicz.github.api.search.GitHubRepoSearchResultItem;
import io.github.piotr_janczukowicz.github.client.exception.GitHubValidationException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smoke")
class ProtocolSmokeIT {

    private static final SmokeEnvironment ENV = SmokeEnvironment.fromEnv();

    @BeforeAll
    static void requireToken() {
        ENV.requireConfigured();
    }

    @Test
    void list_shouldReachASecondPage_whenPagedBySinceId() throws IOException {
        final GitHubPage<?> first = ENV.github().users().user().list(GitHubPageRequest.bySince());

        final Optional<? extends GitHubPage<?>> second = first.next();

        assertThat(first.items()).isNotEmpty();
        assertThat(second).isPresent();
        assertThat(second.orElseThrow().items()).isNotEmpty();
    }

    @Test
    void pages_shouldStreamTwoPages_whenPerPageIsOne() throws IOException {
        final GitHubLicensesApi licenses = ENV.github().licenses();

        final long pages = licenses.listCommonlyUsed(GitHubPageNumberRequest.of(1, 1))
                .pages()
                .limit(2)
                .count();

        assertThat(pages).isEqualTo(2);
    }

    @Test
    void stream_shouldCrossPageBoundaries_whenPerPageIsOne() throws IOException {
        final GitHubLicensesApi licenses = ENV.github().licenses();

        final List<GitHubLicenseSimple> streamed = licenses.listCommonlyUsed(GitHubPageNumberRequest.of(1, 1)).stream()
                .limit(3)
                .toList();

        assertThat(streamed).hasSize(3);
    }

    @Test
    void searchRepos_shouldReportATotalCount_whenSearchingTheOwnersRepositories() throws IOException {
        final GitHubPage<GitHubRepoSearchResultItem> page =
                ENV.github().search().searchRepos("user:" + ENV.owner() + " smoke in:name");

        assertThat(page.totalCount()).isNotNull().isPositive();
        assertThat(page.items()).extracting(GitHubRepoSearchResultItem::name).contains(ENV.repo());
    }

    @Test
    void listConcurrencyGroups_shouldParseACursorPage_whenReadingTheSandbox() throws IOException {
        final GitHubPage<GitHubConcurrencyGroupListEntry> page =
                ENV.github().actions().concurrencyGroups().listConcurrencyGroupsForRepository(ENV.owner(), ENV.repo());

        assertThat(page.items()).isNotNull();
    }

    @Test
    void lock_shouldRoundTrip_whenAnIssueIsLockedAndUnlocked() throws IOException {
        final GitHubIssueApi issues = ENV.github().issues().issue();
        final GitHubIssue created = issues.create(
                ENV.owner(),
                ENV.repo(),
                new GitHubCreatePayload(ENV.name("lock"), null, null, null, null, null, null, null));
        try {
            issues.lock(ENV.owner(), ENV.repo(), created.number());
            final boolean locked = issues.get(ENV.owner(), ENV.repo(), created.number())
                    .orElseThrow()
                    .locked();
            issues.unlock(ENV.owner(), ENV.repo(), created.number());
            final boolean unlocked = issues.get(ENV.owner(), ENV.repo(), created.number())
                    .orElseThrow()
                    .locked();

            assertThat(locked).isTrue();
            assertThat(unlocked).isFalse();
        } finally {
            issues.update(
                    ENV.owner(),
                    ENV.repo(),
                    created.number(),
                    new GitHubUpdatePayload(
                            null, null, GitHubIssueUpdateState.CLOSED, null, null, null, null, null, null, null));
        }
    }

    @Test
    void downloadZipballArchive_shouldReturnTheRedirectTarget_whenReadingTheDefaultBranch() throws IOException {
        final String branch = ENV.github()
                .repositories()
                .repository()
                .get(ENV.owner(), ENV.repo())
                .orElseThrow()
                .defaultBranch();

        final URI location =
                ENV.github().repositories().contents().downloadZipballArchive(ENV.owner(), ENV.repo(), branch);

        assertThat(location.getHost()).isNotBlank();
    }

    @Test
    void render_shouldReturnHtml_whenRenderingMarkdown() throws IOException {
        final String html = ENV.github()
                .markdown()
                .render(new GitHubRenderMarkdownPayload("**smoke**", GitHubMarkdownMode.MARKDOWN, null));

        assertThat(html).contains("<strong>smoke</strong>");
    }

    @Test
    void renderRaw_shouldReturnHtml_whenSendingPlainText() throws IOException {
        final String html = ENV.github().markdown().renderRaw("# smoke", GitHubMarkdownRawContentType.TEXT_X_MARKDOWN);

        assertThat(html).contains("smoke");
    }

    @Test
    void createLabel_shouldRaiseValidation_whenTheLabelAlreadyExists() throws IOException {
        final GitHub github = ENV.github();
        final String name = ENV.name("dup");
        github.issues()
                .labels()
                .createLabel(ENV.owner(), ENV.repo(), new GitHubCreateLabelPayload(name, "ededed", null));
        try {
            final Throwable thrown = catchThrowable(() -> github.issues()
                    .labels()
                    .createLabel(ENV.owner(), ENV.repo(), new GitHubCreateLabelPayload(name, "ededed", null)));

            assertThat(thrown).isInstanceOf(GitHubValidationException.class);
        } finally {
            github.issues().labels().deleteLabel(ENV.owner(), ENV.repo(), name);
        }
    }

    @Test
    void get_shouldKeepWorking_whenTheSameResourceIsReadTwice() throws IOException {
        final GitHubRepositoryApi repositories = ENV.github().repositories().repository();

        final Optional<GitHubFullRepository> first = repositories.get(ENV.owner(), ENV.repo());
        final Optional<GitHubFullRepository> second = repositories.get(ENV.owner(), ENV.repo());

        assertThat(List.of(first, second))
                .allSatisfy(repository -> assertThat(repository).isPresent());
    }
}

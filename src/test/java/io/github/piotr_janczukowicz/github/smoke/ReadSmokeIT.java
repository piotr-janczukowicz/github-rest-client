package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.GitHub;
import io.github.piotr_janczukowicz.github.api.branches.GitHubShortBranch;
import io.github.piotr_janczukowicz.github.api.codesofconduct.GitHubCodeOfConduct;
import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.ratelimit.GitHubRateLimitOverview;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubFullRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserView;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.GitHubClients;
import io.github.piotr_janczukowicz.github.client.exception.GitHubUnauthorizedException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.client.authentication.AuthenticationProviders;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smoke")
class ReadSmokeIT {

    private static final SmokeEnvironment ENV = SmokeEnvironment.fromEnv();

    @BeforeAll
    static void requireToken() {
        ENV.requireConfigured();
    }

    @Test
    void get_shouldReportRemainingCalls_whenReadingTheRateLimit() throws IOException {
        final Optional<GitHubRateLimitOverview> overview =
                ENV.github().rateLimit().get();

        assertThat(overview).isPresent();
        assertThat(overview.orElseThrow().resources().core().limit()).isPositive();
    }

    @Test
    void zen_shouldReturnAText_whenReadingMeta() throws IOException {
        final String zen = ENV.github().meta().zen();

        assertThat(zen).isNotBlank();
    }

    @Test
    void get_shouldContainThePlusOneEmoji_whenReadingEmojis() throws IOException {
        final Map<String, String> emojis = ENV.github().emojis().get();

        assertThat(emojis).containsKey("+1");
    }

    @Test
    void listCommonlyUsed_shouldContainMit_whenReadingLicenses() throws IOException {
        final List<GitHubLicenseSimple> licenses =
                ENV.github().licenses().listCommonlyUsed().all();

        assertThat(licenses).extracting(GitHubLicenseSimple::key).contains("mit");
    }

    @Test
    void templates_shouldContainJava_whenReadingGitignore() throws IOException {
        final List<String> templates = ENV.github().gitignore().templates();

        assertThat(templates).contains("Java");
    }

    @Test
    void getAllCodesOfConduct_shouldBeNonEmpty() throws IOException {
        final List<GitHubCodeOfConduct> codes = ENV.github().codeOfConduct().getAllCodesOfConduct();

        assertThat(codes).isNotEmpty();
    }

    @Test
    void getByLogin_shouldReturnTheOwner_whenReadingTheAccount() throws IOException {
        final Optional<GitHubUserView> account = ENV.github().users().user().getByLogin(ENV.owner());

        assertThat(account).isPresent();
    }

    @Test
    void get_shouldHaveADefaultBranchInTheBranchList_whenReadingTheSandbox() throws IOException {
        final GitHub github = ENV.github();
        final GitHubFullRepository repository =
                github.repositories().repository().get(ENV.owner(), ENV.repo()).orElseThrow();

        final List<GitHubShortBranch> branches =
                github.branches().branch().listBranches(ENV.owner(), ENV.repo()).all();

        assertThat(branches).extracting(GitHubShortBranch::name).contains(repository.defaultBranch());
    }

    @Test
    void listCommonlyUsed_shouldReachASecondPage_whenPerPageIsOne() throws IOException {
        final GitHubPage<GitHubLicenseSimple> first =
                ENV.github().licenses().listCommonlyUsed(GitHubPageNumberRequest.of(1, 1));

        final Optional<GitHubPage<GitHubLicenseSimple>> second = first.next();

        assertThat(first.items()).hasSize(1);
        assertThat(second).isPresent();
        assertThat(second.orElseThrow().items()).hasSize(1);
    }

    @Test
    void get_shouldBeEmpty_whenTheRepositoryDoesNotExist() throws IOException {
        final Optional<GitHubFullRepository> repository =
                ENV.github().repositories().repository().get(ENV.owner(), "smoke-does-not-exist");

        assertThat(repository).isEmpty();
    }

    @Test
    void getAuthenticatedUser_shouldRaiseUnauthorized_whenTheTokenIsInvalid() {
        try (GitHubClient client =
                GitHubClients.create(AuthenticationProviders.bearer("invalid"), "github-api-smoke/1.0")) {
            final GitHub github = new GitHub(GitHubCaller.of(client));

            final Throwable thrown = catchThrowable(() -> github.users().user().getAuthenticatedUser());

            assertThat(thrown).isInstanceOf(GitHubUnauthorizedException.class);
        }
    }
}

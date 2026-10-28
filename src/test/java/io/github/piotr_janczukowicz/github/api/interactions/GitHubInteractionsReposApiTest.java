package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubInteractionsReposApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String LIMITS_URI = "https://api.github.com/repos/octocat/Hello-World/interaction-limits";
    private static final String BYPASS_URI =
            "https://api.github.com/repos/octocat/Hello-World/interaction-limits/pulls/bypass-list";
    private static final String CREATION_CAP_URI =
            "https://api.github.com/repos/octocat/Hello-World/interaction-limits/pulls/creation-cap";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubInteractionsReposApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getRestrictionsForRepo_shouldReturnRestrictions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("interaction-limit-response.json")));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForRepo(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(restrictions).contains(InteractionsCatalogFixtures.interactionLimitResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(LIMITS_URI);
        });
    }

    @Test
    void getRestrictionsForRepo_shouldReturnEmpty_whenBodyIsEmptyObject() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "{}"));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForRepo(OWNER, REPO);

        assertThat(restrictions).isEmpty();
    }

    @Test
    void getRestrictionsForRepo_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForRepo(OWNER, REPO);

        assertThat(restrictions).isEmpty();
    }

    @Test
    void getRestrictionsForRepo_shouldReturnEmpty_whenBodyAbsent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), null));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForRepo(OWNER, REPO);

        assertThat(restrictions).isEmpty();
    }

    @Test
    void setRestrictionsForRepo_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("interaction-limit-response.json")));

        final GitHubInteractionLimitResponse restrictions =
                api().setRestrictionsForRepo(OWNER, REPO, InteractionsCatalogFixtures.interactionLimit());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(restrictions).isEqualTo(InteractionsCatalogFixtures.interactionLimitResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString(LIMITS_URI);
        });
    }

    @Test
    void removeRestrictionsForRepo_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeRestrictionsForRepo(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(LIMITS_URI);
        });
    }

    @Test
    void getPullRequestBypassListForRepo_shouldReturnUsers_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + simpleUserJson() + "]"));

        final List<GitHubSimpleUser> users = api().getPullRequestBypassListForRepo(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(users).containsExactly(UsersCatalogFixtures.octocat());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(BYPASS_URI);
        });
    }

    @Test
    void setPullRequestBypassListForRepo_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setPullRequestBypassListForRepo(
                        OWNER, REPO, InteractionsCatalogFixtures.interactionLimitPullRequestBypassList());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString(BYPASS_URI);
        });
    }

    @Test
    void removePullRequestBypassListForRepo_shouldDeletePayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removePullRequestBypassListForRepo(
                        OWNER, REPO, InteractionsCatalogFixtures.interactionLimitPullRequestBypassList());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(BYPASS_URI);
        });
    }

    @Test
    void getPullRequestCreationCapForRepo_shouldReturnCap_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-creation-cap.json")));

        final GitHubPullRequestCreationCap cap = api().getPullRequestCreationCapForRepo(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(cap).isEqualTo(InteractionsCatalogFixtures.pullRequestCreationCap());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(CREATION_CAP_URI);
        });
    }

    @Test
    void getPullRequestCreationCapForRepo_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubInteractionsReposApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getPullRequestCreationCapForRepo(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void updatePullRequestCreationCapForRepo_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-creation-cap.json")));

        final GitHubPullRequestCreationCap cap = api().updatePullRequestCreationCapForRepo(
                        OWNER, REPO, InteractionsCatalogFixtures.updatePullRequestCreationCapForOrgPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(cap).isEqualTo(InteractionsCatalogFixtures.pullRequestCreationCap());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(CREATION_CAP_URI);
        });
    }

    @ParameterizedTest
    @MethodSource("blankOwnerCalls")
    void call_shouldThrow_whenOwnerBlank(final RepoCall call) {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsReposApi reposApi = new GitHubInteractionsReposApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> call.execute(reposApi));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("owner");
        }
    }

    @ParameterizedTest
    @MethodSource("blankRepoCalls")
    void call_shouldThrow_whenRepoBlank(final RepoCall call) {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsReposApi reposApi = new GitHubInteractionsReposApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> call.execute(reposApi));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repo");
        }
    }

    @Test
    void setRestrictionsForRepo_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsReposApi reposApi = new GitHubInteractionsReposApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> reposApi.setRestrictionsForRepo(OWNER, REPO, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void setPullRequestBypassListForRepo_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsReposApi reposApi = new GitHubInteractionsReposApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> reposApi.setPullRequestBypassListForRepo(OWNER, REPO, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void removePullRequestBypassListForRepo_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsReposApi reposApi = new GitHubInteractionsReposApi(TestClients.caller(client));

            final Throwable thrown =
                    catchThrowable(() -> reposApi.removePullRequestBypassListForRepo(OWNER, REPO, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void updatePullRequestCreationCapForRepo_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsReposApi reposApi = new GitHubInteractionsReposApi(TestClients.caller(client));

            final Throwable thrown =
                    catchThrowable(() -> reposApi.updatePullRequestCreationCapForRepo(OWNER, REPO, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    private GitHubInteractionsReposApi api() {
        return new GitHubInteractionsReposApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubInteractionsReposApiTest.class, fileName);
    }

    private static String simpleUserJson() {
        return FileUtils.readString(GitHubSimpleUser.class, "simple-user-octocat.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<RepoCall> blankOwnerCalls() {
        return blankPathCalls(" ", REPO);
    }

    private static Stream<RepoCall> blankRepoCalls() {
        return blankPathCalls(OWNER, " ");
    }

    private static Stream<RepoCall> blankPathCalls(final String owner, final String repo) {
        final GitHubInteractionLimit limit = InteractionsCatalogFixtures.interactionLimit();
        final GitHubInteractionLimitPullRequestBypassList bypass =
                InteractionsCatalogFixtures.interactionLimitPullRequestBypassList();
        final GitHubUpdatePullRequestCreationCapForOrgPayload cap =
                InteractionsCatalogFixtures.updatePullRequestCreationCapForOrgPayload();
        return Stream.of(
                api -> api.getRestrictionsForRepo(owner, repo),
                api -> api.setRestrictionsForRepo(owner, repo, limit),
                api -> api.removeRestrictionsForRepo(owner, repo),
                api -> api.getPullRequestBypassListForRepo(owner, repo),
                api -> api.setPullRequestBypassListForRepo(owner, repo, bypass),
                api -> api.removePullRequestBypassListForRepo(owner, repo, bypass),
                api -> api.getPullRequestCreationCapForRepo(owner, repo),
                api -> api.updatePullRequestCreationCapForRepo(owner, repo, cap));
    }

    @FunctionalInterface
    private interface RepoCall {
        void execute(GitHubInteractionsReposApi api) throws Exception;
    }
}

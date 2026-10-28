package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubInteractionsOrgsApiTest {

    private static final String ORG = "github";
    private static final String LIMITS_URI = "https://api.github.com/orgs/github/interaction-limits";
    private static final String CREATION_CAP_URI =
            "https://api.github.com/orgs/github/interaction-limits/pulls/creation-cap";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubInteractionsOrgsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getRestrictionsForOrg_shouldReturnRestrictions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("interaction-limit-response.json")));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForOrg(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(restrictions).contains(InteractionsCatalogFixtures.interactionLimitResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(LIMITS_URI);
        });
    }

    @Test
    void getRestrictionsForOrg_shouldReturnEmpty_whenBodyIsEmptyObject() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "  {}\n"));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForOrg(ORG);

        assertThat(restrictions).isEmpty();
    }

    @Test
    void getRestrictionsForOrg_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForOrg(ORG);

        assertThat(restrictions).isEmpty();
    }

    @Test
    void getRestrictionsForOrg_shouldReturnEmpty_whenBodyAbsent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), null));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForOrg(ORG);

        assertThat(restrictions).isEmpty();
    }

    @Test
    void setRestrictionsForOrg_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("interaction-limit-response.json")));

        final GitHubInteractionLimitResponse restrictions =
                api().setRestrictionsForOrg(ORG, InteractionsCatalogFixtures.interactionLimit());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(restrictions).isEqualTo(InteractionsCatalogFixtures.interactionLimitResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString(LIMITS_URI);
        });
    }

    @Test
    void removeRestrictionsForOrg_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeRestrictionsForOrg(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(LIMITS_URI);
        });
    }

    @Test
    void getPullRequestCreationCapForOrg_shouldReturnCap_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-creation-cap.json")));

        final GitHubPullRequestCreationCap cap = api().getPullRequestCreationCapForOrg(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(cap).isEqualTo(InteractionsCatalogFixtures.pullRequestCreationCap());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(CREATION_CAP_URI);
        });
    }

    @Test
    void getPullRequestCreationCapForOrg_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubInteractionsOrgsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getPullRequestCreationCapForOrg(ORG));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void updatePullRequestCreationCapForOrg_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-creation-cap.json")));

        final GitHubPullRequestCreationCap cap = api().updatePullRequestCreationCapForOrg(
                        ORG, InteractionsCatalogFixtures.updatePullRequestCreationCapForOrgPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(cap).isEqualTo(InteractionsCatalogFixtures.pullRequestCreationCap());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(CREATION_CAP_URI);
        });
    }

    @ParameterizedTest
    @MethodSource("blankOrgCalls")
    void call_shouldThrow_whenOrgBlank(final OrgCall call) {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsOrgsApi orgsApi = new GitHubInteractionsOrgsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> call.execute(orgsApi));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void setRestrictionsForOrg_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsOrgsApi orgsApi = new GitHubInteractionsOrgsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> orgsApi.setRestrictionsForOrg(ORG, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void updatePullRequestCreationCapForOrg_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsOrgsApi orgsApi = new GitHubInteractionsOrgsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> orgsApi.updatePullRequestCreationCapForOrg(ORG, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    private GitHubInteractionsOrgsApi api() {
        return new GitHubInteractionsOrgsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubInteractionsOrgsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<OrgCall> blankOrgCalls() {
        final GitHubInteractionLimit limit = InteractionsCatalogFixtures.interactionLimit();
        final GitHubUpdatePullRequestCreationCapForOrgPayload cap =
                InteractionsCatalogFixtures.updatePullRequestCreationCapForOrgPayload();
        return Stream.of(
                api -> api.getRestrictionsForOrg(" "),
                api -> api.setRestrictionsForOrg(" ", limit),
                api -> api.removeRestrictionsForOrg(" "),
                api -> api.getPullRequestCreationCapForOrg(" "),
                api -> api.updatePullRequestCreationCapForOrg(" ", cap));
    }

    @FunctionalInterface
    private interface OrgCall {
        void execute(GitHubInteractionsOrgsApi api) throws Exception;
    }
}

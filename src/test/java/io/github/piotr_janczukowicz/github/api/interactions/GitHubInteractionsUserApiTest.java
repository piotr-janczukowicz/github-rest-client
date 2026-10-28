package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubInteractionsUserApiTest {

    private static final String LIMITS_URI = "https://api.github.com/user/interaction-limits";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubInteractionsUserApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getRestrictionsForAuthenticatedUser_shouldReturnRestrictions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("interaction-limit-response.json")));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForAuthenticatedUser();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(restrictions).contains(InteractionsCatalogFixtures.interactionLimitResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(LIMITS_URI);
        });
    }

    @Test
    void getRestrictionsForAuthenticatedUser_shouldReturnEmpty_whenBodyIsEmptyObject() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "\t{}  "));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForAuthenticatedUser();

        assertThat(restrictions).isEmpty();
    }

    @Test
    void getRestrictionsForAuthenticatedUser_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForAuthenticatedUser();

        assertThat(restrictions).isEmpty();
    }

    @Test
    void getRestrictionsForAuthenticatedUser_shouldReturnEmpty_whenBodyAbsent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), null));

        final Optional<GitHubInteractionLimitResponse> restrictions = api().getRestrictionsForAuthenticatedUser();

        assertThat(restrictions).isEmpty();
    }

    @Test
    void setRestrictionsForAuthenticatedUser_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("interaction-limit-response.json")));

        final GitHubInteractionLimitResponse restrictions =
                api().setRestrictionsForAuthenticatedUser(InteractionsCatalogFixtures.interactionLimit());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(restrictions).isEqualTo(InteractionsCatalogFixtures.interactionLimitResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString(LIMITS_URI);
        });
    }

    @Test
    void removeRestrictionsForAuthenticatedUser_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeRestrictionsForAuthenticatedUser();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(LIMITS_URI);
        });
    }

    @Test
    void setRestrictionsForAuthenticatedUser_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsUserApi userApi = new GitHubInteractionsUserApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> userApi.setRestrictionsForAuthenticatedUser(null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    private GitHubInteractionsUserApi api() {
        return new GitHubInteractionsUserApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubInteractionsUserApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}

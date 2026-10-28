package io.github.piotr_janczukowicz.github.api.credentials;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.accepted;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.credentialsApi;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCredentialsApiIntegrationTest {

    private static final String REVOKE_PATH = "/credentials/revoke";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void revoke_shouldComplete_whenResponseIsAccepted() throws Exception {
        HTTP.stub(POST, REVOKE_PATH, accepted());
        final GitHubRevokePayload payload = CredentialsCatalogFixtures.revokePayload();

        try (final GitHubApiWireMockSupport.CredentialsApiClient api = credentialsApi(HTTP)) {
            api.api().revoke(payload);
        }

        HTTP.verifyRequested(POST, REVOKE_PATH);
        HTTP.verifyHeader(POST, REVOKE_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, REVOKE_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}

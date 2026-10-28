package io.github.piotr_janczukowicz.github.api.codesofconduct;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCodeOfConductApiTest {

    private static final String KEY = "contributor_covenant";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeOfConductApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getAllCodesOfConduct_shouldReturnCodesOfConduct_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("code-of-conduct.json") + "]"));

        final List<GitHubCodeOfConduct> codes = api().getAllCodesOfConduct();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(codes).containsExactly(RepositoriesCatalogFixtures.contributorCovenantWithBody());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/codes_of_conduct");
        });
    }

    @Test
    void getConductCode_shouldReturnCodeOfConduct_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("code-of-conduct.json")));

        final Optional<GitHubCodeOfConduct> code = api().getConductCode(KEY);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(code).contains(RepositoriesCatalogFixtures.contributorCovenantWithBody());
            softly.assertThat(requestUri).hasToString("https://api.github.com/codes_of_conduct/contributor_covenant");
        });
    }

    @Test
    void getConductCode_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubCodeOfConduct> code = api().getConductCode(KEY);

        assertThat(code).isEmpty();
    }

    @Test
    void getConductCode_shouldThrow_whenKeyBlank() {
        final GitHubCodeOfConductApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getConductCode(" "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    private GitHubCodeOfConductApi api() {
        return new GitHubCodeOfConductApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCodeOfConductApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}

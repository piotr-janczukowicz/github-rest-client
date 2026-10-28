package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class GitHubRepositoriesAutolinksApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int AUTOLINK_ID = 3;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoriesAutolinksApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listAutolinks_shouldReturnAutolinks_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("autolink.json") + "]"));

        final List<GitHubAutolink> autolinks = api().listAutolinks(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(autolinks).containsExactly(RepositoriesCatalogFixtures.sampleAutolink());
            softly.assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/Hello-World/autolinks");
        });
    }

    @Test
    void createAutolink_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("autolink.json")));
        final GitHubCreateAutolinkPayload payload = RepositoriesCatalogFixtures.createAutolinkPayload();

        final GitHubAutolink autolink = api().createAutolink(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(autolink).isEqualTo(RepositoriesCatalogFixtures.sampleAutolink());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/autolinks");
        });
    }

    @Test
    void getAutolink_shouldReturnAutolink_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("autolink.json")));

        final Optional<GitHubAutolink> autolink = api().getAutolink(OWNER, REPO, AUTOLINK_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(autolink).contains(RepositoriesCatalogFixtures.sampleAutolink());
            softly.assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/Hello-World/autolinks/3");
        });
    }

    @Test
    void getAutolink_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubAutolink> autolink = api().getAutolink(OWNER, REPO, AUTOLINK_ID);

        assertThat(autolink).isEmpty();
    }

    @Test
    void deleteAutolink_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteAutolink(OWNER, REPO, AUTOLINK_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/autolinks/3");
        });
    }

    @Test
    void getAutolink_shouldPutLongIdInPath_whenIdExceedsIntRange() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("autolink.json")));

        api().getAutolink(OWNER, REPO, 3_000_000_000L);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/Hello-World/autolinks/3000000000");
    }

    private GitHubRepositoriesAutolinksApi api() {
        return new GitHubRepositoriesAutolinksApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRepositoriesAutolinksApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}

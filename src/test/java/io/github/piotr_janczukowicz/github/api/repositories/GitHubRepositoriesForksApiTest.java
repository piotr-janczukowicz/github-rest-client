package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubRepositoriesForksApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoriesForksApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listForks_shouldReturnForks_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        final GitHubPage<GitHubMinimalRepository> page = api().listForks(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.helloWorldMinimalRepository());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/forks?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void createFork_shouldPostPayload_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), json("full-repository.json")));

        api().createFork(OWNER, REPO, RepositoriesCatalogFixtures.createForkPayload());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/forks");
    }

    @Test
    void createFork_shouldPostWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), json("full-repository.json")));

        api().createFork(OWNER, REPO, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void listForks_shouldUseDefaultFilter_whenRequestOverload() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        api().listForks(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "10");
    }

    @Test
    void listForks_shouldSendSortQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        api().listForks(
                        OWNER,
                        REPO,
                        GitHubListForksFilterBuilder.builder()
                                .sort(GitHubRepoForksSort.NEWEST)
                                .build(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("sort", "newest");
    }

    private GitHubRepositoriesForksApi api() {
        return new GitHubRepositoriesForksApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRepositoriesForksApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}

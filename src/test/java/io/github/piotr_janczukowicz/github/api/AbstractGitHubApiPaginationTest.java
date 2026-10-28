package io.github.piotr_janczukowicz.github.api;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.ok;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.users.GitHubAttestation;
import io.github.piotr_janczukowicz.github.api.users.GitHubAttestations;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubPageBody;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpResponses;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class AbstractGitHubApiPaginationTest {

    private static final JsonType<List<GitHubAttestation>> ATTESTATIONS_TYPE =
            JsonTypes.listOf(GitHubAttestation.class);

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void wrapperList_shouldExposeTotalCount_whenExtractorProvided() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("list-attestations.json")));

        final GitHubPage<GitHubAttestation> page;
        try (final GitHubClient client = TestClients.client(transport)) {
            page = new PaginationApi(TestClients.caller(client))
                    .listWithCount("/attestations", GitHubPageRequest.byCursor());
        }

        assertThat(page.totalCount()).isEqualTo(9);
        assertThat(page.items()).isNotEmpty();
    }

    @Test
    void wrapperList_shouldThrow_whenItemsExtractorNull() {
        final Throwable thrown;
        try (final GitHubClient client = TestClients.client(transport)) {
            thrown = catchThrowable(() -> new PaginationApi(TestClients.caller(client)).listWithNullItems());
        }

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("items");
    }

    @Test
    void wrapperList_shouldReturnEmptyItems_whenArrayNull() throws Exception {
        when(transport.execute(any())).thenReturn(ok("{\"attestations\":null}"));

        final GitHubPage<GitHubAttestation> page;
        try (final GitHubClient client = TestClients.client(transport)) {
            page = new PaginationApi(TestClients.caller(client))
                    .listWithCount("/attestations", GitHubPageRequest.byCursor());
        }

        assertThat(page.items()).isEmpty();
        assertThat(page.totalCount()).isEqualTo(9);
    }

    @Test
    void wrapperList_shouldWalkToPageTwo_whenPageKindAndNextLink() throws Exception {
        when(transport.execute(any()))
                .thenReturn(linkedResponse(
                        "<https://api.github.com/attestations?page=2&per_page=1>; rel=\"next\"",
                        "{\"attestations\":[{\"repository_id\":1}]}"))
                .thenReturn(ok("{\"attestations\":[{\"repository_id\":2}]}"));

        final GitHubPage<GitHubAttestation> second;
        try (final GitHubClient client = TestClients.client(transport)) {
            second = new PaginationApi(TestClients.caller(client))
                    .listPaged("/attestations", GitHubPageNumberRequest.of(1, 1))
                    .next()
                    .orElseThrow();
        }

        assertThat(second.items()).extracting(GitHubAttestation::repositoryId).containsExactly(2L);
    }

    @Test
    void wrapperList_shouldOmitTotalCount_whenExtractorNull() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("list-attestations.json")));

        final GitHubPage<GitHubAttestation> page;
        try (final GitHubClient client = TestClients.client(transport)) {
            page = new PaginationApi(TestClients.caller(client)).listWithoutCount();
        }

        assertThat(page.totalCount()).isNull();
        assertThat(page.items()).isNotEmpty();
    }

    @Test
    void pageList_shouldRejectExplicitPerPage_whenAboveMaxPerPage() {
        final Throwable thrown;
        try (final GitHubClient client = TestClients.client(transport)) {
            thrown = catchThrowable(() -> new PaginationApi(TestClients.caller(client))
                    .listPages(
                            GitHubPageNumberRequest.of(1, 31),
                            GitHubListSpec.<GitHubAttestation>builder()
                                    .allowedKinds(GitHubPageRequestKindValidators.PAGE)
                                    .decoder(json -> response -> GitHubPageBody.of(List.of()))
                                    .maxPerPage(30)
                                    .build()));
        }

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessage("perPage must be at most 30");
    }

    @Test
    void cursorList_shouldOmitPerPage_whenWithoutPerPageFollowsLink() throws Exception {
        when(transport.execute(any()))
                .thenReturn(linkedResponse("<https://api.github.com/items?after=next>; rel=\"next\"", "[]"))
                .thenReturn(ok("[]"));

        try (final GitHubClient client = TestClients.client(transport)) {
            new PaginationApi(TestClients.caller(client))
                    .listCursors(
                            GitHubPageRequest.byCursor(),
                            GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, ATTESTATIONS_TYPE)
                                    .withoutPerPage()
                                    .withoutPerPage()
                                    .withoutPerPage())
                    .next();
        }

        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport, times(2)).execute(captor.capture());

        assertThat(captor.getAllValues())
                .extracting(request -> request.uri().toString())
                .containsExactly("https://api.github.com/items", "https://api.github.com/items?after=next");
    }

    private static HttpResponse linkedResponse(final String link, final String body) {
        return HttpResponses.of(
                HttpStatus.OK.code(), Map.of(LINK, List.of(link)), HttpBody.of(body.getBytes(StandardCharsets.UTF_8)));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubAttestation.class, fileName);
    }

    private static final class PaginationApi extends GitHubResourceApi {

        private PaginationApi(final GitHubCaller caller) {
            super(caller);
        }

        private GitHubPage<GitHubAttestation> listWithCount(final String path, final GitHubPageRequest request)
                throws IOException {
            return list(
                    PathTemplate.of(path),
                    Map.of(),
                    request,
                    GitHubListSpec.wrapper(
                            GitHubPageRequestKindValidators.CURSOR,
                            GitHubAttestations.class,
                            GitHubAttestations::attestations,
                            unused -> 9));
        }

        private GitHubPage<GitHubAttestation> listWithNullItems() throws IOException {
            return list(
                    PathTemplate.of("/attestations"),
                    Map.of(),
                    GitHubPageRequest.byCursor(),
                    GitHubListSpec.wrapper(
                            GitHubPageRequestKindValidators.CURSOR, GitHubAttestations.class, null, unused -> 1));
        }

        private GitHubPage<GitHubAttestation> listWithoutCount() throws IOException {
            return list(
                    PathTemplate.of("/attestations"),
                    Map.of(),
                    GitHubPageRequest.byCursor(),
                    GitHubListSpec.wrapper(
                            GitHubPageRequestKindValidators.CURSOR,
                            GitHubAttestations.class,
                            GitHubAttestations::attestations,
                            null));
        }

        private GitHubPage<GitHubAttestation> listPaged(final String path, final GitHubPageRequest request)
                throws IOException {
            return list(
                    PathTemplate.of(path),
                    Map.of(),
                    request,
                    GitHubListSpec.wrapper(
                            GitHubPageRequestKindValidators.PAGE,
                            GitHubAttestations.class,
                            GitHubAttestations::attestations,
                            unused -> 2));
        }

        private GitHubPage<GitHubAttestation> listPages(
                final GitHubPageRequest request, final GitHubListSpec<GitHubAttestation> spec) throws IOException {
            return list(PathTemplate.of("/items"), Map.of(), request, spec);
        }

        private GitHubPage<GitHubAttestation> listCursors(
                final GitHubPageRequest request, final GitHubListSpec<GitHubAttestation> spec) throws IOException {
            return list(PathTemplate.of("/items"), Map.of(), request, spec);
        }
    }
}

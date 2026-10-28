package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
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
class GitHubActionsConcurrencyGroupsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String GROUP = "deploy";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsConcurrencyGroupsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listConcurrencyGroupsForRepository_shouldReturnGroups_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("concurrency-group-list.json")));

        final GitHubPage<GitHubConcurrencyGroupListEntry> page = api().listConcurrencyGroupsForRepository(OWNER, REPO);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.concurrencyGroupList().concurrencyGroups());
    }

    @Test
    void listConcurrencyGroupsForRepository_shouldSendCursor_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("concurrency-group-list.json")));

        api().listConcurrencyGroupsForRepository(OWNER, REPO, GitHubCursorRequest.after("abc"));

        assertThat(captureSingleRequest().uri()).hasParameter("after", "abc");
    }

    @Test
    void getConcurrencyGroupForRepository_shouldReturnGroup_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("concurrency-group.json")));

        final Optional<GitHubConcurrencyGroup> group = api().getConcurrencyGroupForRepository(OWNER, REPO, GROUP);

        assertThat(group).contains(ActionsCatalogFixtures.concurrencyGroup());
    }

    @Test
    void getConcurrencyGroupForRepository_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("concurrency-group.json")));

        api().getConcurrencyGroupForRepository(
                        OWNER,
                        REPO,
                        GROUP,
                        GitHubGetConcurrencyGroupForRepositoryFilterBuilder.builder()
                                .aheadOfRun(1)
                                .build());

        assertThat(captureSingleRequest().uri()).hasParameter("ahead_of_run", "1");
    }

    @Test
    void listConcurrencyGroupsForWorkflowRun_shouldReturnGroups_whenRequestOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("concurrency-group-run-list.json")));

        final GitHubPage<GitHubConcurrencyGroupRunListEntry> page =
                api().listConcurrencyGroupsForWorkflowRun(OWNER, REPO, 1L);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.concurrencyGroupRunList().concurrencyGroups());
    }

    @Test
    void listConcurrencyGroupsForWorkflowRun_shouldSendCursor_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("concurrency-group-run-list.json")));

        api().listConcurrencyGroupsForWorkflowRun(OWNER, REPO, 1L, GitHubCursorRequest.before("abc"));

        assertThat(captureSingleRequest().uri()).hasParameter("before", "abc");
    }

    private GitHubActionsConcurrencyGroupsApi api() {
        return new GitHubActionsConcurrencyGroupsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsConcurrencyGroupsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}

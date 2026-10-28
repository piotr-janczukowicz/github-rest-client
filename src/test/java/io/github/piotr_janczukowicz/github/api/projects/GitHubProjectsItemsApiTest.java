package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
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
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubProjectsItemsApiTest {

    private static final String ORG = "octocat";
    private static final String USERNAME = "monalisa";
    private static final long PROJECT_NUMBER = 1L;
    private static final long ITEM_ID = 3L;
    private static final long VIEW_NUMBER = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void listForOrg_shouldReturnItems_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));

        final GitHubPage<GitHubProjectItem> page = api().listForOrg(ORG, PROJECT_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ProjectsCatalogFixtures.itemWithContent());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void addForOrg_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("item-simple.json")));
        final GitHubAddProjectItemPayload payload =
                GitHubAddProjectItemPayload.byId(GitHubProjectItemContentType.ISSUE, 42L);

        final GitHubProjectItemSimple item = api().addForOrg(ORG, PROJECT_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(item).isEqualTo(ProjectsCatalogFixtures.itemSimpleDraft());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void getForOrg_shouldReturnItem_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("item-with-content.json")));

        final Optional<GitHubProjectItem> item = api().getForOrg(ORG, PROJECT_NUMBER, ITEM_ID);

        assertThat(item).contains(ProjectsCatalogFixtures.itemWithContent());
    }

    @Test
    void getForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubProjectItem> item = api().getForOrg(ORG, PROJECT_NUMBER, ITEM_ID);

        assertThat(item).isEmpty();
    }

    @Test
    void getForOrg_shouldSendFieldsQuery_whenFilterHasFields() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("item-with-content.json")));
        final GitHubListProjectViewItemsFilter filter = GitHubListProjectViewItemsFilterBuilder.builder()
                .fields(List.of("123", "456"))
                .build();

        final Optional<GitHubProjectItem> item = api().getForOrg(ORG, PROJECT_NUMBER, ITEM_ID, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(item).contains(ProjectsCatalogFixtures.itemWithContent());
            softly.assertThat(requestUri.getQuery()).contains("fields=123,456").doesNotContain("q=");
        });
    }

    @Test
    void updateForOrg_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("item-with-content.json")));
        final GitHubUpdateProjectItemPayload payload = ProjectsCatalogFixtures.updateProjectItemPayload();

        final GitHubProjectItem item = api().updateForOrg(ORG, PROJECT_NUMBER, ITEM_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(item).isEqualTo(ProjectsCatalogFixtures.itemWithContent());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
        });
    }

    @Test
    void deleteForOrg_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteForOrg(ORG, PROJECT_NUMBER, ITEM_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octocat/projectsV2/1/items/3");
        });
    }

    @Test
    void listViewItemsForOrg_shouldOmitQ_whenFilterHasFieldsOnly() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));
        final GitHubListProjectViewItemsFilter filter = GitHubListProjectViewItemsFilterBuilder.builder()
                .fields(List.of("123"))
                .build();

        api().listViewItemsForOrg(ORG, PROJECT_NUMBER, VIEW_NUMBER, filter, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString()).contains("fields=123").doesNotContain("q=");
    }

    @Test
    void listForUser_shouldUseUsersPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));

        api().listForUser(USERNAME, PROJECT_NUMBER);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/users/monalisa/projectsV2/1/items?" + TestClients.perPageQuery());
    }

    @Test
    void listForOrg_shouldThrow_whenPageRequestInvalid() {
        final GitHubProjectsItemsApi api = api();
        final GitHubPageRequest page = GitHubPageRequest.byPage();

        final Throwable thrown = catchThrowable(() -> api.listForOrg(ORG, PROJECT_NUMBER, page));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addForOrg_shouldThrow_whenOrgBlank() {
        final GitHubAddProjectItemPayload payload =
                GitHubAddProjectItemPayload.byId(GitHubProjectItemContentType.ISSUE, 42L);

        final GitHubProjectsItemsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.addForOrg(" ", PROJECT_NUMBER, payload));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
    }

    @Test
    void listForOrg_shouldSendQuery_whenRequestPresent() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));

        api().listForOrg(ORG, PROJECT_NUMBER, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/orgs/octocat/projectsV2/1/items?" + TestClients.perPageQuery());
    }

    @Test
    void listForOrg_shouldSendQuery_whenFilterPresent() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));
        final GitHubListProjectItemsFilter filter =
                GitHubListProjectItemsFilterBuilder.builder().q("is:open").build();

        api().listForOrg(ORG, PROJECT_NUMBER, filter, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("q=is:open");
    }

    @Test
    void listViewItemsForOrg_shouldReturnItems_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));

        final GitHubPage<GitHubProjectItem> page = api().listViewItemsForOrg(ORG, PROJECT_NUMBER, VIEW_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ProjectsCatalogFixtures.itemWithContent());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listViewItemsForOrg_shouldSendQuery_whenRequestPresent() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));

        api().listViewItemsForOrg(ORG, PROJECT_NUMBER, VIEW_NUMBER, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString()).contains("/views/1/items");
    }

    @Test
    void listForUser_shouldSendQuery_whenRequestPresent() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));

        api().listForUser(USERNAME, PROJECT_NUMBER, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/users/monalisa/projectsV2/1/items?" + TestClients.perPageQuery());
    }

    @Test
    void listForUser_shouldSendQuery_whenFilterPresent() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));
        final GitHubListProjectItemsFilter filter =
                GitHubListProjectItemsFilterBuilder.builder().q("is:open").build();

        api().listForUser(USERNAME, PROJECT_NUMBER, filter, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("q=is:open");
    }

    @Test
    void addForUser_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("item-simple.json")));
        final GitHubAddProjectItemPayload payload =
                GitHubAddProjectItemPayload.byId(GitHubProjectItemContentType.ISSUE, 42L);

        final GitHubProjectItemSimple item = api().addForUser(USERNAME, PROJECT_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(item).isEqualTo(ProjectsCatalogFixtures.itemSimpleDraft());
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users/monalisa/projectsV2/1/items");
        });
    }

    @Test
    void getForUser_shouldReturnItem_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("item-with-content.json")));

        final Optional<GitHubProjectItem> item = api().getForUser(USERNAME, PROJECT_NUMBER, ITEM_ID);

        assertThat(item).contains(ProjectsCatalogFixtures.itemWithContent());
    }

    @Test
    void getForUser_shouldSendFieldsQuery_whenFilterHasFields() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("item-with-content.json")));
        final GitHubListProjectViewItemsFilter filter = GitHubListProjectViewItemsFilterBuilder.builder()
                .fields(List.of("123"))
                .build();

        final Optional<GitHubProjectItem> item = api().getForUser(USERNAME, PROJECT_NUMBER, ITEM_ID, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(item).contains(ProjectsCatalogFixtures.itemWithContent());
            softly.assertThat(requestUri.getQuery()).contains("fields=123");
        });
    }

    @Test
    void updateForUser_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("item-with-content.json")));
        final GitHubUpdateProjectItemPayload payload = ProjectsCatalogFixtures.updateProjectItemPayload();

        final GitHubProjectItem item = api().updateForUser(USERNAME, PROJECT_NUMBER, ITEM_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(item).isEqualTo(ProjectsCatalogFixtures.itemWithContent());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
        });
    }

    @Test
    void deleteForUser_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteForUser(USERNAME, PROJECT_NUMBER, ITEM_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void listViewItemsForUser_shouldReturnItems_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));

        final GitHubPage<GitHubProjectItem> page = api().listViewItemsForUser(USERNAME, PROJECT_NUMBER, VIEW_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ProjectsCatalogFixtures.itemWithContent());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listViewItemsForUser_shouldSendQuery_whenRequestPresent() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));

        api().listViewItemsForUser(USERNAME, PROJECT_NUMBER, VIEW_NUMBER, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString()).contains("/users/monalisa/projectsV2/1/views/1/items");
    }

    @Test
    void listViewItemsForUser_shouldSendFields_whenFilterPresent() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("item-with-content.json") + "]"));
        final GitHubListProjectViewItemsFilter filter = GitHubListProjectViewItemsFilterBuilder.builder()
                .fields(List.of("123"))
                .build();

        api().listViewItemsForUser(USERNAME, PROJECT_NUMBER, VIEW_NUMBER, filter, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("fields=123");
    }

    private GitHubProjectsItemsApi api() {
        return new GitHubProjectsItemsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubProjectsItemsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}

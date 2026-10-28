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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubProjectsFieldsApiTest {

    private static final String ORG = "octocat";
    private static final String USERNAME = "monalisa";
    private static final long PROJECT_NUMBER = 1L;
    private static final long FIELD_ID = 123L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void listForOrg_shouldReturnFields_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("field-text.json") + "]"));

        final GitHubPage<GitHubProjectField> page = api().listForOrg(ORG, PROJECT_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ProjectsCatalogFixtures.textField());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void addForOrg_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("field-text.json")));
        final GitHubAddProjectFieldPayload payload =
                GitHubAddProjectFieldPayload.basic("Team notes", GitHubProjectFieldDataType.TEXT);

        final GitHubProjectField field = api().addForOrg(ORG, PROJECT_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(field).isEqualTo(ProjectsCatalogFixtures.textField());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void getForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubProjectField> field = api().getForOrg(ORG, PROJECT_NUMBER, FIELD_ID);

        assertThat(field).isEmpty();
    }

    @Test
    void getForOrg_shouldReturnField_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("field-text.json")));

        final Optional<GitHubProjectField> field = api().getForOrg(ORG, PROJECT_NUMBER, FIELD_ID);

        assertThat(field).contains(ProjectsCatalogFixtures.textField());
    }

    @Test
    void addForUser_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("field-text.json")));
        final GitHubAddFieldForUserPayload payload =
                GitHubAddFieldForUserPayload.basic("Team notes", GitHubProjectFieldDataType.TEXT);

        final GitHubProjectField field = api().addForUser(USERNAME, PROJECT_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(field).isEqualTo(ProjectsCatalogFixtures.textField());
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users/monalisa/projectsV2/1/fields");
        });
    }

    @Test
    void getForUser_shouldReturnField_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("field-text.json")));

        final Optional<GitHubProjectField> field = api().getForUser(USERNAME, PROJECT_NUMBER, FIELD_ID);

        assertThat(field).contains(ProjectsCatalogFixtures.textField());
    }

    @Test
    void listForUser_shouldUseUsersPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("field-text.json") + "]"));

        api().listForUser(USERNAME, PROJECT_NUMBER);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/users/monalisa/projectsV2/1/fields?" + TestClients.perPageQuery());
    }

    @Test
    void listForUser_shouldUseUsersPath_whenRequestPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("field-text.json") + "]"));

        api().listForUser(USERNAME, PROJECT_NUMBER, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/users/monalisa/projectsV2/1/fields?" + TestClients.perPageQuery());
    }

    @Test
    void listForOrg_shouldThrow_whenPageRequestInvalid() {
        final GitHubProjectsFieldsApi api = api();
        final GitHubPageRequest page = GitHubPageRequest.byPage();

        final Throwable thrown = catchThrowable(() -> api.listForOrg(ORG, PROJECT_NUMBER, page));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    private GitHubProjectsFieldsApi api() {
        return new GitHubProjectsFieldsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubProjectsFieldsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}

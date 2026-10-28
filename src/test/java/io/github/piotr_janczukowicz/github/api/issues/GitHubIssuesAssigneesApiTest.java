package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubIssuesAssigneesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ASSIGNEE = "hubot";
    private static final int ISSUE_NUMBER = 1309;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssuesAssigneesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listAssignees_shouldReturnUsers_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + userJson() + "]"));

        final GitHubPage<GitHubSimpleUser> page = api().listAssignees(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(UsersCatalogFixtures.octocat());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/assignees");
        });
    }

    @Test
    void checkUserCanBeAssigned_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean canAssign = api().checkUserCanBeAssigned(OWNER, REPO, ASSIGNEE);

        assertThat(canAssign).isTrue();
    }

    @Test
    void checkUserCanBeAssigned_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean canAssign = api().checkUserCanBeAssigned(OWNER, REPO, ASSIGNEE);

        assertThat(canAssign).isFalse();
    }

    @Test
    void addAssignees_shouldPostPayload_whenAssigneesProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), issueJson()));
        final GitHubAddAssigneesPayload payload = GitHubAddAssigneesPayloadBuilder.builder(
                        IssuesCatalogFixtures.addAssigneesPayload())
                .assignees(List.of(GitHubAddAssigneesAssignee.ofLogin(ASSIGNEE)))
                .build();

        api().addAssignees(OWNER, REPO, ISSUE_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/assignees");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("hubot");
        });
    }

    @Test
    void addAssignees_shouldPostWithoutBody_whenNoPayloadOverloadCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), issueJson()));

        api().addAssignees(OWNER, REPO, ISSUE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void addAssignees_shouldPostWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), issueJson()));

        api().addAssignees(OWNER, REPO, ISSUE_NUMBER, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void removeAssignees_shouldDeletePayload_whenAssigneesProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueJson()));
        final GitHubRemoveAssigneesPayload payload = IssuesCatalogFixtures.removeAssigneesPayload();

        api().removeAssignees(OWNER, REPO, ISSUE_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/assignees");
        });
    }

    @Test
    void removeAssignees_shouldDeleteWithoutBody_whenNoPayloadOverloadCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueJson()));

        api().removeAssignees(OWNER, REPO, ISSUE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void removeAssignees_shouldDeleteWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueJson()));

        api().removeAssignees(OWNER, REPO, ISSUE_NUMBER, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void checkUserCanBeAssignedToIssue_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean canAssign = api().checkUserCanBeAssignedToIssue(OWNER, REPO, ISSUE_NUMBER, ASSIGNEE);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(canAssign).isTrue();
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/Hello-World/issues/1309/assignees/hubot");
        });
    }

    private GitHubIssuesAssigneesApi api() {
        return new GitHubIssuesAssigneesApi(TestClients.caller(transport));
    }

    private static String userJson() {
        return FileUtils.readString(GitHubIssuesAssigneesApiTest.class, "simple-user-octocat.json");
    }

    private static String issueJson() {
        return FileUtils.readString(GitHubIssueApiTest.class, "issue.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}

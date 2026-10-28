package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubIssuesLabelsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String NAME = "bug";
    private static final int ISSUE_NUMBER = 1309;
    private static final int MILESTONE_NUMBER = 1;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssuesLabelsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listLabelsOnIssue_shouldGetPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + labelJson() + "]"));

        final GitHubPage<GitHubLabel> page = api().listLabelsOnIssue(OWNER, REPO, ISSUE_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.label());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void addLabels_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + labelJson() + "]"));
        final GitHubAddLabelsPayload payload = IssuesCatalogFixtures.addLabelsPayload();

        api().addLabels(OWNER, REPO, ISSUE_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/labels");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("bug");
        });
    }

    @Test
    void addLabels_shouldPostWithoutBody_whenNoPayloadOverloadCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + labelJson() + "]"));

        api().addLabels(OWNER, REPO, ISSUE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void addLabels_shouldPostWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + labelJson() + "]"));

        api().addLabels(OWNER, REPO, ISSUE_NUMBER, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void setLabels_shouldPutPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + labelJson() + "]"));
        final GitHubSetLabelsPayload payload = IssuesCatalogFixtures.setLabelsPayload();

        api().setLabels(OWNER, REPO, ISSUE_NUMBER, payload);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void setLabels_shouldPutWithoutBody_whenNoPayloadOverloadCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + labelJson() + "]"));

        api().setLabels(OWNER, REPO, ISSUE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void setLabels_shouldPutWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + labelJson() + "]"));

        api().setLabels(OWNER, REPO, ISSUE_NUMBER, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void removeAllLabels_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeAllLabels(OWNER, REPO, ISSUE_NUMBER);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void removeLabel_shouldDeleteName_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + labelJson() + "]"));

        api().removeLabel(OWNER, REPO, ISSUE_NUMBER, NAME);

        assertThat(captureSingleRequest().uri().getPath())
                .isEqualTo("/repos/octocat/Hello-World/issues/1309/labels/bug");
    }

    @Test
    void listLabelsForRepo_shouldGetPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + labelJson() + "]"));

        api().listLabelsForRepo(OWNER, REPO);

        assertThat(captureSingleRequest().uri().getPath()).isEqualTo("/repos/octocat/Hello-World/labels");
    }

    @Test
    void createLabel_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), labelJson()));
        final GitHubCreateLabelPayload payload = IssuesCatalogFixtures.createLabelPayload();

        api().createLabel(OWNER, REPO, payload);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void getLabel_shouldReturnLabel_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), labelJson()));

        final Optional<GitHubLabel> label = api().getLabel(OWNER, REPO, NAME);

        assertThat(label).contains(IssuesCatalogFixtures.label());
    }

    @Test
    void updateLabel_shouldPatchPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), labelJson()));
        final GitHubUpdateLabelPayload payload = IssuesCatalogFixtures.updateLabelPayload();

        api().updateLabel(OWNER, REPO, NAME, payload);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.PATCH);
    }

    @Test
    void updateLabel_shouldPatchWithoutBody_whenNoPayloadOverloadCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), labelJson()));

        api().updateLabel(OWNER, REPO, NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void updateLabel_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), labelJson()));

        api().updateLabel(OWNER, REPO, NAME, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void deleteLabel_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteLabel(OWNER, REPO, NAME);

        assertThat(captureSingleRequest().uri().getPath()).isEqualTo("/repos/octocat/Hello-World/labels/bug");
    }

    @Test
    void listLabelsForMilestone_shouldGetPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + labelJson() + "]"));

        api().listLabelsForMilestone(OWNER, REPO, MILESTONE_NUMBER);

        assertThat(captureSingleRequest().uri().getPath()).isEqualTo("/repos/octocat/Hello-World/milestones/1/labels");
    }

    private GitHubIssuesLabelsApi api() {
        return new GitHubIssuesLabelsApi(TestClients.caller(transport));
    }

    private static String labelJson() {
        return FileUtils.readString(GitHubIssuesLabelsApiTest.class, "label.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}

package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestone;
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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubIssuesMilestonesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int MILESTONE_NUMBER = 1;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssuesMilestonesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listMilestones_shouldIncludeFilters_whenFilterSet() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + milestoneJson() + "]"));
        final GitHubListMilestonesFilter filter = GitHubListMilestonesFilterBuilder.builder()
                .state(GitHubListIssuesState.OPEN)
                .sort(GitHubListMilestonesSort.DUE_ON)
                .direction(GitHubDirection.ASC)
                .build();

        final GitHubPage<GitHubMilestone> page = api().listMilestones(OWNER, REPO, filter, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.milestone());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri().getQuery())
                    .contains("state=open")
                    .contains("sort=due_on")
                    .contains("direction=asc");
        });
    }

    @Test
    void listMilestones_shouldReturnMilestones_whenDefaultOverload() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + milestoneJson() + "]"));

        final GitHubPage<GitHubMilestone> page = api().listMilestones(OWNER, REPO);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.milestone());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listMilestones_shouldReturnMilestones_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + milestoneJson() + "]"));

        final GitHubPage<GitHubMilestone> page = api().listMilestones(OWNER, REPO, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.milestone());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void createMilestone_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), milestoneJson()));
        final GitHubCreateMilestonePayload payload = IssuesCatalogFixtures.createMilestonePayload();

        api().createMilestone(OWNER, REPO, payload);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void getMilestone_shouldReturnMilestone_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), milestoneJson()));

        final Optional<GitHubMilestone> milestone = api().getMilestone(OWNER, REPO, MILESTONE_NUMBER);

        assertThat(milestone).contains(IssuesCatalogFixtures.milestone());
    }

    @Test
    void updateMilestone_shouldPatch_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), milestoneJson()));
        final GitHubUpdateMilestonePayload payload = IssuesCatalogFixtures.updateMilestonePayload();

        api().updateMilestone(OWNER, REPO, MILESTONE_NUMBER, payload);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.PATCH);
    }

    @Test
    void updateMilestone_shouldPatchWithoutBody_whenNoPayloadOverloadCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), milestoneJson()));

        api().updateMilestone(OWNER, REPO, MILESTONE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void updateMilestone_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), milestoneJson()));

        api().updateMilestone(OWNER, REPO, MILESTONE_NUMBER, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void deleteMilestone_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteMilestone(OWNER, REPO, MILESTONE_NUMBER);

        assertThat(captureSingleRequest().uri().getPath()).isEqualTo("/repos/octocat/Hello-World/milestones/1");
    }

    private GitHubIssuesMilestonesApi api() {
        return new GitHubIssuesMilestonesApi(TestClients.caller(transport));
    }

    private static String milestoneJson() {
        return FileUtils.readString(GitHubIssuesMilestonesApiTest.class, "milestone.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}

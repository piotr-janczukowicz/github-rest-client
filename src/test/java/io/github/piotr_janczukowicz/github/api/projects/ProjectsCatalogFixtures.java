package io.github.piotr_janczukowicz.github.api.projects;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUserBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class ProjectsCatalogFixtures {

    private static final String OCTOCAT_API = "https://api.github.com/users/octocat";

    private static final Instant ARCHIVED_AT = Instant.parse("2022-04-29T12:00:00Z");

    private static final Instant PROJECT_CLOSED_DELETED_AT = Instant.parse("2022-04-28T12:00:00Z");

    private ProjectsCatalogFixtures() {}

    static GitHubSimpleUser octocat583231() {
        return GitHubSimpleUserBuilder.builder()
                .login("octocat")
                .id(583_231L)
                .nodeId("MDQ6VXNlcjU4MzIzMQ==")
                .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
                .gravatarId("")
                .url(OCTOCAT_API)
                .htmlUrl("https://github.com/octocat")
                .followersUrl(OCTOCAT_API + "/followers")
                .followingUrl(OCTOCAT_API + "/following{/other_user}")
                .gistsUrl(OCTOCAT_API + "/gists{/gist_id}")
                .starredUrl(OCTOCAT_API + "/starred{/owner}{/repo}")
                .subscriptionsUrl(OCTOCAT_API + "/subscriptions")
                .organizationsUrl(OCTOCAT_API + "/orgs")
                .reposUrl(OCTOCAT_API + "/repos")
                .eventsUrl(OCTOCAT_API + "/events{/privacy}")
                .receivedEventsUrl(OCTOCAT_API + "/received_events")
                .type("User")
                .siteAdmin(false)
                .name("monalisa octocat")
                .email("octocat@github.com")
                .starredAt("2020-11-02T16:12:33Z")
                .userViewType(GitHubUserViewType.PUBLIC)
                .build();
    }

    static GitHubProjectFormattedText formattedText(final String raw, final String html) {
        return new GitHubProjectFormattedText(raw, html);
    }

    static GitHubProject project() {
        return GitHubProjectBuilder.builder()
                .id(1L)
                .nodeId("PVT_kwDOABcd")
                .owner(octocat583231())
                .creator(octocat583231())
                .title("Roadmap")
                .description("Product roadmap")
                .isPublic(true)
                .closedAt(OptionalNull.of(PROJECT_CLOSED_DELETED_AT))
                .createdAt(Instant.parse("2022-04-28T12:00:00Z"))
                .updatedAt(Instant.parse("2022-04-28T12:00:00Z"))
                .number(1)
                .shortDescription("Roadmap project")
                .deletedAt(OptionalNull.of(PROJECT_CLOSED_DELETED_AT))
                .deletedBy(OptionalNull.of(octocat583231()))
                .state(GitHubProjectState.OPEN)
                .latestStatusUpdate(statusUpdate())
                .isTemplate(false)
                .build();
    }

    static GitHubProjectView projectView() {
        return GitHubProjectViewBuilder.builder()
                .id(1)
                .number(1)
                .name("All Issues")
                .layout(GitHubProjectViewLayout.TABLE)
                .nodeId("PVTV_lADOABcd")
                .projectUrl("https://api.github.com/orgs/octocat/projectsV2/1")
                .htmlUrl("https://github.com/orgs/octocat/projects/1/views/1")
                .creator(octocat583231())
                .createdAt(Instant.parse("2022-04-28T12:00:00Z"))
                .updatedAt(Instant.parse("2022-04-28T12:00:00Z"))
                .filter("is:issue")
                .visibleFields(List.of(123L, 456L, 789L))
                .sortBy(List.of(
                        new GitHubProjectViewSort(123, GitHubProjectViewSortDirection.ASC),
                        new GitHubProjectViewSort(456, GitHubProjectViewSortDirection.DESC)))
                .groupBy(List.of(123L))
                .verticalGroupBy(List.of(456L))
                .build();
    }

    static GitHubProjectDraftIssue draftIssue() {
        return GitHubProjectDraftIssueBuilder.builder()
                .id(42L)
                .nodeId("DI_kwDOABcd")
                .title("Draft task")
                .body("Draft body")
                .user(octocat583231())
                .createdAt(Instant.parse("2022-04-28T12:00:00Z"))
                .updatedAt(Instant.parse("2022-04-28T12:00:00Z"))
                .build();
    }

    static GitHubProjectField textField() {
        return GitHubProjectFieldBuilder.builder()
                .id(123)
                .issueFieldId(10_042L)
                .nodeId("PVTF_lADOABcd")
                .projectUrl("https://api.github.com/orgs/octocat/projectsV2/1")
                .name("Team notes")
                .dataType(GitHubProjectFieldDataType.TEXT)
                .options(List.of(singleSelectOption()))
                .configuration(new GitHubProjectFieldConfiguration(1, 14, List.of(iterationSetting())))
                .createdAt(Instant.parse("2022-04-28T12:00:00Z"))
                .updatedAt(Instant.parse("2022-04-28T12:00:00Z"))
                .build();
    }

    public static GitHubProjectItemSimple itemSimpleDraft() {
        return GitHubProjectItemSimpleBuilder.builder()
                .id(3L)
                .nodeId("PVTI_lADOABcd")
                .contentType(GitHubProjectItemContentType.DRAFT_ISSUE)
                .content(new GitHubProjectItemContent.DraftIssue(GitHubProjectDraftIssueBuilder.builder()
                        .id(42L)
                        .nodeId("DI_kwDOABcd")
                        .title("Draft task")
                        .body("Draft body")
                        .user(octocat583231())
                        .createdAt(Instant.parse("2022-04-28T12:00:00Z"))
                        .updatedAt(Instant.parse("2022-04-28T12:00:00Z"))
                        .build()))
                .creator(octocat583231())
                .createdAt(Instant.parse("2022-04-28T12:00:00Z"))
                .updatedAt(Instant.parse("2022-04-28T12:00:00Z"))
                .archivedAt(OptionalNull.of(ARCHIVED_AT))
                .projectUrl("https://api.github.com/orgs/octocat/projectsV2/1")
                .itemUrl("https://api.github.com/orgs/octocat/projectsV2/1/items/3")
                .build();
    }

    static GitHubProjectItem itemWithContent() {
        return GitHubProjectItemBuilder.builder()
                .id(3L)
                .nodeId("PVTI_lADOABcd")
                .projectUrl("https://api.github.com/users/monalisa/2/projectsV2/3")
                .contentType(GitHubProjectItemContentType.ISSUE)
                .content(Map.of("title", "Found a bug", "number", 1309))
                .creator(octocat583231())
                .createdAt(Instant.parse("2022-04-28T12:00:00Z"))
                .updatedAt(Instant.parse("2022-04-28T12:00:00Z"))
                .archivedAt(OptionalNull.of(ARCHIVED_AT))
                .itemUrl("https://api.github.com/users/monalisa/2/projectsV2/items/3")
                .fields(List.of(Map.of("id", 123, "value", "Updated text value")))
                .build();
    }

    static GitHubProjectStatusUpdate statusUpdate() {
        return GitHubProjectStatusUpdateBuilder.builder()
                .id(9L)
                .nodeId("PVTSU_node")
                .projectNodeId("PVT_project")
                .creator(octocat583231())
                .createdAt(Instant.parse("2022-04-28T12:00:00Z"))
                .updatedAt(Instant.parse("2022-04-29T12:00:00Z"))
                .status(GitHubProjectStatus.ON_TRACK)
                .startDate("2022-04-01")
                .targetDate("2022-04-30")
                .body("Shipped")
                .build();
    }

    static GitHubProjectIterationSetting iterationSetting() {
        return GitHubProjectIterationSettingBuilder.builder()
                .id("iter-1")
                .startDate("2022-04-28")
                .duration(14)
                .title(formattedText("Sprint 1", "<p>Sprint 1</p>"))
                .completed(true)
                .build();
    }

    static GitHubProjectSingleSelectOption singleSelectOption() {
        return GitHubProjectSingleSelectOptionBuilder.builder()
                .id("opt-1")
                .name(formattedText("Todo", "<p>Todo</p>"))
                .description(formattedText("desc", "<p>desc</p>"))
                .color("BLUE")
                .build();
    }

    static GitHubUpdateProjectItemFieldPayload updateProjectItemField() {
        return new GitHubUpdateProjectItemFieldPayload(123, OptionalNull.of("hello"));
    }

    static GitHubCreateDraftItemPayload createDraftItemPayload() {
        return new GitHubCreateDraftItemPayload("Draft task", "Body");
    }

    static GitHubCreateProjectViewPayload createProjectViewPayload() {
        return new GitHubCreateProjectViewPayload(
                "All Issues", GitHubProjectViewLayout.TABLE, null, null, null, null, null);
    }

    static GitHubUpdateProjectItemPayload updateProjectItemPayload() {
        return new GitHubUpdateProjectItemPayload(List.of(updateProjectItemField()));
    }
}

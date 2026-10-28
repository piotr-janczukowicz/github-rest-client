package io.github.piotr_janczukowicz.github.api.classroom;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;

/**
 * GitHub Classroom REST resource for API version {@code 2026-03-10}.
 *
 * <p>The Classroom API was removed from the GitHub REST API on {@code 2026-08-28} (see the <a
 * href="https://gh.io/classroom-sunset">sunset notice</a>). Every operation now answers {@code 410 Gone}, so each
 * method throws {@link UnsupportedOperationException} without performing an HTTP call.
 *
 * @deprecated The Classroom operations were closed and removed on {@code 2026-08-28}; they always return HTTP 410.
 */
@Deprecated(since = "2026-05-22")
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubClassroomApi extends GitHubResourceApi {

    private static final String DEPRECATED_SINCE = "2026-05-22";

    private static final String REMOVED_MESSAGE = "The GitHub Classroom API has been removed from the GitHub REST API"
            + " (since 2026-08-28, see the sunset notice https://gh.io/classroom-sunset)";

    /**
     * GitHub Classroom resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubClassroomApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get an assignment (GET {@code /assignments/{assignment_id}}).
     *
     * @param assignmentId assignment id
     * @throws UnsupportedOperationException always, the operation was removed from the GitHub REST API
     * @deprecated This operation was closed and removed on {@code 2026-08-28} and always returns HTTP 410. Deprecation
     *     date {@code 2026-05-22}; removal date {@code 2026-08-28}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void getAssignment(final int assignmentId) {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        throw new UnsupportedOperationException(REMOVED_MESSAGE);
    }

    /**
     * List accepted assignments for an assignment (GET {@code /assignments/{assignment_id}/accepted_assignments}).
     *
     * @param assignmentId assignment id
     * @throws UnsupportedOperationException always, the operation was removed from the GitHub REST API
     * @deprecated This operation was closed and removed on {@code 2026-08-28} and always returns HTTP 410. Deprecation
     *     date {@code 2026-05-22}; removal date {@code 2026-08-28}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void listAcceptedAssignmentsForAssignment(final int assignmentId) {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        throw new UnsupportedOperationException(REMOVED_MESSAGE);
    }

    /**
     * Get assignment grades (GET {@code /assignments/{assignment_id}/grades}).
     *
     * @param assignmentId assignment id
     * @throws UnsupportedOperationException always, the operation was removed from the GitHub REST API
     * @deprecated This operation was closed and removed on {@code 2026-08-28} and always returns HTTP 410. Deprecation
     *     date {@code 2026-05-22}; removal date {@code 2026-08-28}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void getAssignmentGrades(final int assignmentId) {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        throw new UnsupportedOperationException(REMOVED_MESSAGE);
    }

    /**
     * List classrooms (GET {@code /classrooms}).
     *
     * @throws UnsupportedOperationException always, the operation was removed from the GitHub REST API
     * @deprecated This operation was closed and removed on {@code 2026-08-28} and always returns HTTP 410. Deprecation
     *     date {@code 2026-05-22}; removal date {@code 2026-08-28}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void listClassrooms() {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        throw new UnsupportedOperationException(REMOVED_MESSAGE);
    }

    /**
     * Get a classroom (GET {@code /classrooms/{classroom_id}}).
     *
     * @param classroomId classroom id
     * @throws UnsupportedOperationException always, the operation was removed from the GitHub REST API
     * @deprecated This operation was closed and removed on {@code 2026-08-28} and always returns HTTP 410. Deprecation
     *     date {@code 2026-05-22}; removal date {@code 2026-08-28}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void getClassroom(final int classroomId) {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        throw new UnsupportedOperationException(REMOVED_MESSAGE);
    }

    /**
     * List assignments for a classroom (GET {@code /classrooms/{classroom_id}/assignments}).
     *
     * @param classroomId classroom id
     * @throws UnsupportedOperationException always, the operation was removed from the GitHub REST API
     * @deprecated This operation was closed and removed on {@code 2026-08-28} and always returns HTTP 410. Deprecation
     *     date {@code 2026-05-22}; removal date {@code 2026-08-28}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void listAssignmentsForClassroom(final int classroomId) {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        throw new UnsupportedOperationException(REMOVED_MESSAGE);
    }
}

package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** GitHub Pages deployment status. */
@CatalogSchema("components/schemas/pages-deployment-status/properties/status")
public enum GitHubPagesDeploymentStatus {
    DEPLOYMENT_IN_PROGRESS("deployment_in_progress"),
    SYNCING_FILES("syncing_files"),
    FINISHED_FILE_SYNC("finished_file_sync"),
    UPDATING_PAGES("updating_pages"),
    PURGING_CDN("purging_cdn"),
    DEPLOYMENT_CANCELLED("deployment_cancelled"),
    DEPLOYMENT_FAILED("deployment_failed"),
    DEPLOYMENT_CONTENT_FAILED("deployment_content_failed"),
    DEPLOYMENT_ATTEMPT_ERROR("deployment_attempt_error"),
    DEPLOYMENT_LOST("deployment_lost"),
    SUCCEED("succeed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubPagesDeploymentStatus> WIRE_INDEX =
            EnumUtils.index(GitHubPagesDeploymentStatus.class, GitHubPagesDeploymentStatus::wireValue);

    private final String wireValue;

    GitHubPagesDeploymentStatus(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Deployment status from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubPagesDeploymentStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}

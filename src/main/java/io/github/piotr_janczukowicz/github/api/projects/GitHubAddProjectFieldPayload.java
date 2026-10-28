package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/** Add project field request body. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/projects/add-field-for-org/requestBody")
public sealed interface GitHubAddProjectFieldPayload
        permits GitHubAddProjectFieldPayload.IssueField,
                GitHubAddProjectFieldPayload.Basic,
                GitHubAddProjectFieldPayload.SingleSelect,
                GitHubAddProjectFieldPayload.Iteration {

    /**
     * Adds a field from an existing issue field id.
     *
     * @param issueFieldId issue field id
     * @return payload
     */
    static IssueField issueField(final long issueFieldId) {
        return new IssueField(issueFieldId);
    }

    /**
     * Adds a text, number, or date field.
     *
     * @param name field name
     * @param dataType field data type
     * @return payload
     */
    static Basic basic(final String name, final GitHubProjectFieldDataType dataType) {
        return new Basic(name, dataType);
    }

    /**
     * Adds a single-select field.
     *
     * @param name field name
     * @param singleSelectOptions select options
     * @return payload
     */
    static SingleSelect singleSelect(
            final String name, final List<GitHubProjectFieldSingleSelectOption> singleSelectOptions) {
        return new SingleSelect(name, singleSelectOptions);
    }

    /**
     * Adds an iteration field.
     *
     * @param name field name
     * @param iterationConfiguration iteration configuration
     * @return payload
     */
    static Iteration iteration(
            final String name, final GitHubProjectFieldIterationConfiguration iterationConfiguration) {
        return new Iteration(name, iterationConfiguration);
    }

    /**
     * Issue field id variant.
     *
     * @param issueFieldId Issue field id
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    record IssueField(@JsonProperty("issue_field_id") long issueFieldId) implements GitHubAddProjectFieldPayload {}

    /**
     * Text, number, or date field variant.
     *
     * @param name Field name
     * @param dataType Field data type
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    record Basic(String name, @JsonProperty("data_type") GitHubProjectFieldDataType dataType)
            implements GitHubAddProjectFieldPayload, GitHubAddFieldForUserPayload {

        private static final String PARAM_NAME = "name";
        private static final String PARAM_DATA_TYPE = "dataType";

        public Basic {
            requireNonNull(name, PARAM_NAME);
            requireNonNull(dataType, PARAM_DATA_TYPE);
            if (dataType != GitHubProjectFieldDataType.TEXT
                    && dataType != GitHubProjectFieldDataType.NUMBER
                    && dataType != GitHubProjectFieldDataType.DATE) {
                throw new IllegalArgumentException(PARAM_DATA_TYPE + " must be text, number, or date");
            }
        }
    }

    /**
     * Single-select field variant.
     *
     * @param name Field name
     * @param singleSelectOptions Select options
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    record SingleSelect(
            String name,
            @JsonProperty("single_select_options") List<GitHubProjectFieldSingleSelectOption> singleSelectOptions)
            implements GitHubAddProjectFieldPayload, GitHubAddFieldForUserPayload {

        private static final String PARAM_NAME = "name";
        private static final String PARAM_SINGLE_SELECT_OPTIONS = "singleSelectOptions";

        public SingleSelect {
            requireNonNull(name, PARAM_NAME);
            singleSelectOptions = List.copyOf(requireNonNull(singleSelectOptions, PARAM_SINGLE_SELECT_OPTIONS));
        }

        /**
         * Data type wire value for this variant.
         *
         * @return {@code single_select}
         */
        @JsonProperty("data_type")
        public GitHubProjectFieldDataType dataType() {
            return GitHubProjectFieldDataType.SINGLE_SELECT;
        }
    }

    /**
     * Iteration field variant.
     *
     * @param name Field name
     * @param iterationConfiguration Iteration configuration
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    record Iteration(
            String name,
            @JsonProperty("iteration_configuration") GitHubProjectFieldIterationConfiguration iterationConfiguration)
            implements GitHubAddProjectFieldPayload, GitHubAddFieldForUserPayload {

        private static final String PARAM_NAME = "name";
        private static final String PARAM_ITERATION_CONFIGURATION = "iterationConfiguration";

        public Iteration {
            requireNonNull(name, PARAM_NAME);
            requireNonNull(iterationConfiguration, PARAM_ITERATION_CONFIGURATION);
        }

        /**
         * Data type wire value for this variant.
         *
         * @return {@code iteration}
         */
        @JsonProperty("data_type")
        public GitHubProjectFieldDataType dataType() {
            return GitHubProjectFieldDataType.ITERATION;
        }
    }
}

package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Thread subject.
 *
 * @param title The subject title
 * @param url The subject url
 * @param latestCommentUrl The url of the latest comment on the subject
 * @param type The subject type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/thread/properties/subject")
@RecordBuilder
public record GitHubThreadSubject(String title, String url, String latestCommentUrl, String type) {

    public GitHubThreadSubject {
        requireNonNull(title, "title");
        requireNonNull(url, "url");
        requireNonNull(latestCommentUrl, "latestCommentUrl");
        requireNonNull(type, "type");
    }
}

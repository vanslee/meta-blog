package com.ldx.blog.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Uaena
 * @date 2023/5/19 22:57
 */
@Data
public class CommentPage {
    private Integer current;
    private Integer size;
    @JsonProperty("article_id")
    private Long articleId;
}

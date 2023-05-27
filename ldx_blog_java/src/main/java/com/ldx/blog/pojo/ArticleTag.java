package com.ldx.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
* @author Uaena
* @TableName article_tag
 *
*/
@Getter
@Setter
@AllArgsConstructor
public class ArticleTag implements Serializable {

    /**
    * 
    */
    private Long articleId;
    /**
    * 
    */
    private Integer tagId;

}

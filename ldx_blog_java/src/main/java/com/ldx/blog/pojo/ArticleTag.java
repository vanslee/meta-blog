package com.ldx.blog.pojo;

import lombok.Data;

import java.io.Serializable;

/**
* @author Uaena
* @TableName article_tag
 *
*/
@Data
public class ArticleTag implements Serializable {

    /**
    * 
    */
    private Long articleId;
    /**
    * 
    */
    private Long tagId;

}

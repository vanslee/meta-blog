package com.ldx.blog.pojo;

import lombok.Data;

import java.io.Serializable;

/**
* @author Uaena
* @TableName article_category
*/
@Data
public class ArticleCategory implements Serializable {

    /**
    * 
    */
    private Long articleId;
    /**
    * 
    */
    private Integer categoryId;

}

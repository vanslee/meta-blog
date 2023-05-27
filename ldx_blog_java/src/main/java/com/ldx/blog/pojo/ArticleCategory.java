package com.ldx.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
* @author Uaena
* @TableName article_category
*/
@Getter
@Setter
@AllArgsConstructor
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

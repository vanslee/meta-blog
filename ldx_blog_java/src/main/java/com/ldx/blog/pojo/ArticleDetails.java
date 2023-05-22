package com.ldx.blog.pojo;

import lombok.Data;

import java.io.Serializable;

/**
* @author Uaena
* @TableName article_details
*/
@Data
public class ArticleDetails implements Serializable {

    /**
    * 
    */
    private Long id;
    /**
    * 
    */
    private String content;

}

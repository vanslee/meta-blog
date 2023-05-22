package com.ldx.blog.pojo;

import lombok.Data;

import java.io.Serializable;

/**
* @author Uaena
* @TableName article
*/
@Data
public class Article implements Serializable {

    /**
    * 
    */
    private Integer id;
    /**
    * 
    */
    private Long articleId;
    /**
    * 
    */
    private String author;
    /**
    * 
    */
    private String originalAuthor;
    /**
    * 
    */
    private String originalUrl;
    /**
    * 
    */
    private String articleTitle;
    /**
    * 
    */
    private Long publishDate;
    /**
    *
    */
    private Long updateDate;
    /**
    *
    */
    private String articleOmission;
    /**
    * 
    */
    private String imgUrl;
    /**
    * 
    */
    private Object views;
    /**
    * 
    */
    private Object likes;
    /**
    * 
    */
    private Object comments;
    private char isDelete;



}

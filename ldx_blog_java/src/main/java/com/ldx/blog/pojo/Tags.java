package com.ldx.blog.pojo;

import lombok.Data;

import java.io.Serializable;

/**
* @author Uaena
* @TableName tags
*/
@Data
public class Tags implements Serializable {

    /**
    * 
    */
    private Integer id;
    /**
    *
    */
    private String tagName;
    /**
    * 
    */
    private Long createTime;
    /**
    * 
    */
    private Boolean isDelete;
    /**
    * 
    */
    private Integer categoryId;



}

package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
* @author Uaena
* @TableName tags
*/
@Getter
@Setter
public class Tags implements Serializable {

    /**
    * 
    */
    @TableId(value = "id", type = IdType.AUTO)
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

    public Tags(Integer id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }
}

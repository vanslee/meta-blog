package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName reward
 */
@TableName(value ="reward")
@Data
public class Reward implements Serializable {
    private Integer id;

    private String fundraiser;

    private String fundraisingsources;

    private String fundraisingplace;

    private Double rewardmoney;

    private String remarks;

    private Date rewarddate;

    private String rewardurl;

    private static final long serialVersionUID = 1L;
}
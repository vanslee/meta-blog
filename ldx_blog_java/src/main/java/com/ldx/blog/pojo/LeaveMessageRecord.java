package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName leave_message_record
 */
@TableName(value ="leave_message_record")
@Data
public class LeaveMessageRecord implements Serializable {
    private Integer id;

    private String pagename;

    private Integer pid;

    private Integer answererid;

    private Integer respondentid;

    private String leavemessagedate;

    private Integer likes;

    private String leavemessagecontent;

    private Integer isread;

    private static final long serialVersionUID = 1L;
}
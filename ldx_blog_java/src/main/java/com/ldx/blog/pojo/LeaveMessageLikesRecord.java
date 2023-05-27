package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName leave_message_likes_record
 */
@TableName(value ="leave_message_likes_record")
@Data
public class LeaveMessageLikesRecord implements Serializable {
    private Integer id;

    private String pagename;

    private Integer pid;

    private Integer likerid;

    private String likedate;

    private static final long serialVersionUID = 1L;
}
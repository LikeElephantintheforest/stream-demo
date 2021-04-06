package cn.lcy.stream.repository.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * t_test_student
 *
 * @author admin
 * @date 2021-04-02 15:42:35
 */
@Data
@TableName(value = "t_test_student")
public class StudentDO extends BaseDO {

    /**
     * 班级id
     */
    private Long classId;

    /**
     * 兴趣班id
     */
    private Long hobbyId;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 最后一次参加兴趣班时间
     */
    private Date lastAttendTime;


}
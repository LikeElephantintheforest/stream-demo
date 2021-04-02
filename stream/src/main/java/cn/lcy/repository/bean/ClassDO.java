package cn.lcy.repository.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * t_test_class
 *
 * @author admin
 * @date 2021-04-02 15:42:35
 */
@Data
@TableName(value = "t_test_class")
public class ClassDO extends BaseDO {

    /**
     * 名称
     */
    private String name;

}
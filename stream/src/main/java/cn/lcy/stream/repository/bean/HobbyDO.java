package cn.lcy.stream.repository.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * t_test_hobby
 *
 * @author admin
 * @date 2021-04-02 15:42:35
 */
@Data
@TableName(value = "t_test_hobby")
public class HobbyDO extends BaseDO {

    /**
     * 描述
     */
    private String desc;


}
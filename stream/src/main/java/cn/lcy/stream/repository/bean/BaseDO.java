package cn.lcy.stream.repository.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @description:
 * @author: admin
 * @email: lichenyang
 * @date: 2021/4/2 3:43 下午
 */
@Getter
@Setter
@ToString
public class BaseDO {

    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableLogic
    /**
     * 加@TableLogic的情况：执行update user set deleted=1 where id =1 and deleted=0。
     * 不加@TableLogic的情况：执行delete from user where deleted=0。
     *
     * 作者：日常更新
     * 链接：https://www.jianshu.com/p/8c7f05c4d242
     * 来源：简书
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    protected Integer isDel;

    /**
     * 自动填充
     */
    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date modifyTime = new Date();

}

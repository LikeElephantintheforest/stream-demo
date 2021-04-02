package cn.lcy.repository.datasupport;

import cn.lcy.repository.bean.StudentDO;
import cn.lcy.repository.mapper.StudentDOMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: admin
 * @email: lichenyang
 * @date: 2021/4/2 3:59 下午
 */
@Repository
public class StudentDataSupport extends ServiceImpl<StudentDOMapper, StudentDO> {
}

package cn.lcy.stream.demo1;

import cn.lcy.stream.BaseTransactionTest;
import cn.lcy.stream.repository.bean.StudentDO;
import cn.lcy.stream.repository.datasupport.StudentDataSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class StreamApplicationTests extends BaseTransactionTest {

    @Resource
    StudentDataSupport studentDataSupport;

    @BeforeClass(description = "")
    public void initData() {

        Long hobbyId = 100L;
        Long classId = 10L;

        //create students;
        ArrayList<StudentDO> firstConditionHobbyAndClass = new ArrayList<>();
        StudentDO studentDO = new StudentDO();

        for (int i = 0; i < 10; i++) {
            studentDO.setClassId(classId);
            studentDO.setHobbyId(hobbyId);
            studentDO.setAge(RandomUtils.nextInt(2));
            studentDO.setLastAttendTime(new Date());
            firstConditionHobbyAndClass.add(studentDO);
        }

        studentDataSupport.saveBatch(firstConditionHobbyAndClass);

    }

    @Test(description = "根据hobbyId对students进行分组;如果hobbyId重复，则使用集合进行装填")
    public void extractedByHobbyId() {


    }


}

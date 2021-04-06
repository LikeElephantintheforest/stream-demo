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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        ArrayList<StudentDO> secondConditionHobbyAndClass = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            StudentDO studentDO = new StudentDO();
            studentDO.setClassId(classId);
            studentDO.setHobbyId(hobbyId);
            studentDO.setAge(RandomUtils.nextInt(18));
            studentDO.setLastAttendTime(new Date());
            firstConditionHobbyAndClass.add(studentDO);
        }
        for (int i = 0; i < 10; i++) {
            StudentDO studentDO = new StudentDO();
            studentDO.setClassId(classId + 5);
            studentDO.setHobbyId(hobbyId + 5);
            studentDO.setAge(RandomUtils.nextInt(18));
            studentDO.setLastAttendTime(new Date());
            secondConditionHobbyAndClass.add(studentDO);
        }

        studentDataSupport.saveBatch(firstConditionHobbyAndClass);
        studentDataSupport.saveBatch(secondConditionHobbyAndClass);

    }

    @Test(description = "根据hobbyId对students进行分组;如果hobbyId重复，则使用集合进行装填")
    public void extractedByHobbyId() {

        //当前的全量学生
        List<StudentDO> requestData = studentDataSupport.list();

        Map<Long/*hobby_id*/, List<StudentDO>/*hobby_id对应的students*/> result = requestData.stream()
                .collect(
                        Collectors.toMap(
                                StudentDO::getHobbyId,
                                s -> {
                                    List<StudentDO> list = new ArrayList<>();
                                    list.add(s);
                                    return list;
                                },
                                (List<StudentDO> value1, List<StudentDO> value2) -> {
                                    value1.addAll(value2);
                                    return value1;
                                }
                        )
                );

        System.out.println(result);


    }


}

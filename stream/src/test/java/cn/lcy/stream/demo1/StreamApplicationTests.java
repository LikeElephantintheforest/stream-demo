package cn.lcy.stream.demo1;

import cn.lcy.stream.BaseTransactionTest;
import cn.lcy.stream.repository.bean.StudentDO;
import cn.lcy.stream.repository.datasupport.StudentDataSupport;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.StopWatch;
import org.joda.time.DateTime;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
public class StreamApplicationTests extends BaseTransactionTest {

    @Resource
    StudentDataSupport studentDataSupport;

    @BeforeClass(description = "初始化数据")
    public void initData() {

        Long classId = 10L;
        Long hobbyId = 100L;

        //create students;
        ArrayList<StudentDO> firstConditionHobbyAndClass = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            StudentDO studentDO = new StudentDO();
            studentDO.setClassId(classId + RandomUtils.nextInt(5));
            studentDO.setHobbyId(hobbyId + RandomUtils.nextInt(5));
            studentDO.setAge(RandomUtils.nextInt(18));
            studentDO.setLastAttendTime(DateTime.now().plusHours(RandomUtils.nextInt(10)).toDate());
            firstConditionHobbyAndClass.add(studentDO);
        }

        studentDataSupport.saveBatch(firstConditionHobbyAndClass);

    }

    @Test(description = "case1 : 根据字段排序")
    public void sortTest() {

        //当前的全量学生
        List<StudentDO> requestData = studentDataSupport.list();

        List<StudentDO> sortList = requestData.stream().sorted(
                Comparator.comparing(StudentDO::getAge)     //首先根据age排序
                        .thenComparing(StudentDO::getHobbyId).reversed()//其次根据id编号排序
        ).collect(Collectors.toList());

        List<StudentDO> sortList2 = requestData.stream().sorted(Comparator.comparing(StudentDO::getAge))
                .collect(Collectors.toList());

        log.info("sortList response = {} ", sortList);
        log.info("sortList2 response = {} ", sortList);

    }

    @Test(description = "case2 : 根据hobbyId对students进行分组，如果hobbyId重复，则使用集合进行装填；")
    public void extractedByHobbyId() {

        //当前的全量学生
        List<StudentDO> requestData = studentDataSupport.list();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //test stream
        Map<Long/*hobby_id*/, List<StudentDO>/*hobby_id对应的students*/> result = requestData.stream()
                .collect(
                        Collectors.toMap(
                                StudentDO::getHobbyId, // key wrapper
                                s -> {                  // value wrapper
                                    List<StudentDO> list = new ArrayList<>();
                                    list.add(s);
                                    return list;
                                },
                                (List<StudentDO> value1, List<StudentDO> value2) -> {   //mergeFunc
                                    value1.addAll(value2);
                                    return value1;
                                }
                        )
                );
        stopWatch.stop();
        log.info("stream : elapsed[{}]", stopWatch.getTime());
        log.info("stream : size[{}]", result.size());// elapsed[27]

        stopWatch.reset();
        stopWatch.start();
        //test Multimaps
        ImmutableListMultimap<Long, StudentDO> index = Multimaps.index(requestData, StudentDO::getHobbyId);
        log.info("guava : elapsed[{}]", stopWatch.getTime());//elapsed[58]

    }

    @Test(description = "case3 : 根据表达式进行分组")
    public void t1() {

        List<StudentDO> requestData = studentDataSupport.list();

        Map<Boolean/*true 有兴趣班*/, List<StudentDO>> haveHobbyStudents =
                requestData.stream().collect(Collectors.groupingBy(e -> e.getHobbyId() > 0));

    }


    @Test(description = "case4 : 根据字段分组随后进行排序并选取top")
    public void groupAndThenSort() {

        //当前的全量学生
        List<StudentDO> requestData = studentDataSupport.list();

        //方法一: 先分组，随后从组内寻找【指定】数据
        Collector<StudentDO, ?, Optional<StudentDO>> sortFunction = Collectors.reducing((c1, c2) -> c1.getAge() > c2.getAge() ? c1 : c2);
        Map<Long/*hobby_id*/, StudentDO> groupAndSorting1 = requestData.stream()
                .collect(Collectors.groupingBy(StudentDO::getHobbyId, // 先根据HobbyId分组
                        Collectors.collectingAndThen(sortFunction, Optional::get)));// 随后排序
        //方法二: 当key冲突时，以怎样的方式保留唯一key相关的数据
        Map<Long/*hobby_id*/, StudentDO> groupAndSorting2 = requestData.stream()
                .collect(Collectors.toMap(StudentDO::getHobbyId, Function.identity(), (c1, c2) -> c1.getAge() > c2.getAge() ? c1 : c2));// 随后排序

        //case 3: 类似的sql
        /**
         * SELECT *
         * FROM   (SELECT id,
         *                hobby_id,
         *                class_id,
         *                last_attend_time,
         *                IF(@bak = hobby_id, @rounum := @rounum + 1, @rounum := 1) AS
         *                row_number,
         *                @bak := hobby_id
         *         FROM   (SELECT id,
         *                        hobby_id,
         *                        class_id,
         *                        last_attend_time
         *                 FROM   t_test_student
         *                 ORDER  BY hobby_id ASC,
         *                           last_attend_time DESC) a,
         *                (SELECT @rounum := 0,
         *                        @bak := 0) b) c
         * WHERE  c.row_number < 2
         */

    }


    @Test(description = "平均值、总计等常用方法")
    public void summarizingTest() {

        //当前的全量学生
        List<StudentDO> requestData = studentDataSupport.list();
        Map<Long/*hobby_id*/, IntSummaryStatistics> summaryStatisticsMap =
                requestData.stream().collect(Collectors.groupingBy(StudentDO::getHobbyId, Collectors.summarizingInt(StudentDO::getAge)));

        summaryStatisticsMap.forEach((hobbyId, summarizing) -> {
            double average = summarizing.getAverage();
            long count = summarizing.getCount();
            int max = summarizing.getMax();
            long sum = summarizing.getSum();
            int min = summarizing.getMin();
            log.info("{},{},{},{},{},", average, count, max, sum, min);
        });

    }


}

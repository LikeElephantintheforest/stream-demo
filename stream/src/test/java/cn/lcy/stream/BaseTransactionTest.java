package cn.lcy.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * @description:
 * @author: admin
 * @email: lichenyang
 * @date: 2021/4/6 11:16 上午
 */
@Slf4j
@Rollback(value = true)
@SpringBootTest(classes = StreamApplication.class)
public class BaseTransactionTest extends AbstractTransactionalTestNGSpringContextTests {

    @Test
    public void testEnv() throws InterruptedException {
        Thread.sleep(2000L);
        System.out.println("test env running");
    }

}

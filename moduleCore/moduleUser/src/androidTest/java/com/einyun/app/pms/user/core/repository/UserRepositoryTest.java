package com.einyun.app.pms.user.core.repository;

import androidx.test.runner.AndroidJUnit4;

import com.einyun.app.base.event.CallBack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.user.core.repository
 * @ClassName: UserRepositoryTest
 * @Description: UserRepository单元测试
 * @Author: chumingjun
 * @CreateDate: 2019/09/09 17:32
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/09 17:32
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@RunWith(AndroidJUnit4.class)
public class UserRepositoryTest {
    UserRepository userRepository;

    @Before
    public void setup(){
        userRepository=new UserRepository();
    }

    @Test
    public void localUser() {

    }


    /**
     *
     * 登陆单元测试
     * @throws InterruptedException
     */
    @Test
    public void login() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        userRepository.login("wy_yong_he1", "1234@qwer", (CallBack<LoginResponse>) data -> {
            assertNotNull(data);
            assertNotNull(data.getData());
            assertNotNull(data.getData().getUserId());
            latch.countDown();
        });
        latch.await(10, TimeUnit.SECONDS);
    }
}
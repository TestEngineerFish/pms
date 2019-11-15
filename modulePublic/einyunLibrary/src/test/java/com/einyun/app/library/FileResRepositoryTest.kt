package com.einyun.app.library

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.library.upload.repository
 * @ClassName: FileResRepositoryTest
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/31 0031 下午 15:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/31 0031 下午 15:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

class FileResRepositoryTest {


    @Test
    fun testCountDownLatch(){
        var countDownLatch=CountDownLatch(30)
        var excutePool: ExecutorService= Executors.newFixedThreadPool(5)
        for(i in 1..30){
            var runnable= Runnable {
                System.out.println(i)
                countDownLatch.countDown()
                System.out.println(Thread.currentThread().name)
            }
            excutePool.execute(runnable)
        }
        countDownLatch.await()
        System.out.println("await")
        System.out.println(Thread.currentThread().name)
        excutePool.shutdown()
        System.out.println("finish")
        System.out.println(Thread.currentThread().name)
    }
}
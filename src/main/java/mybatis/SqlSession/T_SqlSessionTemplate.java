/*******************************************************************************
 * Project Key : 
 * Create on 2018年8月1日 上午10:13:15
 * Copyright (c) 2004 - 2014. 拉卡拉支付有限公司版权所有. 京ICP备12007612号
 * 注意：本内容仅限于拉卡拉支付有限公司内部传阅，禁止外泄以及用于其他的商业目的
 ******************************************************************************/
package mybatis.SqlSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mybatis.mappers.TdSystemLog;
import mybatis.mappers.TdSystemLogMapper;

/**
 * <P>TODO</P>
 * @author 陈冠达   2018年8月1日 上午10:13:15  
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class T_SqlSessionTemplate {
	@Autowired
	public TdSystemLogMapper tdSystemLogMapper;

	@Autowired
	public SqlSessionTemplate sqlSessionTemplate;

	@Test
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, timeout = 10)
	public void temp() throws Exception {
		TdSystemLog t = tdSystemLogMapper.selectByPrimaryKey("1212");
		System.out.println(t.getUpdate());
	}

	@Test
	public void Threadsafe() throws Exception {
		ExecutorService e = Executors.newCachedThreadPool();
		for (int i = 0; i < 20; ++i) {
			e.execute(new Runnable() {
				@Override
				public void run() {
					TdSystemLog t = tdSystemLogMapper.selectByPrimaryKey("1212");
					System.out.println(t.getUpdate());
				}
			});
		}
		//阻塞等待线程池执行完所有任务，否则junit提前结束
		e.shutdown();
		e.awaitTermination(5, TimeUnit.MINUTES);
	}
}

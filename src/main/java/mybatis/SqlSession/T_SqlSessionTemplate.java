
package mybatis.SqlSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import mybatis.mappers.TdSystemLog;
import mybatis.mappers.TdSystemLogMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Component
public class T_SqlSessionTemplate {
	Logger logger = LoggerFactory.getLogger(T_SqlSessionTemplate.class);
	@Autowired
	public TdSystemLogMapper tdSystemLogMapper;

	@Autowired
	public SqlSessionTemplate sqlSessionTemplate;

	@Test
	@Transactional
	public void temp() throws Exception {
		TdSystemLog t = tdSystemLogMapper.selectByPrimaryKey("1212");
	}

	@Test
	public void Threadsafe() throws Exception {
		ExecutorService e = Executors.newCachedThreadPool();
		for (int i = 0; i < 20; ++i) {
			e.execute(new Runnable() {
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


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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mybatis.mappers.TdSystemLog;
import mybatis.mappers.TdSystemLogMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class T_SqlSessionTemplate {

	@Test
	public void temp() throws Exception {
		transactional_Method.Query();
	}

	public static void simpleQuery() throws Exception {
		tdSystemLogMapper.selectByPrimaryKey("1212");
	}

	@Test
	public void Create_Use_Commit_close() throws Exception {
		simpleQuery();
		simpleQuery();
		simpleQuery();//一共消耗三个DefaultSqlSession
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

	Logger logger = LoggerFactory.getLogger(T_SqlSessionTemplate.class);

	public static TdSystemLogMapper tdSystemLogMapper;

	public static SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	public Transactional_Method transactional_Method;

	@Autowired
	public void setTdSystemLogMapper(TdSystemLogMapper tdSystemLogMapper) {
		T_SqlSessionTemplate.tdSystemLogMapper = tdSystemLogMapper;
	}

	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		T_SqlSessionTemplate.sqlSessionTemplate = sqlSessionTemplate;
	}

}

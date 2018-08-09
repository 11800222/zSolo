
package mybatis.SqlSession;

import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import mybatis.mappers.TdSystemLog;
import mybatis.mappers.TdSystemLogMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class T_SqlSessionTemplate {

	@Test
	public void diff_Transactional_same_SqlSession() throws Exception {
		//	transactional_Method.nesting_Transactional_RollBack();
		//	another_Transactional_Method.RollBack();
		//		another_Transactional_Method.RollBack_throw_out();

	}

	@Test
	public void Rollback() throws Exception {
		another_Transactional_Method.RollBack_catch();//Exception不抛出@Transactional外不会回滚

		another_Transactional_Method.RollBack_throw_out();

	}

	@Test
	public void thread_safe() throws Exception {
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
	public static Transactional_Method transactional_Method;

	@Autowired
	public static Another_Transactional_Method another_Transactional_Method;

	@Autowired
	public void setTransactional_Method(Transactional_Method transactional_Method) {
		T_SqlSessionTemplate.transactional_Method = transactional_Method;
	}

	@Autowired
	public void setAnother_Transactional_Method(Another_Transactional_Method another_Transactional_Method) {
		T_SqlSessionTemplate.another_Transactional_Method = another_Transactional_Method;
	}

	@Autowired
	public void setTdSystemLogMapper(TdSystemLogMapper tdSystemLogMapper) {
		T_SqlSessionTemplate.tdSystemLogMapper = tdSystemLogMapper;
	}

	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		T_SqlSessionTemplate.sqlSessionTemplate = sqlSessionTemplate;
	}

}

class Transactional_Method {

	@Transactional
	public void nesting_Transactional() throws Exception {

		T_SqlSessionTemplate.tdSystemLogMapper.selectByPrimaryKey("1212");

		System.out.println("进入另一个类的@Transactional方法。。。。。");
		T_SqlSessionTemplate.another_Transactional_Method.Query();
		System.out.println("退出另一个类的@Transactional方法。。。。。");
	}

	@Transactional
	public void nesting_Transactional_RollBack() throws Exception {

		T_SqlSessionTemplate.tdSystemLogMapper.selectByPrimaryKey("1212");

		System.out.println("进入另一个类的@Transactional（会rollback）方法。。。。。");
		try {
			T_SqlSessionTemplate.another_Transactional_Method.RollBack_throw_out();//
		} catch (Exception e) {
		}
		System.out.println("退出另一个类的@Transactional（会rollback）方法。。。。。");
	}

}

class Another_Transactional_Method {

	@Transactional
	public void RollBack_throw_out() throws Exception {
		System.out.println("正常更新Notes为时间：" + String.valueOf(new Date().getTime()));
		T_SqlSessionTemplate.sqlSessionTemplate.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("1212", String.valueOf(new Date().getTime())));//insert、delete都是走的update

		System.out.println("执行异常数据库操作：");
		StringBuffer longStr = new StringBuffer("————————————————————————");
		for (int i = 0; i < 10; ++i) {
			longStr.append(longStr);
		}
		T_SqlSessionTemplate.sqlSessionTemplate.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("1212", longStr.toString()));//insert、delete都是走的update

		System.out.println("结束");
	}

	@Transactional
	public void Query() throws Exception {
		T_SqlSessionTemplate.tdSystemLogMapper.selectByPrimaryKey("1212");
	}

	@Transactional
	public void RollBack_catch() {
		System.out.println("正常更新Notes为时间：" + String.valueOf(new Date().getTime()));
		T_SqlSessionTemplate.sqlSessionTemplate.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("1212", String.valueOf(new Date().getTime())));//insert、delete都是走的update

		try {
			System.out.println("执行异常数据库操作：");
			StringBuffer longStr = new StringBuffer("————————————————————————");
			for (int i = 0; i < 10; ++i) {
				longStr.append(longStr);
			}
			T_SqlSessionTemplate.sqlSessionTemplate.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("1212", longStr.toString()));//insert、delete都是走的update
		} catch (Exception e) {
		}

		System.out.println("结束");
	}
}

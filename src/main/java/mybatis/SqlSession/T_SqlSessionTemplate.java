
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mybatis.mappers.TdSystemLog;
import mybatis.mappers.TdSystemLogMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class T_SqlSessionTemplate {

	@Test
	public void TEMP() throws Exception {
		transactional_Method.inner_REQUIRES_NEW_Transactional();
	}

	@Test
	public void Rollback() throws Exception {

		another_Transactional_Method.Dont_throw_out_RuntimeEX_Transactional();//Exception不抛出@Transactional外,不回滚

		another_Transactional_Method.throw_out_RuntimeEX_Transactional();//Exception抛出@Transactional外,回滚

	}

	@Test
	public void PROPAGATION() throws Exception {//不同传播级别的sqlsession使用
		//	transactional_Method.inner_REQUIRED_Transactional();//默认PROPAGATION——PROPAGATION_REQUIRED

		transactional_Method.inner_REQUIRES_NEW_Transactional();//内层@Transactional需要新开sqlsession

	}

	@Test
	public void PROPAGATION_REQUIRED_RollBack() throws Exception {//REQUIRED传播级别的回滚保障
		//内层如何影响外层：不捕捉内层@Transactional的RuntimeException
		//	transactional_Method.Dont_catch_inner_RuntimeException();

		//内层如何影响外层：捕捉内层@Transactional的RuntimeException
		//	transactional_Method.catch_inner_RuntimeException(); //Transaction rolled back because it has been marked as rollback-only

		//外层如何影响内层：
		transactional_Method.inner_NoThrowEX_outer_ThrowEx();
	}

	@Test
	public void PROPAGATION_REQUIRES_NEW_RollBack() throws Exception {//REQUIRES_NEW传播级别的回滚保障

	}

	@Test
	public void thread_safe() throws Exception {//SqlSessionTemplate线程安全验证
		//多线程同时使用一个SqlSessionTemplate
		ExecutorService e = Executors.newCachedThreadPool();
		for (int i = 0; i < 20; ++i) {
			e.execute(new Runnable() {
				public void run() {
					TdSystemLog t = tdSystemLogMapper.selectByPrimaryKey("1212");
					T_SqlSessionTemplate.logger.info(t.getUpdate().toString());
				}
			});
		}
		//阻塞等待线程池执行完所有任务，否则junit提前结束
		e.shutdown();
		e.awaitTermination(5, TimeUnit.MINUTES);
	}

	static Logger logger = LoggerFactory.getLogger(T_SqlSessionTemplate.class);

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
	public void inner_REQUIRED_Transactional() {

		T_SqlSessionTemplate.tdSystemLogMapper.selectByPrimaryKey("1212");

		T_SqlSessionTemplate.logger.info("进入另一个类的@Transactional方法。。。。。");
		T_SqlSessionTemplate.another_Transactional_Method.REQUIRES_UPDATE();
		T_SqlSessionTemplate.logger.info("退出另一个类的@Transactional方法。。。。。");
	}

	@Transactional
	public void inner_REQUIRES_NEW_Transactional() {

		T_SqlSessionTemplate.tdSystemLogMapper.selectByPrimaryKey("1212");

		T_SqlSessionTemplate.logger.info("进入另一个类的@Transactional方法。。。。。");
		T_SqlSessionTemplate.another_Transactional_Method.REQUIRES_NEW_UPDATE();
		T_SqlSessionTemplate.logger.info("退出另一个类的@Transactional方法。。。。。");
	}

	@Transactional
	public void Dont_catch_inner_RuntimeException() {

		T_SqlSessionTemplate.tdSystemLogMapper.selectByPrimaryKey("1212");

		T_SqlSessionTemplate.logger.info("进入另一个类的@Transactional（会rollback）方法。。。。。");

		T_SqlSessionTemplate.another_Transactional_Method.throw_out_RuntimeEX_Transactional();

		T_SqlSessionTemplate.logger.info("退出另一个类的@Transactional（会rollback）方法。。。。。");
	}

	@Transactional
	public void catch_inner_RuntimeException() {

		T_SqlSessionTemplate.tdSystemLogMapper.selectByPrimaryKey("1212");

		T_SqlSessionTemplate.logger.info("进入另一个类的@Transactional（会rollback）方法。。。。。");
		try {
			T_SqlSessionTemplate.another_Transactional_Method.throw_out_RuntimeEX_Transactional();
		} catch (Exception e) {
		}
		T_SqlSessionTemplate.logger.info("退出另一个类的@Transactional（会rollback）方法。。。。。");
	}

	@Transactional
	public void inner_NoThrowEX_outer_ThrowEx() {

		T_SqlSessionTemplate.tdSystemLogMapper.selectByPrimaryKey("1212");

		T_SqlSessionTemplate.logger.info("进入另一个类的@Transactional（会rollback）方法。。。。。");
		T_SqlSessionTemplate.another_Transactional_Method.REQUIRES_UPDATE();
		T_SqlSessionTemplate.logger.info("退出另一个类的@Transactional（会rollback）方法。。。。。");

		StringBuffer longStr = new StringBuffer("————————————————————————");
		for (int i = 0; i < 10; ++i) {
			longStr.append(longStr);
		}
		T_SqlSessionTemplate.sqlSessionTemplate.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("1212", longStr.toString()));//insert、delete都是走的update

	}

}

class Another_Transactional_Method {

	@Transactional
	public void REQUIRES_UPDATE() {
		T_SqlSessionTemplate.sqlSessionTemplate.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("1212", (new Date()).toString()));//insert、delete都是走的update
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void REQUIRES_NEW_UPDATE() {
		T_SqlSessionTemplate.sqlSessionTemplate.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("1212", (new Date()).toString()));//insert、delete都是走的update
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void SUPPORTS_UPDATE() {
		T_SqlSessionTemplate.sqlSessionTemplate.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("1212", (new Date()).toString()));//insert、delete都是走的update
	}

	@Transactional
	public void throw_out_RuntimeEX_Transactional() {
		T_SqlSessionTemplate.logger.info("执行异常数据库操作方法开始：");
		StringBuffer longStr = new StringBuffer("————————————————————————");
		for (int i = 0; i < 10; ++i) {
			longStr.append(longStr);
		}
		T_SqlSessionTemplate.sqlSessionTemplate.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("1212", longStr.toString()));//insert、delete都是走的update

		T_SqlSessionTemplate.logger.info("执行异常数据库操作方法结束。");
	}

	@Transactional
	public void Dont_throw_out_RuntimeEX_Transactional() {

		try {
			T_SqlSessionTemplate.logger.info("执行异常数据库操作方法开始：");
			StringBuffer longStr = new StringBuffer("————————————————————————");
			for (int i = 0; i < 10; ++i) {
				longStr.append(longStr);
			}
			T_SqlSessionTemplate.sqlSessionTemplate.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("1212", longStr.toString()));//insert、delete都是走的update
		} catch (Exception e) {
		}

		T_SqlSessionTemplate.logger.info("执行异常数据库操作方法结束。");
	}
}

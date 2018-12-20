package mybatis.SqlSession;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mybatis.mappers.TdSystemLog;
import mybatis.mappers.TdSystemLogMapper;
import util.ObjectUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class T_DefaultSqlSession {

	@Test
	public void temp() throws Exception {
		AnotherSessionUpdateAndCommmit();

	}

	@Test
	public void page() throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		List re = session.selectList("mybatis.mappers.TdSystemLogMapper.selectAll", null, new RowBounds(2, 3));
		System.out.println(re.size());
	}

	@Test
	public void ThreadUnsafe() throws Exception {
		final SqlSession session = sqlSessionFactory.openSession();
		final TdSystemLogMapper mapper = session.getMapper(TdSystemLogMapper.class);

		ExecutorService e = Executors.newCachedThreadPool();
		for (int i = 0; i < 1; ++i) {
			e.execute(new Runnable() {
				@Override
				public void run() {
					try {
						QueryUseThis(session);
						//						TdSystemLog t = mapper.selectByPrimaryKey("1212");  mapper
						//						System.out.println(t.getUpdate());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		//阻塞等待线程池执行完所有任务，否则方法体运行完junit强行退出
		e.shutdown();
		e.awaitTermination(5, TimeUnit.MINUTES);
	}

	@Test
	public void LocalCacheCoverTxLevle() throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		session.getConnection().setTransactionIsolation(2);//Read Commited

		QueryUseThis(session);

		Thread.sleep(1000);
		AnotherSessionUpdateAndCommmit();
		Thread.sleep(1000);

		QueryUseThis(session);
		session.commit();
	}

	@Test
	public void localCacheClearByUpdate() throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		session.getConnection().setTransactionIsolation(2);

		QueryUseThis(session);

		//并不是update缓存的数据行，但缓存仍然被clear
		session.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("12", String.valueOf(new Date().getTime())));//insert、delete都是走的update

		Thread.sleep(1000);
		AnotherSessionUpdateAndCommmit();
		Thread.sleep(1000);

		QueryUseThis(session);
		session.commit();
	}

	public static void QueryUseThis(SqlSession session) throws Exception {
		TdSystemLog t = session.selectOne("mybatis.mappers.TdSystemLogMapper.selectByPrimaryKey", 1212);
		System.out.println("session__" + ObjectUtil.getObjectId(session) + "(tx_level = " + session.getConnection().getTransactionIsolation() + "  autoCommit = " + session.getConnection().getAutoCommit() + "  Query  field value  = " + t.getUpdate().toString().substring(t.getUpdate().toString().length() - 18));
	}

	public static SqlSession AnotherSessionUpdateAndCommmit() throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		System.out.println("—————————————————————————————————————————————————————————————————————————————————————————————————————————————————");
		System.out.println("Anthor session__" + ObjectUtil.getObjectId(session) + "(tx_level = " + session.getConnection().getTransactionIsolation() + "  autoCommit = " + session.getConnection().getAutoCommit() + ")   updated field and commit");
		session.update("mybatis.mappers.TdSystemLogMapper.updateNotNullByPrimaryKey", new TdSystemLog("1212", String.valueOf(new Date().getTime())));
		System.out.println("—————————————————————————————————————————————————————————————————————————————————————————————————————————————————");
		session.commit();
		session.close();
		return session;
	}

	@BeforeClass
	public static void loadDriver() throws Exception {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml"); //读取配置文件
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	public static SqlSessionFactory sqlSessionFactory;
}


package mybatis.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import util.ObjectUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MySqlDriver {
	@Test
	public void temp() throws Exception {
		AnotherSessionUpdateAndCommmit();
		//	AnotherSessionUpdateWithoutCommmit();

	}

	@Test
	public void READ_UNCOMMITTED() throws Exception {
		Connection conn = DriverManager.getConnection(Remotedburl);// getConnection.pcapng
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(1); // SET SESSION TRANSACTION ISOLATION  READ_UNCOMMITTED
		System.out.println("READ_COMMITTED begins, tx_level = " + conn.getTransactionIsolation() + "  autoCommit = " + conn.getAutoCommit()); // SELECT  @@session.tx_isolation

		QueryUseThis(conn);

		Thread.sleep(1000);
		AnotherSessionUpdateWithoutCommmit();//不提交，结果到数据库里看
		Thread.sleep(1000);

		QueryUseThis(conn);

		conn.commit();
		conn.close();
	}

	@Test
	public void READ_COMMITTED() throws Exception {
		Connection conn = DriverManager.getConnection(Remotedburl);
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(2);
		System.out.println("READ_COMMITTED begins, tx_level = " + conn.getTransactionIsolation() + "  autoCommit = " + conn.getAutoCommit());

		QueryUseThis(conn);

		Thread.sleep(1000);
		AnotherSessionUpdateAndCommmit();//提交，结果到数据库里看
		//		AnotherSessionUpdateWithoutCommmit();//不提交，结果到数据库里看
		Thread.sleep(1000);

		QueryUseThis(conn);

		conn.commit();
		conn.close();
	}

	@Test
	public void REPEATABLE_READ() throws Exception {
		Connection conn = DriverManager.getConnection(Remotedburl);
		conn.setAutoCommit(false);
		System.out.println("REPEATABLE_READ tx begins, tx_level = " + conn.getTransactionIsolation() + "  autoCommit = " + conn.getAutoCommit());

		QueryUseThis(conn);

		Thread.sleep(1000);
		AnotherSessionUpdateAndCommmit();//提交，结果到数据库里看
		Thread.sleep(1000);

		QueryUseThis(conn);

		conn.commit();
		conn.close();
	}

	@Test
	public void updateAndrollback() throws Exception {
		Connection conn = DriverManager.getConnection(Remotedburl);
		conn.setAutoCommit(false);

		Statement stmt = conn.createStatement();
		stmt.executeUpdate("update td_system_log set BUSID = '20187301132' where id=1212");

		conn.rollback();
	}

	public static String Remotedburl = ("jdbc:mysql://192.168.0.180:5433/mpbmd?user=mpbmd&password=mpbmd123");
	//	public static String Remotedburl = ("jdbc:mysql://uncledrew11.u1.luyouxia.net:57310/mpbm?user=mpbadm&password=mpbadm123");

	@BeforeClass
	public static void loadDriver() throws Exception {
		// 加载驱动器并注册到JDBC公共类DriverManager
		// newInstance()中driver注册自己：DriverManager.registerDriver(new
		// MyJDBCDriver());
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	}

	public static void QueryUseThis(Connection conn) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM td_system_log where id=1212"); // ResultSet.pcapng
		rs.next();
		System.out.println("connection_" + ObjectUtil.getObjectId(conn) + " Query   field value  = " + rs.getString(8).substring(rs.getString(8).length() - 10));
	}

	public static Connection AnotherSessionUpdateWithoutCommmit() throws Exception {
		Connection conn = DriverManager.getConnection(Remotedburl);
		conn.setAutoCommit(false);
		System.out.println("———————————————————————————————Another Session begin Transaction Update———————————————————————————————————————");
		conn.createStatement().executeUpdate("update td_system_log set NOTES = '" + new Date().toString() + "' where id=1212");
		System.out.println("connection_" + ObjectUtil.getObjectId(conn) + " updated field");

		System.out.println("———————————————————————————————Method return without committing ——————————————————————————————————————————————");
		//conn.commit();
		return conn;
	}

	public static Connection AnotherSessionUpdateAndCommmit() throws Exception {
		Connection conn = DriverManager.getConnection(Remotedburl);
		conn.setAutoCommit(false);
		System.out.println("———————————————————————————————Another Session begin Transaction Update ———————————————————————————————————————");
		System.out.println("connection_" + ObjectUtil.getObjectId(conn) + "(tx_level = " + conn.getTransactionIsolation() + "  autoCommit = " + conn.getAutoCommit() + ")   updated field");
		conn.createStatement().executeUpdate("update td_system_log set NOTES = '" + new Date().toString() + "' where id=1212");
		System.out.println("———————————————————————————————Transaction Commmit——————————————————————————————————————————————————————————————");
		conn.commit();
		return conn;
	}

}
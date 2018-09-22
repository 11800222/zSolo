package mybatis.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestMysql {
	public static String Remotedburl = ("jdbc:mysql://127.0.0.1:3306/world?user=root&password=82936200");

	@Test
	public void temp() throws Exception {
		Connection conn = DriverManager.getConnection(Remotedburl);
		//		Statement stmt = conn.createStatement();
		//		ResultSet rs = stmt.executeQuery("SELECT * FROM city where id=12");
		//		rs.next();
		for (int i = 0; i < 50000; ++i) {
			conn.createStatement().execute("INSERT into testtable(col1) VALUES (" + Math.random() + ")");
		}

	}

}

package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestExec {
	
	String para1;
	
	int para2;
	
	public TestExec(String para1,int para2) {
		this.para1 = para1;
		this.para2 = para2;
	}
	
	public void excute() {
		int out = 0;
		for (int i = 0; i < 1000000; i++) {
			out = out +i;
		}
		System.out.println("第"+para2+"次："+out);
        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/test"; // 数据库 URL
        String user = "root"; // 数据库用户名
        String password = ""; // 数据库密码

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1. 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 建立连接
            connection = DriverManager.getConnection(url, user, password);

            // 3. 创建 Statement
            statement = connection.createStatement();

            // 4. 执行查询
//            String upsql = "UPDATE person SET version = 1 WHERE name = dc";
            String selString = "select version from person where name = 'dc'";
            resultSet = statement.executeQuery(selString);
            // 5. 处理结果
            while (resultSet.next()) {
                System.out.println("Column1: " + resultSet.getString("version"));
                // 继续处理其他列...
            }
            resultSet = statement.executeQuery(selString);
            

            // 5. 处理结果
            while (resultSet.next()) {
                System.out.println("Column1: " + resultSet.getString("version"));
                // 继续处理其他列...
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // 处理数据库驱动未找到异常
        } catch (SQLException e) {
            e.printStackTrace(); // 处理 SQL 异常
        } finally {
            // 6. 关闭连接
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // 处理关闭连接异常
            }
        }
		
	}
	
}

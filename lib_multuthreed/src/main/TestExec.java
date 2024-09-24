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
		System.out.println("��"+para2+"�Σ�"+out);
        // ���ݿ�������Ϣ
        String url = "jdbc:mysql://localhost:3306/test"; // ���ݿ� URL
        String user = "root"; // ���ݿ��û���
        String password = ""; // ���ݿ�����

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1. �������ݿ�����
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. ��������
            connection = DriverManager.getConnection(url, user, password);

            // 3. ���� Statement
            statement = connection.createStatement();

            // 4. ִ�в�ѯ
//            String upsql = "UPDATE person SET version = 1 WHERE name = dc";
            String selString = "select version from person where name = 'dc'";
            resultSet = statement.executeQuery(selString);
            // 5. ������
            while (resultSet.next()) {
                System.out.println("Column1: " + resultSet.getString("version"));
                // ��������������...
            }
            resultSet = statement.executeQuery(selString);
            

            // 5. ������
            while (resultSet.next()) {
                System.out.println("Column1: " + resultSet.getString("version"));
                // ��������������...
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // �������ݿ�����δ�ҵ��쳣
        } catch (SQLException e) {
            e.printStackTrace(); // ���� SQL �쳣
        } finally {
            // 6. �ر�����
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // ����ر������쳣
            }
        }
		
	}
	
}

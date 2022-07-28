package next.login.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import next.login.bean.LoginBean;

public class LoginDao {

	private String dburl = "jdbc:mysql://localhost:3306/project";
	private String dbuname = "root";
	private String dbpassword = "root12345678";
	private String dbdriver = "com.mysql.cj.jdbc.Driver";

	public void loadDriver(String dbDriver) {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(dburl, dbuname, dbpassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public boolean validate(LoginBean loginBean) {
		boolean status = false;
		try {
			loadDriver(dbdriver);
			Connection con = getConnection();

			String sql = "select * from project.member where uname = ? and password = ?";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, loginBean.getUsername());
			ps.setString(2, loginBean.getPassword());

			ResultSet rs = ps.executeQuery();
			status = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return status;
	}

}

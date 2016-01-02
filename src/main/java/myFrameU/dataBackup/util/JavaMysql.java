package myFrameU.dataBackup.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import myFrameU.dataBackup.init.InitConfig;
import myFrameU.dataBackup.init.InitMavenImpl;

public class JavaMysql {
	/**
	 * 
	 * mysql数据备份 接收脚本名，并返回此路径
	 * 
	 * sql为备份的脚本名比如xxx.sql
	 * 
	 */
	// 备份数据库
	public static String backup(String sql) throws Exception {

		InitConfig ic = InitMavenImpl.ic;
		String username = ic.getUsername();
		String password = ic.getPassword();
		String mysqlpaths = ic.getMysqlpath();
		String databaseName = ic.getDatabaseName();
		String address = ic.getAddress();
		String sqlpath = ic.getSqlPath();
		
		File backupath = new File(sqlpath);
		if (!backupath.exists()) {
			backupath.mkdir();
		}

		StringBuffer sb = new StringBuffer();

		sb.append(mysqlpaths);
		sb.append("mysqldump ");
		sb.append("--opt ");
		sb.append("-h ");
		sb.append(address);
		sb.append(" ");
		sb.append("--user=");
		sb.append(username);
		sb.append(" ");
		sb.append("--password=");
		sb.append(password);
		sb.append(" ");
		sb.append("--lock-all-tables=true ");
		sb.append("--result-file=");
		sb.append(sqlpath);
		sb.append(sql);
		sb.append(" ");
		sb.append("--default-character-set=utf8 ");
		sb.append(databaseName);
		Runtime cmd = Runtime.getRuntime();
		try {
			System.out.println(sb.toString());
			Process p = cmd.exec(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sqlpath + sql;

	}

	// 读取属性值

	public static Properties getPprVue(String properName) throws Exception {
		InputStream in = JavaMysql.class.getResourceAsStream(properName);
		// InputStream inputStream =
		// JavaMysql.class.getClassLoader().getResourceAsStream(properName);
		Properties p = new Properties();

		try {
			p.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p;

	}

	// 还原备份
	public static void load(String filename) throws Exception {
		InitConfig ic = InitMavenImpl.ic;
		
		String root =ic.getUsername();
		String databaseName = ic.getDatabaseName();
		String pass = ic.getPassword();

		// 得到MYSQL的用户名密码后调用 mysql 的 cmd:
		String mysqlpaths = ic.getMysqlpath();
		String sqlpath = ic.getSqlPath();
		// String filepath = mysqlpaths + sqlpath + filename; // 备份的路径地址
		String filepath = filename; // 备份的路径地址
		// 新建数据库finacing
		String stmt1 = mysqlpaths + "mysqladmin -u " + root + " -p" + pass
				+ " create database finacing"; // -p后面加的是你的密码
		String stmt2 = mysqlpaths + "mysql -u " + root + " -p" + pass + "  "
				+ databaseName + " < " + filepath;
		String[] cmd = { "cmd", "/c", stmt2 };
		try {
			System.out.println("stmt1=" + stmt1);
			System.out.println("stmt2=" + stmt2);
			Runtime.getRuntime().exec(stmt1);
			Runtime.getRuntime().exec(cmd);
			System.out.println("数据已从 " + filepath + " 导入到数据库中");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {

		// backup2();
		try {
			load("db.sql");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// load("xx.sql");
	}

	public static void backup2() {

	}
}
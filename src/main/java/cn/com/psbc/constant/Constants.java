package cn.com.psbc.constant;

/**
 * 常量接口
 *
 *
 */
public interface Constants {

	/**
	 * Oracle配置相关的常量
	 */
	String JDBC_DRIVER = "jdbc.driver";
	String JDBC_DATASOURCE_SIZE = "jdbc.datasource.size";
	String JDBC_URL = "jdbc.url";
	String JDBC_USER = "jdbc.user";
	String JDBC_PASSWORD = "jdbc.password";
	String JDBC_USER_INSERT = "jdbc.user.insert";
	String JDBC_PASSWORD_INSERT = "jdbc.password.insert";

	/**
	 * mysql作业相关的常量
	 */
	String MYSQL_JDBC_DRIVER = "mysql.jdbc.driver";
	String MYSQL_JDBC_DATASOURCE_SIZE = "mysql.jdbc.datasource.size";
	String MYSQL_JDBC_URL = "mysql.jdbc.url";
	String MYSQL_JDBC_USER = "mysql.jdbc.user";
	String MYSQL_JDBC_PASSWORD = "mysql.jdbc.password";
	/**
	 * 任务相关的常量
	 */
	String ORACLE_INSERT_TABLENAME = "oracle.insert.tablename";
	String TABLE_DATABASE_NAME = "table.column.databasename";
	String TABLE_TABLE_NAME = "table.column.tablename";
	String ORACLE_TABLE_COLUMN_TABLESIZE = "oracle.table.column.tablesize";
	String ORACLE_TABLE_COLUMN_TABLEROWS = "oracle.table.column.tablerows";
	String ORACLE_TABLE_COLUMN_CDATE = "oracle.table.column.cdate";

	String FSIMAGE_TMP_PATH = "fsimage.tmp.path";
	String FSIMAGE_OUTPUT_PATH_FILE= "fsimage.output.path.file";
	String FSIMAGE_TXT_HDFS_PATH = "fsimage.txt.hdfs.path";
	String HDFS_ADDRESS_PATH= "hdfs.address.path";

}

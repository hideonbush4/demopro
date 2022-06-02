package com.example.demo.study.jdbc;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class DBUtils {
    /**
     * 获取Oracle数据库连接
     * @return connnetion
     */
    public static Connection getConnection(){
        Connection conn = null;

        //设置数据库的连接地址
//        String url  = "jdbc:oracle:thin:@localhost:1521:test";//改格式数据库连接时一直报错
        String url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = localhost)(PORT =1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME =test)))";
        String user = "crawlm";
        String passwd = "crawlm_Sd";

        try{
            //加载数据库驱动
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //获取数据库连接
            conn = DriverManager.getConnection(url,user,passwd);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("加载数据库启动失败！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取数据库连接失败！");
        }
        return conn;
    }

    /**
     * 关闭数据库连接方法
     * @param conn Connection
     * @param ps PreparedStatement
     * @param rs ResultSet
     */
    public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs){
        //关闭结果集对象
        try{
            if (rs != null){
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("关闭ResultSet失败！");
        }
        //关闭SQL语句预编译对象
        try {
            if (ps != null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("关闭PreparedStatement失败!");
        }
        //关闭数据库连接
        try{
            if (conn != null){
                conn.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("关闭Connetion失败！");
        }
    }

    /**
     * 获取数据库数据方法
     * @return
     */
    public static ResultSet getResultSet(){
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String querySql = "select KEYWORDS1 from KEYWORDS_ALL";

        try{
            conn = DBUtils.getConnection();
            if (conn == null){
                System.out.println("获取连接失败");
            }
            statement = conn.prepareStatement(querySql);
            rs = statement.executeQuery();
            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
            return rs;
        }finally {

            DBUtils.closeConnection(conn,statement,rs);
            return rs;
        }
    }

    /**
     * 更新数据库方法
     * map需要为LinkedHashMap,按顺序填充sql的？
     * String updateSql = "update  KEYWORDS_ALL set KEYWORDS2=? where  KEYWORDS1=?";
     * @param data
     * @return
     */
    public static int updateBatchData(List<Map<String, String>> data, String updateSql){
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        //更新SQL语句
        try{
            conn = DBUtils.getConnection();
            if (conn == null){
                System.out.println("获取连接失败");
            }

            //关闭自动提交事务
            conn.setAutoCommit(false);
            //创建一个 PreparedStatement 对象来将参数化的 SQL语句发送到数据库
            statement = conn.prepareStatement(updateSql);
            //将一组参数添加到此 PreparedStatement 对象的批处理命令中
            for (Map<String, String> map : data) {
                int i = 0;
                for (String s : map.keySet()) {
                    i++;
                    statement.setString(i, map.get(s));
                }
                statement.addBatch();
            }
            //执行更新操作
            statement.executeBatch();
            //语句执行完毕，提交事务
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(1);
            return -1;
        }finally {
            DBUtils.closeConnection(conn,statement,rs);
            System.out.println(2);
            return -2;
        }

    }
}

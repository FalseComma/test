import annotation.Column;
import annotation.Table;
import beans.Book;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReflectTest {

    @Test
    public void test1() throws Exception {
        Book book = new Book();
        book.setName("十万个为什么");
        book.setId(1);

        List list = select(book);
        for (Object obj : list) {
            System.out.println(JSON.toJSONString(obj));
        }
    }

    @Test
    public void test2() throws Exception {
        Book book = new Book("GOK教育", 20, 100, 30);
        System.out.println("插入前查询该书：");
        List list = select(book);
        for (Object obj : list) {
            System.out.println(JSON.toJSONString(obj));
        }
        insert(book);
        System.out.println("插入后查询该书：");
        List list2 = select(book);
        for (Object obj : list2) {
            System.out.println(JSON.toJSONString(obj));
        }
    }

    public int insert(Object obj) throws Exception {
        int out = 0;
        List<Object> params = new ArrayList<Object>();
        Class cc = obj.getClass();
        if (!cc.isAnnotationPresent(Table.class)) {
            throw new Exception("没有表名注解");
        }

        Table table = (Table) cc.getAnnotation(Table.class);
        String tableName = table.value();

        StringBuffer sql = new StringBuffer("insert into " + tableName);
        boolean flag = true;
        int count = 0;

        Field[] fields = cc.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            if (field.getAnnotation(Column.class) != null) {
                Column column = (Column) field.getAnnotation(Column.class);
                String columnName = column.value();

                Object value = field.get(obj);
                if (value != null) {
                    if (value.getClass() == Integer.class || value.getClass() == Double.class) {
                        if (value.toString().equals("0") || value.toString().equals("0.0"))
                            continue;
                    }
                    if (flag) {
                        sql.append(" (");
                        flag = false;
                    } else {
                        sql.append(",");
                    }
                    sql.append(columnName);
                    count++;
                    params.add(value);
                }
            }
        }

        sql.append(") values (");
        if (count == 0) {
            throw new Exception("没有可插入的数据！");
        } else {
            for (int i = 0; i < count; i++) {
                sql.append("?");
                if (i + 1 != count) {
                    sql.append(",");
                }
            }
        }
        sql.append(")");
        System.out.println(sql);
        // 执行数据库语句
        Connection conn = getConn();
        PreparedStatement ps = getPs(sql.toString(), params, conn);
        out = ps.executeUpdate();

        return out;
    }

    /*
     * 公共查询
     * */
    public List<Object> select(Object obj) throws Exception {
        List<Object> out = new ArrayList<Object>();
        List<Object> params = new ArrayList<Object>();

        Class cc = obj.getClass();
        if (!cc.isAnnotationPresent(Table.class)) {
            throw new Exception("没有表名注解");
        }

        Table table = (Table) cc.getAnnotation(Table.class);
        String tableName = table.value();

        StringBuffer sql = new StringBuffer("select * from " + tableName);
        boolean flag = true;

        Field[] fields = cc.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            if (field.getAnnotation(Column.class) != null) {
                Column column = (Column) field.getAnnotation(Column.class);
                String columnName = column.value();

                Object value = field.get(obj);
                if (value != null) {
                    if (value.getClass() == Integer.class || value.getClass() == Double.class) {
                        if (value.toString().equals("0") || value.toString().equals("0.0"))
                            continue;
                    }
                    if (flag) {
                        sql.append(" where ");
                        flag = false;
                    } else {
                        sql.append(" and ");
                    }
                    sql.append(columnName + "=?");
                    params.add(value);
                }
            }
        }

        System.out.println(sql);
        // 执行数据库语句
        Connection conn = getConn();
        PreparedStatement ps = getPs(sql.toString(), params, conn);
        ResultSet rs = ps.executeQuery();
        out = resolveRS(rs, obj.getClass());
        return out;
    }

    /*
     * 获得连接
     * */
    public Connection getConn() {
        String url = "jdbc:mysql://localhost:3306/booksale?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        String name = "root";
        String psw = "199876";
        String driver = "com.mysql.cj.jdbc.Driver";
        try {
            //加载驱动
            Class.forName(driver);
            //或的连接
            Connection conn = DriverManager.getConnection(url, name, psw);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 获得预编译对象
     * */
    public PreparedStatement getPs(String sql, List values, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        if (values != null) {
            for (int i = 0; i < values.size(); i++) {
                ps.setObject(i + 1, values.get(i));
            }
        }

        return ps;
    }

    /*
     * 解析结果集
     * */
    public List<Object> resolveRS(ResultSet rs, Class cc) throws Exception {
        List<Object> objects = new ArrayList<>();

        while (rs.next()) {
            Object obj = cc.newInstance();

            Field[] fields = cc.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }
                Column column = field.getAnnotation(Column.class);

                Object value = rs.getObject(column.value());
                field.set(obj, value);
            }

            objects.add(obj);
        }
        return objects;
    }
}

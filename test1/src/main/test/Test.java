import beans.Book;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class Test {

    private SqlSession sqlSession;

    @Before
    public void init(){
        InputStream inputStream = Test1.class.getClassLoader().getResourceAsStream("MybatisConfig.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(inputStream);
        sqlSession = factory.openSession();
    }

    @org.junit.Test
    public void test(){
        Test1 test = new Test1();
        System.out.println(sqlSession);

        List<Book> bookList = sqlSession.selectList("dao.BookDao.getBooks");
        for (Book b : bookList) {
            System.out.println(JSON.toJSONString(b));
        }
    }

}

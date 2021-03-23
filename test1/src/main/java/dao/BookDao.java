package dao;

import beans.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao {
    public int addBook(Book book);// 增

    public int deleteBook(Integer id);//删

    public int updateBook(Book book);//改

    public List<Book> getBooks();//查询所有的书

    public List<Book> getBooksByID(Integer id);//根据id查找书

    public List<Book> getBooksByName(String name);//根据书名查找书

    /*
    当有多个参数时，需要使用@Param(name)来确保键值对可以一一对应
     */
    public List<Book> getBooksByNameAndID(@Param("name") String name, @Param("id") Integer id);//根据书名查找书

}

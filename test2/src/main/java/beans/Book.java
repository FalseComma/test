package beans;

import annotation.Column;
import annotation.Table;
import lombok.Data;

@Data
@Table("books")
public class Book {
    @Column("id")
    private Integer id;
    @Column("name")
    private String name;//书名
    @Column("price")
    private double price;//单价
    @Column("repertory")
    private int repertory;//库存量
    @Column("sale")
    private int sale;//销售量

    public Book() {
    }

    public Book(String name, double price, int repertory, int sale) {
        this.name = name;
        this.price = price;
        this.repertory = repertory;
        this.sale = sale;
    }

}

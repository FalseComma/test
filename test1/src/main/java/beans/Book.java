package beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

    private Integer id;
    private String name;//书名
    private double price;//单价
    private int repertory;//库存量
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

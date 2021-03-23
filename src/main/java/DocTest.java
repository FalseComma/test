import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.ParagraphStyle;
import com.spire.doc.fields.TextRange;

import java.awt.*;

public class DocTest {

    public static void main(String[] args) {
        //创建Word文档
        Document document = new Document();
        Section sec = document.addSection();

        //添加段落
        Paragraph paragraph = sec.addParagraph();
        paragraph.appendText("java操作Word测试，接下来是");

        //在段落中添加文字，并返回TextRange对象
        TextRange tr = paragraph.appendText("带删除线的文字");

        //通过TextRange对象设置文字的样式
        tr.getCharacterFormat().isStrikeout(true);

        //设置文字字号
        paragraph.appendText("，这是");
        tr = paragraph.appendText("加大的文字");
        tr.getCharacterFormat().setFontSize(20);

        //设置文字颜色
        paragraph.appendText("，这是");
        tr = paragraph.appendText("红色的文字");
        tr.getCharacterFormat().setTextColor(Color.red);

        //设置文字斜体加粗
        paragraph.appendText("，这是");
        tr = paragraph.appendText("斜体加粗的文字");
        tr.getCharacterFormat().setBold(true);
        tr.getCharacterFormat().setItalic(true);

        //设置整个段落的字体和字间距
        ParagraphStyle style1 = new ParagraphStyle(document);
        style1.setName("style");
        style1.getCharacterFormat().setFontName("宋体");
        style1.getCharacterFormat().setCharacterSpacing(2.5f);
        document.getStyles().add(style1);
        paragraph.applyStyle(style1.getName());

        //保存文档，第一个参数是存储的路径和文件名，如果前面的路径没有写，默认保存到此项目的根目录下
        document.saveToFile("javaDOCX.docx", FileFormat.Docx);
    }
}

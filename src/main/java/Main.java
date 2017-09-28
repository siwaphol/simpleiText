import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.PdfEncodings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Main {

    public static String DEST = "results/filled_form.pdf";
    public static final String SRC = "results/200360_WHT1.pdf";
    public static final String XFDF = "results/input.xfdf";
    public static final String FONT = "fonts/THSarabun.ttf";

    public static void main(String[] args) throws IOException {
        new Main().manipulatePdf(DEST);
        new Main().readXML();
    }

    protected void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.setGenerateAppearance(true);

        PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
        // TODO-nong loop and insert value here
        form.getField("Text1.2").setValue("สวัสดีครับ", font, 12f);
        form.getField("Text2.2").setValue("10000000.00");
        form.getField("Radio Button1").setValue("8");

        form.flattenFields();
        pdfDoc.close();
    }

    protected void readXML() throws IOException {
        try{
            File inputFile = new File(XFDF);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}

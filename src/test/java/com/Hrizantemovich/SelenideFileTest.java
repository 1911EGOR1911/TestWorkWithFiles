package com.Hrizantemovich;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import  net.lingala.zip4j.ZipFile;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFileTest {

    @Test
    void testTxtFile() throws Exception {
        String result;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("new.txt")) {
            result = new String(is.readAllBytes(), "UTF-8");
        }
        assertThat(result).contains("new");
    }

    @Test
    void testXlsFile() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("new.xlsx")) {
            XLS parsedXls = new XLS(stream);
            assertThat(parsedXls.excel.getSheetAt(0).getRow(0)
                    .getCell(0).getStringCellValue()).contains("new");
        }
    }

    @Test
    void testPdfFile() throws Exception {
        PDF pdfParsed = new PDF(new File("src/test/resources/IntelliJIDEA_ReferenceCard.pdf"));
        assertThat(pdfParsed.numberOfPages).isEqualTo(2);
    }

    @Test
    void testZipFile() throws Exception {
        ZipFile zipFile = new ZipFile("./src/test/resources/test.zip");
        if (zipFile.isEncrypted())
            zipFile.setPassword("qwerty".toCharArray());
        zipFile.extractAll("./src/test/resources/");
        try (FileInputStream stream = new FileInputStream("./src/test/resources/test.txt")) {
            String result = new String(stream.readAllBytes(), "UTF-8");
            assertThat(result).contains("Testing testing!");
        }
    }

    @Test
    void testDOCXFile() throws Exception {
        try (InputStream file = getClass().getClassLoader().getResourceAsStream("test.docx")) {
            XWPFDocument document = new XWPFDocument(file);
            XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(document);
            String docText = xwpfWordExtractor.getText();
            assertThat(docText.contains("test"));
        }
    }
}



package com.Hrizantemovich;

import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFileTest {

    @Test
    void txtFileTest() throws Exception{
        String result;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("new.txt")) {
            result = new String(is.readAllBytes(),"UTF-8");
        }
        assertThat(result).contains("new");
    }
    @Test
    void checkXlsFile() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("new.xlsx")) {
            XLS parsedXls = new XLS(stream);
            assertThat(parsedXls.excel.getSheetAt(0).getRow(0)
                    .getCell(0).getStringCellValue()).contains("new");
        }
    }
}

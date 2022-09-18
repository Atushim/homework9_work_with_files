package guru.qa;

import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.opencsv.CSVReader;
import guru.qa.domain.SteamUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codeborne.pdftest.PDF;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipFile;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;


public class FileParseTest {

    ClassLoader classLoader = FileParseTest.class.getClassLoader();

    String pdfName = "File_pdf.pdf",
            csvName = "File_csv.csv",
            xlsxName = "File_xls.xlsx";

    InputStream getFileFromZip(String fileName) throws Exception {
        File zipFile = new File("src/test/resources/zip_files.zip");
        ZipFile zip = new ZipFile(zipFile);
        return zip.getInputStream(zip.getEntry(fileName));
    }

    @DisplayName("тест pdf")
    @Test
    void parsePdf() throws Exception {
        try (InputStream pdfFileStream = getFileFromZip(pdfName)) {
            PDF pdf = new PDF(pdfFileStream);
            assertThat(pdf.text).contains("Сидоров Олег 03.03.2023");

        }
    }

    @DisplayName("тест xlsx")
    @Test
    void parsXlsx() throws Exception {
        try (InputStream xlsxFileStream = getFileFromZip(xlsxName)){
            XLS xls = new XLS(xlsxFileStream);
            assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue().contains("Сергей"));
        }

    }

    @DisplayName("тест csv")
    @Test
    void parsCsv() throws Exception {
        try (InputStream csvFileStream = getFileFromZip(csvName)) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(csvFileStream, UTF_8));
            List<String[]> csv = csvReader.readAll();
            assertThat(csv).contains(
                    new String[]{"Урок 1", "Алгебра",	"19.09.2022"}
            );
        }

    }

    @DisplayName("тест JSON")
    @Test
    void jsonJackson() throws Exception{

        InputStream is = classLoader.getResourceAsStream("SteamUser.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        SteamUser jsonObject = mapper.readValue(is, SteamUser.class);

       assertThat(jsonObject.getUserName()).isEqualTo("Atush");
        assertThat(jsonObject.getAge()).isEqualTo(25);
        assertThat(jsonObject.getCountry()).isEqualTo("Russia");
        assertThat(jsonObject.getStatusOnline()).isEqualTo(false);
        assertThat(jsonObject.getGamesCollections()).contains("Detroit:_Become_Human", "Red_Dead_Redemption_2","Dota2");
        System.out.println();
    }


}


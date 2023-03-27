package ukr.nure.itm.inf.speeddivingservice.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class CSVReaderUtil {
    public static String CSV_REGEX = ";";

    public static List<String[]> readAllLinesFromMultipartFile(final MultipartFile file) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            } catch (CsvException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CSVReaderUtil() {
        //empty to avoid instantiating this constant class
    }
}

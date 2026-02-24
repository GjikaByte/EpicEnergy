package epicode.epicenergy.services;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import epicode.epicenergy.entities.Comune;
import epicode.epicenergy.entities.Provincia;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinceImportService {
    public List<Provincia> load(String filename) {

        List<Provincia> province = new ArrayList<>();

        try (
                InputStream is = getClass()
                        .getClassLoader()
                        .getResourceAsStream(filename)
        ) {

            if (is == null) {
                throw new RuntimeException("File non trovato nel classpath: " + filename);
            }

            try (
                    CSVReader reader = new CSVReaderBuilder(
                            new InputStreamReader(is)
                    )
                            .withSkipLines(1)
                            .withCSVParser(new CSVParserBuilder()
                                    .withSeparator(';')
                                    .build())
                            .build()
            ) {

                String[] riga;

                while ((riga = reader.readNext()) != null) {
                    province.add(new Provincia(
                            riga[0],
                            riga[1],
                            riga[2]
                    ));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return province;
    }
}

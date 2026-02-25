package epicode.epicenergy.services;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import epicode.epicenergy.entities.Comune;
import epicode.epicenergy.entities.Provincia;
import epicode.epicenergy.repositories.ProvinceRepository;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ComuneImportService {

    private final ProvinceRepository provinceRepository;

    public ComuneImportService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public List<Comune> load(String filename) {
        List<Comune> comuni = new ArrayList<>();

        Map<String, Provincia> mappaProvince = provinceRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        p -> p.getProvincia(),
                        p -> p,
                        (a, b) -> a
                ));

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filename)) {

            if (is == null) throw new RuntimeException("File non trovato: " + filename);

            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is))
                    .withSkipLines(1)
                    .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                    .build();

            String[] riga;
            while ((riga = reader.readNext()) != null) {
                String nomeGrezzo = riga[3].trim();
                String nomeFix = comuneProvinciaFix(nomeGrezzo);

                Provincia provincia = mappaProvince.get(nomeFix);
                if (provincia == null) {
                    throw new RuntimeException("Provincia non trovata: " + nomeGrezzo + " -> " + nomeFix);
                }

                comuni.add(new Comune(riga[0], riga[1], riga[2], provincia));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return comuni;
    }
    private String comuneProvinciaFix(String s) {
        return switch (s) {
            case "Ascoli Piceno" -> "Ascoli-Piceno";
            case "Bolzano/Bozen" -> "Bolzano";
            case "Forlì-Cesena" -> "Forli-Cesena";
            case "La Spezia" -> "La-Spezia";
            case "Monza e della Brianza" -> "Monza-Brianza";
            case "Pesaro e Urbino" -> "Pesaro-Urbino";
            case "Reggio Calabria" -> "Reggio-Calabria";
            case "Reggio nell'Emilia" -> "Reggio-Emilia";
            case "Valle d'Aosta/Vallée d'Aoste" -> "Aosta";
            case "Verbano-Cusio-Ossola" -> "Novara";
            case "Vibo Valentia" -> "Vibo-Valentia";
            case "Sud Sardegna" -> "Cagliari";
            default -> s;
        };
    }
}
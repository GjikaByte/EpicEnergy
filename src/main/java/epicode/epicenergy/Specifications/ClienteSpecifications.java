package epicode.epicenergy.Specifications;

import epicode.epicenergy.entities.Cliente;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ClienteSpecifications {

    public static Specification<Cliente> ragioneSociale(String ragioneSociale) {
        return (root, query, cb) ->
                ragioneSociale == null ? null :
                        cb.like(cb.lower(root.get("ragioneSociale")), "%" + ragioneSociale.toLowerCase() + "%");
    }

    public static Specification<Cliente> fatturatoMaggioreDi(Double importo) {
        return (root, query, cb) ->
                importo == null ? null :
                        cb.greaterThanOrEqualTo(root.get("fatturatoAnnuale"), importo);
    }

    public static Specification<Cliente> inseritoDopo(LocalDate data) {
        return (root, query, cb) ->
                data == null ? null :
                        cb.greaterThanOrEqualTo(root.get("dataInserimento"), data);
    }

    public static Specification<Cliente> ultimoContattoPrima(LocalDate data) {
        return (root, query, cb) ->
                data == null ? null :
                        cb.lessThanOrEqualTo(root.get("dataUltimoContatto"), data);
    }
}

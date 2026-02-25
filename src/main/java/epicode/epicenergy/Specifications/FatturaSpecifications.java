package epicode.epicenergy.Specifications;

import epicode.epicenergy.entities.Cliente;
import epicode.epicenergy.entities.Fattura;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FatturaSpecifications {

    public static Specification<Fattura> clienteContains(Cliente cliente) {
        return (root, query, cb) ->
                cliente == null ? null :
                        cb.like(cb.lower(root.get("cliente")), "%" + cliente.getNomeContatto().toLowerCase() + "%");
    }

    public static Specification<Fattura> statoCliente(Double importo) {
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

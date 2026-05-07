package com.github.caioleria.ms.pagamentos.tests;



import com.github.caioleria.ms.pagamentos.entities.Pagamentos;
import com.github.caioleria.ms.pagamentos.entities.Status;

import java.math.BigDecimal;

public class Factory {

    public static Pagamentos createPagamento() {
        Pagamentos pagamento = new Pagamentos(1L, BigDecimal.valueOf(32.25),
                "Briannede Tarth", "7418529637423612",
                "07/15", "345", Status.CRIADO, 1L);
        return pagamento;
    }
    public static Pagamentos createPagamentoSemId() {
        Pagamentos pagamento = createPagamento();
        pagamento.setId(null);
        return pagamento;
    }

}

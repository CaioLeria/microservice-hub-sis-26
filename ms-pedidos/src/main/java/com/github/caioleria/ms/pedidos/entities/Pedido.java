package com.github.caioleria.ms.pedidos.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of ="id")
@Entity
@Table(name = "tb_pedido") // Nome fixado no singular
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 11)
    private String cpf;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal valorTotal;

    //mappedBy deve ser "pedido" porque é o nome do atributo na classe ItemDoPedido
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemDoPedido> itemsDoPedido = new ArrayList<>();

    public void calcularValorTotal() {
        this.valorTotal = this.itemsDoPedido.stream()
                .map(i -> i.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
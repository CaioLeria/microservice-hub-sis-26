package com.github.caioleria.ms.pedidos.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_item_do_pedido")
public class ItemDoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal precoUnitario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id") // Esta coluna ligará ao id de tb_pedido
    private Pedido pedido;
}
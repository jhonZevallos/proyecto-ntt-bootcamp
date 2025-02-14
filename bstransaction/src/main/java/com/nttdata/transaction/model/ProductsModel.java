package com.nttdata.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductsModel {

    private String type;
    private String name;
    private List<Product> accounts;
    private List<Product> credits;
    private List<Product> creditCards;

}

package edu.miu.cs.cs489appsd.lab1a.productmgmtapp.service;

import edu.miu.cs.cs489appsd.lab1a.productmgmtapp.model.Product;

import java.util.List;

public final class CsvFormatter implements ProductFormatter{
    @Override
    public String format(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        sb.append("ProductId,Name,DateSupplied,QuantityInStock,UnitPrice\n");

        products.forEach(p -> sb.append("%d,%s,%s,%d,%.2f\n".formatted(
                p.productId(),
                p.name(),
                p.dateSupplied(),
                p.quantityInStock(),
                p.unitPrice()
        )));

        return sb.toString();
    }
}

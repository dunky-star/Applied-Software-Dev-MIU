package edu.miu.cs.cs489appsd.lab1a.productmgmtapp.service;

import edu.miu.cs.cs489appsd.lab1a.productmgmtapp.model.Product;

import java.util.List;

public final class XmlFormatter implements ProductFormatter {
    @Override
    public String format(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\"?>\n<products>\n");

        products.forEach(p -> sb.append("  <product ")
                .append("productId=\"").append(p.productId()).append("\" ")
                .append("name=\"").append(p.name()).append("\" ")
                .append("dateSupplied=\"").append(p.dateSupplied()).append("\" ")
                .append("quantityInStock=\"").append(p.quantityInStock()).append("\" ")
                .append("unitPrice=\"").append(String.format("%.2f", p.unitPrice())).append("\" />\n"));

        sb.append("</products>");
        return sb.toString();
    }
}

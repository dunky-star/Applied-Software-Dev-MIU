package edu.miu.cs.cs489appsd.lab1a.productmgmtapp.service;

import edu.miu.cs.cs489appsd.lab1a.productmgmtapp.model.Product;

import java.util.List;

public final class JsonFormatter implements ProductFormatter{
    @Override
    public String format(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            sb.append("  {\n")
                    .append("    \"productId\": ").append(p.productId()).append(",\n")
                    .append("    \"name\": \"").append(p.name()).append("\",\n")
                    .append("    \"dateSupplied\": \"").append(p.dateSupplied()).append("\",\n")
                    .append("    \"quantityInStock\": ").append(p.quantityInStock()).append(",\n")
                    .append("    \"unitPrice\": ").append(String.format("%.2f", p.unitPrice())).append("\n")
                    .append("  }");
            if (i != products.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}

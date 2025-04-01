package edu.miu.cs.cs489appsd.lab1a.productmgmtapp.service;

import edu.miu.cs.cs489appsd.lab1a.productmgmtapp.model.Product;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public final class JsonFormatter implements ProductFormatter{
    @Override
    public String format(List<Product> products) {
        JSONArray jsonArray = new JSONArray();

        products.forEach(product -> {
            JSONObject jsonObject = new JSONObject()
                    .put("productId", product.productId())
                    .put("name", product.name())
                    .put("dateSupplied", product.dateSupplied())
                    .put("quantityInStock", product.quantityInStock())
                    .put("unitPrice", String.format("%.2f", product.unitPrice()));
            jsonArray.put(jsonObject); // side effect
        });

        return  jsonArray.toString(2);
    }
}

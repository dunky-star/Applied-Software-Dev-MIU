package edu.miu.cs.cs489appsd.lab1a.productmgmtapp.service;

import edu.miu.cs.cs489appsd.lab1a.productmgmtapp.model.Product;

import java.util.List;

public sealed interface ProductFormatter permits CsvFormatter, JsonFormatter, XmlFormatter{
    String format(List<Product> products);
}

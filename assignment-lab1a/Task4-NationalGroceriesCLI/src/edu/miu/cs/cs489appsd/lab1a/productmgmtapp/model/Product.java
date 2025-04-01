package edu.miu.cs.cs489appsd.lab1a.productmgmtapp.model;

import java.time.LocalDate;

public record Product(long productId, String name, LocalDate dateSupplied, int quantityInStock, double unitPrice) {
}

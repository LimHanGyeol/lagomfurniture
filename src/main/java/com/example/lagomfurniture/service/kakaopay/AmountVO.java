package com.example.lagomfurniture.service.kakaopay;

import lombok.Data;

@Data
public class AmountVO {
    private Integer total, tax_free, vat, point, discount;

    public Integer getTotal() {
        return total;
    }

    public Integer getTax_free() {
        return tax_free;
    }

    public Integer getVat() {
        return vat;
    }

    public Integer getPoint() {
        return point;
    }

    public Integer getDiscount() {
        return discount;
    }
}

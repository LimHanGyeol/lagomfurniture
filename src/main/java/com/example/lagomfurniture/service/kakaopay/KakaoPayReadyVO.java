package com.example.lagomfurniture.service.kakaopay;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
public class KakaoPayReadyVO {

    //RESPONSE
    private String tid, next_redirect_pc_url;
    private Date created_at;
    private String partner_orderid, partner_userid;

    public String getNext_redirect_pc_url() {
        return next_redirect_pc_url;
    }

    //결제 한 건에 대한 고유번호. 20자리
    public String getTid() {
        return tid;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public String getPartner_orderid() {
        return partner_orderid;
    }

    public String getPartner_userid() {
        return partner_userid;
    }
}

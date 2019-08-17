package com.example.lagomfurniture.service.kakaopay;

import com.example.lagomfurniture.model.OrderInfo;
import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.OrderInfoRepository;
import com.example.lagomfurniture.repository.ProductRepository;
import com.example.lagomfurniture.repository.UserRepository;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import com.google.gson.JsonArray;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *  2019.08.10. 19:14
 *  Ja Eun
 */
@Service
public class KakaoPay {

    private static final String HOST = "https://kapi.kakao.com";
    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;

    @Autowired
    private OrderInfoRepository orderinfoRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public String kakaoPayReady(String productPrice, String productName, String productId, String sessionedUser) {

        System.out.println("KAKAOPAY READY price: " + productPrice + "name: " + productName + "productId" + productId + "sessionedUser" + sessionedUser);


        RestTemplate restTemplate = new RestTemplate();


        // 서버로 요청할 HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "1d9076b78d6e5e5a34cbf86ac0c0419c");
        headers.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        //서버로 요청할 BODY - 결제로 지정할 데이터
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "id"); //결제건에 대한 가맹점의 주문번호. -- productId 넣음
        params.add("partner_user_id", "user"); //가맹점에서 사용자를 구분할 수 있는 id. --SessionId 넣음
        params.add("item_name", productName);
        params.add("quantity", "1");
        params.add("total_amount", productPrice);
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://localhost:8080/payment/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8080/payment/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8080/payment/kakaoPayFail");

        //HEADER 와 BODY 연결
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            /**
             * REST TEMPLATE 이용해서 데이터 전달
             * POST방식으로 HOST+"/v1/payment/ready"에 HEADER+BODY 정보를 보낸다
             * 요청이 성공으로 떨어지면 응답 정보를 보내준다.
             * 응답 받는 객체 => KakaoPayReadyVO.class
             *
             * 요청이 성공하면 응답 바디에 JSON 객체로 아래의 값을 포함
             */

            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

            System.out.println("kakaoPayReady : " + kakaoPayReadyVO);
            System.out.println("kakaoPayReady : " + body);

            System.out.println("이동되는 주소 : "+ kakaoPayReadyVO.getNext_redirect_pc_url());
            //Redircet URL 불러와 결제가 완료되면 주소로 v가게 됨
            return kakaoPayReadyVO.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "/pay";
    }


    /**
     *  결제 정보 받아오기
     *  결제 완료 후 받아오는 pg_token과 tid 필수
     */

    public KakaoPayApprovalVO kakaoPayInfo(String pg_token,HttpSession session) {
        System.out.println(":::::::::::::::KakaoPayInfoVO::::::::::::::");
        //HEADER
        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "1d9076b78d6e5e5a34cbf86ac0c0419c");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", "id");
        params.add("partner_user_id","user");
        params.add("pg_token", pg_token);

//        params.add("total_amount","10000"); //ready 금액과 동일해야 함 !!!!!!!!!!! 여기 고쳐야 돼!!!!!!!!!!!!!!!!1
//        System.out.println("kakaopayreadyvo partner_order_id : " + kakaoPayReadyVO.getPartner_orderid());
//        System.out.println("kakaopayreadyvo partner_user_id : " + kakaoPayReadyVO.getPartner_userid());

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {

            /**
             * 2019.08.13 13:13
             * Ja Eun
             */
            // KakaoPayApprovalVO -> 응답 정보 받는 곳
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            System.out.println("" + kakaoPayApprovalVO);

            System.out.println("kakaoPayApprovalVO name : " + kakaoPayApprovalVO.getItem_name());
            System.out.println("kakaoPayApprovalVO approved_at : " + kakaoPayApprovalVO.getApproved_at());
            System.out.println("kakaoPayApprovalVO price : " + kakaoPayApprovalVO.getPayload());
            System.out.println("kakaoPayApprovalVO orderid : " + kakaoPayApprovalVO.getPartner_order_id()); //ProducctId
            System.out.println("kakaoPayApprovalVO userid : " + kakaoPayApprovalVO.getPartner_user_id()); //SessionedUser
            System.out.println("kakaoPayApprovalVO quantity : " + kakaoPayApprovalVO.getQuantity());
            System.out.println("kakaoPayApprovalVO body : " +body.toString());

            String price = kakaoPayApprovalVO.getAmount().getTotal().toString();
            System.out.println("kakaoPayApprovalVO amount" + price);

            String name = kakaoPayApprovalVO.getItem_name();
            String quantity = kakaoPayApprovalVO.getQuantity().toString();

            //Date to String
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(kakaoPayApprovalVO.getCreated_at());

            //DB INSERT
            OrderInfo orderinfo = new OrderInfo(currentDate,price,quantity);

            System.out.println("order" + orderinfo);
            orderinfoRepository.save(orderinfo); //order_info save data


            //세션 유저 확인
            User user = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
            String sessionUser = user.getUserEmail();
            System.out.println("kakaoPayApprovalVO 세션 유저 확인 : " + sessionUser);

//            List<Product> productList = productRepository.findByProductId(Long.parseLong(kakaoPayApprovalVO.getPartner_order_id()));
//
//            OrderInfo orderInfo2 = new OrderInfo(currentDate,price,quantity,productList);
//            model.addAttribute("orderinfo",orderInfo2);

            return kakaoPayApprovalVO;

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


}

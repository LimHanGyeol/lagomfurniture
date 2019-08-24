package com.example.lagomfurniture.service.kakaopay;

import com.example.lagomfurniture.model.OrderDetail;
import com.example.lagomfurniture.model.OrderInfo;
import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.OrderDetailRepository;
import com.example.lagomfurniture.repository.OrderInfoRepository;
import com.example.lagomfurniture.repository.ProductRepository;
import com.example.lagomfurniture.repository.UserRepository;
import com.example.lagomfurniture.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 2019.08.10. 19:14
 * Ja Eun
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
    @Autowired
    private OrderDetailRepository orderDetailRepository;

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
        params.add("partner_order_id", productId); //결제건에 대한 가맹점의 주문번호. -- productId 넣음
        params.add("partner_user_id", sessionedUser); //가맹점에서 사용자를 구분할 수 있는 id. --SessionId 넣음
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

            System.out.println("이동되는 주소 : " + kakaoPayReadyVO.getNext_redirect_pc_url());
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
     * 결제 정보 받아오기
     * 결제 완료 후 받아오는 pg_token과 tid 필수
     */

    public KakaoPayApprovalVO kakaoPayInfo(String pg_token, HttpSession session) {
        System.out.println(":::::::::::::::KakaoPayInfoVO::::::::::::::");

        //세션에 저장된 PRODUCT_SESSION_ID
        String productSessionId = (String) session.getAttribute(HttpSessionUtils.PRODUCT_SESSION_ID);
        System.out.println("KakaoPayApprovalVO PRODUCT_SESSION_ID 확인 :  " + productSessionId);

        //세션에 저장된 PRODUCT_SESSION_NAME
//        String productSessionName = (String) session.getAttribute(HttpSessionUtils.PRODUCT_SESSION_NAME);
//        System.out.println("KakaoPayApprovalVO PRODUCT_SESSION_ID 확인 :  " + productSessionName);

        //세션에 저장된 USER_SESSION_KEY
        User user = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        String sessionUserEmail = user.getUserEmail();

        //세션에 저장된 ORDER_DETAIL
        OrderDetail orderDetail = (OrderDetail) session.getAttribute(HttpSessionUtils.ORDER_DETAIL);


        System.out.println("kakaoPayApprovalVO 세션 유저 확인 : " + sessionUserEmail);

        User user1 = userRepository.findByUserEmail(sessionUserEmail);
        Long userid = user1.getId();
        System.out.println("kakaoPayApprovalVO 세션 유저 확인 아이디 : " + userid);

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
//        params.add("payload", productThumbnail);
//        params.add("item_code",productCategory);
        params.add("partner_order_id", productSessionId);
        params.add("partner_user_id", sessionUserEmail);
        params.add("pg_token", pg_token);



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
            System.out.println("kakaoPayApprovalVO orderid : " + kakaoPayApprovalVO.getPartner_order_id()); //ProducctId
            System.out.println("kakaoPayApprovalVO userid : " + kakaoPayApprovalVO.getPartner_user_id()); //SessionedUser
            System.out.println("kakaoPayApprovalVO quantity : " + kakaoPayApprovalVO.getQuantity());
            System.out.println("kakaoPayApprovalVO category: " + kakaoPayApprovalVO.getItem_code()); //category
            System.out.println("kakaoPayApprovalVO body : " + body.toString());

            String price = kakaoPayApprovalVO.getAmount().getTotal().toString();
            System.out.println("kakaoPayApprovalVO amount: " + price);
            System.out.println("kakaoPayApprovalVO 한 건당 고유번호 Tid : " + kakaoPayApprovalVO.getTid());

            String quantity = kakaoPayApprovalVO.getQuantity().toString();
            String ordernum = kakaoPayApprovalVO.getTid();

            //Date to String
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(kakaoPayApprovalVO.getCreated_at());

            //결제 내용과 상품 번호 JOIN TABLE
            List<Product> productList = productRepository.findByProductId(Long.parseLong(kakaoPayApprovalVO.getPartner_order_id()));

            //결제 내용과 유저 이메일 JOIN TABLE
            List<User> orderUser = userRepository.findByid(userid);
            System.out.println("order유저 확인 : " + orderUser);

            //ORDER INFO DB INSERT
            OrderInfo orderInfo_product_user = new OrderInfo(ordernum, currentDate, price, quantity, productList, orderUser);
            orderinfoRepository.save(orderInfo_product_user);

            //ORDER DETAIL DB INSERT
            orderDetailRepository.save(orderDetail);


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

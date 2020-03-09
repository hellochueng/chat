package org.lzz.chat.test;

import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import org.lzz.chat.mapreduce.Util;

import java.util.HashMap;
import java.util.Map;

public class test4 {

    public static void main(String[] args) {
//        Double a =10.0;
//        Double b = (1D/3D)*a;
//        System.out.println(a);
//        System.out.println(b);
        try {
            Stripe.apiKey = "sk_test_g5JV8EVtc482VDbBH7z67xbL00isIARHAh";
            try {
                Charge c = stripe(token().getId());
                System.out.println(c);
                System.out.println(c.getStatus());
                System.out.println(c.getPaymentIntent());
                System.out.println(c.getFailureCode());
                System.out.println(c.getPaid());
            }catch (CardException c){
                System.out.println("信用卡");
            }

        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

    public static Charge stripe(String token){


        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 4000);
        chargeMap.put("currency", "hkd");
        chargeMap.put("description", "Charge for jenny.rosen@example.com");
        chargeMap.put("source", token); // obtained via Stripe.js

        try {
            return Charge.create(chargeMap);
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Token token() throws StripeException {

        Map<String, Object> tokenParams = new HashMap<>();
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", "4242424242424241");
        cardParams.put("exp_month", 8);
        cardParams.put("exp_year", 2020);
        cardParams.put("cvc", "314");
        tokenParams.put("card", cardParams);
        return Token.create(tokenParams);
    }

    public static Customer customer(String token){
        Map<String, Object> customerParams = new HashMap<String, Object>();

        customerParams.put("description", "Customer for chao");
        customerParams.put("source", token);

        Customer c = null;
        try {
            c = Customer.create(customerParams);
            System.out.println(c);
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return c;
    }
}

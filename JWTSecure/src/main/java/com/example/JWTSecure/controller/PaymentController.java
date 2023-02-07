package com.example.JWTSecure.controller;

import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.config.PaymentConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

//    @PostMapping("/create_payment")
//    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) throws UnsupportedEncodingException {
    @GetMapping("/create_payment")
    public ResponseEntity<?> createPayment() throws UnsupportedEncodingException {
        OrderDTO orderDTO = new OrderDTO();
//        double amount = paymentDTO.getAmount() * 100;
        int amount = 10000000;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentConfig.VNP_VERSION);
        vnp_Params.put("vnp_Command", PaymentConfig.VNP_COMMAND);
        vnp_Params.put("vnp_TmnCode", PaymentConfig.TMN_CODE);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", PaymentConfig.CURRCODE);
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", "23555"); // Order id
        vnp_Params.put("vnp_OrderInfo", "Ok");
//        vnp_Params.put("vnp_BankTranNo", paymentDTO.getDescription());
//        vnp_Params.put("vnp_OrderType", PaymentConfig.ORDER_TYPE);
        vnp_Params.put("vnp_Locale", PaymentConfig.LOCATE);
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.RETURNURL);
//        vnp_Params.put("vnp_IpAddr", PaymentConfig.IPDEFAULT);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.CHECKSUM, hashData.toString());
        queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.VNP_PAYURL + "?" + queryUrl;

        PaymentResDTO paymentResDTO = new PaymentResDTO();
        paymentResDTO.setStatus("Ok");
        paymentResDTO.setMessage("Successfully");
        paymentResDTO.setURL(paymentUrl);

        return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);
    }

    @GetMapping("/payment_infor")
    public ResponseEntity<?> transaction(
            @RequestParam(value = "vnp_Amount", required = false) String amount,
            @RequestParam(value = "vnp_BankCode", required = false) String bankCode,
            @RequestParam(value = "vnp_BankTranNo", required = false) String bankTranNo,
            @RequestParam(value = "vnp_CardType", required = false) String cardType,
            @RequestParam(value = "vnp_OrderInfo", required = false) String orderInfo,
            @RequestParam(value = "PayDate", required = false) String payDate,
            @RequestParam(value = "vnp_ResponseCode", required = false) String responseCode,
            @RequestParam(value = "vnp_TmnCode", required = false) String tmnCode,
            @RequestParam(value = "vnp_TransactionNo", required = false) String transactionNo,
            @RequestParam(value = "vnp_TxnRef", required = false) String txnRef,
            @RequestParam(value = "vnp_SecureHashType", required = false) String secureHashType,
            @RequestParam(value = "vnp_SecureHash", required = false) String secureHash
    ) throws UnsupportedEncodingException {
        TransactionStatusDTO result = new TransactionStatusDTO();
//        if (!responseCode.equalsIgnoreCase("Ok")) {
            result.setStatus("NOK");
            result.setMessage("failed");
            result.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(result);
//        }
//        return null;
    }
}

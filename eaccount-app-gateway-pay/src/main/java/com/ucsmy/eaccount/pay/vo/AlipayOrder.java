package com.ucsmy.eaccount.pay.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AlipayOrder {
    @JsonProperty("out_trade_no")
    private String tradeNo;// 唯一订单号
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("total_amount")
    private double totalAmount;
    private String subject;// 商品的标题/交易标题/订单标题/订单关键字等。
    private String body;// 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
    @JsonProperty("passback_params")
    private String passBackParams;
    @JsonProperty("extend_params")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object extendParams;
}

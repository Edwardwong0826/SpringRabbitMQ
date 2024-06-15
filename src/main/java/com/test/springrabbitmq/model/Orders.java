package com.test.springrabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders implements Serializable {

    private String id;

    private String orderName;

    private BigDecimal amount;

    private Date orderTime;

}

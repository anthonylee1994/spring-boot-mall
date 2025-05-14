package com.learn.springboottutorial.dto;

import java.util.List;

/**
 * @author anthonylee
 */
public class CreateOrderRequest {
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}

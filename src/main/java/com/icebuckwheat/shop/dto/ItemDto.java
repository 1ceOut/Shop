package com.icebuckwheat.shop.dto;

import lombok.Data;

@Data
public class ItemDto {
    private String image;
    private String title;
    private String subtitle;
    private String origin_price;
    private String price;
    private String discount_percent;
    private String url;
    private String review_count;
}

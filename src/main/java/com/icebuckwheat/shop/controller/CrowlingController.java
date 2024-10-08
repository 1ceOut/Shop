package com.icebuckwheat.shop.controller;

import com.icebuckwheat.shop.dto.ItemDto;
import com.icebuckwheat.shop.service.Crowling;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/shop")
//@CrossOrigin(value = "http://localhost:8080",allowCredentials ="true")
public class CrowlingController {

    private final Crowling crowling;

    @GetMapping("/list")
    public ResponseEntity<Object> getData() throws InterruptedException, IOException {
        ArrayList<ItemDto> itemDtos = crowling.mainPage();
        if (itemDtos == null || itemDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(itemDtos);
    }

    @GetMapping("/header")
    public ResponseEntity<Object> getHeader() throws InterruptedException, IOException {
        return ResponseEntity.ok(crowling.MainBanner());
    }
}

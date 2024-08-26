package com.icebuckwheat.shop.service;

import com.icebuckwheat.shop.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Crowling {

    private final WebDriver web_driver;

    public ArrayList<ItemDto> mainPage() throws InterruptedException {

        ArrayList<ItemDto> items = new ArrayList<>();
        try {
            // 웹페이지 로드
            web_driver.get("https://www.kurly.com/collection-groups/market-best?page=1&collection=market-best-logic");
            new Actions(web_driver).sendKeys(Keys.END).perform();

            WebElement getATag = web_driver.findElement(By.cssSelector(".css-11kh0cw"));
            List<WebElement> elements = getATag.findElements(By.tagName("a"));
            System.out.println("parsing end!");
            for (WebElement element : elements) {
                ItemDto itemDto = new ItemDto();
                String imgURL = element.findElement(By.tagName("img")).getAttribute("src");
                if (!imgURL.startsWith("https")) {
                    continue;
                }
                itemDto.setUrl(element.getAttribute("href"));
                itemDto.setImage(imgURL);
                itemDto.setTitle(element.findElement(By.cssSelector(".css-1dry2r1")).getText());
                try {
                    itemDto.setSubtitle(element.findElement(By.cssSelector(".css-1wejlc3")).getText());
                } catch (Exception ignored) {

                }
                try {
                    if (element.findElement(By.className("discount")) != null) {
                        itemDto.setDiscount_percent(element.findElement(By.className("discount-rate")).getText());
                        itemDto.setOrigin_price(element.findElement(By.className("price-number")).getText());
                    }
                } catch (Exception ignored) {

                }
                itemDto.setPrice(element.findElement(By.className("sales-price")).findElement(By.className("price-number")).getText());
                itemDto.setReview_count(element.findElement(By.className("review-number")).getText().replace("+",""));
                items.add(itemDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            web_driver.quit();
        }
        System.out.println(items.size());
        return items;
    }
}

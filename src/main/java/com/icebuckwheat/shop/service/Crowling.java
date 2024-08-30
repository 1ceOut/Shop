package com.icebuckwheat.shop.service;

import com.icebuckwheat.shop.dto.BannerDto;
import com.icebuckwheat.shop.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Crowling {

    private final ChromeOptions chromeOptions;

    public ArrayList<ItemDto> mainPage() throws InterruptedException {

        ArrayList<ItemDto> items = new ArrayList<>();
        WebDriver driver = new ChromeDriver(chromeOptions);
        try {

            // 웹페이지 로드
            driver.get("https://www.kurly.com/collection-groups/market-best?page=1&collection=market-best-logic");
            new Actions(driver).sendKeys(Keys.END).perform();

            WebElement getATag = driver.findElement(By.xpath("//div[contains(@class, 'cw')]"));
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
        }
        finally {
            driver.close();
            driver.quit();
        }
        return items;
    }

    public List<BannerDto> MainBanner() throws InterruptedException {
        WebDriver driver = new ChromeDriver(chromeOptions);
        ArrayList<BannerDto> bannerlist = new ArrayList<>();
        try {
            driver.get("https://www.kurly.com/main");
            WebElement element = driver.findElement(By.className("swiper-wrapper"));
            List<WebElement> elements = element.findElements(By.className("swiper-slide"));

            for (WebElement element1 : elements) {
                bannerlist.add(BannerDto.builder()
                        .image(element1.findElements(By.xpath(".//a/div/span/img")).get(0).getAttribute("src"))
                        .url(element1.findElement(By.tagName("a")).getAttribute("href"))
                        .build());
                if (bannerlist.size() >= 3) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            driver.close();
            driver.quit();
        }
        return bannerlist;
    }
}

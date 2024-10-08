package com.icebuckwheat.shop.service;

import com.icebuckwheat.shop.dto.BannerDto;
import com.icebuckwheat.shop.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
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
            //new Actions(driver).sendKeys(Keys.END).perform();

            int previousHeight = ((Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight")).intValue();
            while (true) {
                // 페이지 끝으로 스크롤
                new Actions(driver).sendKeys(Keys.END).perform();
                // 잠시 대기
                Thread.sleep(100); // 이미지 로드 대기

                // 현재 높이
                int currentHeight = ((Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight")).intValue();
                if (currentHeight == previousHeight) {
                    break; // 더 이상 스크롤할 수 없으면 종료
                }
                previousHeight = currentHeight;
            }


            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"container\"]/div/div[2]/div[2]/a"));
            System.out.printf("parsing: %d\n",elements.size());
            for (WebElement element : elements) {
                ItemDto itemDto = new ItemDto();
                String imgURL = element.findElement(By.tagName("img")).getAttribute("src");
                if (!imgURL.startsWith("https")) {
                    continue;
                }
                itemDto.setUrl(element.getAttribute("href"));
                itemDto.setImage(imgURL);
                itemDto.setTitle(element.findElement(By.cssSelector(".css-1dry2r1.e1c07x485")).getText());
                try {
                    itemDto.setSubtitle(element.findElement(By.cssSelector(".css-1kpzrna.e1c07x486 > p")).getText());
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

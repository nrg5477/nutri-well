package com.example.nutri_well.db;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.List;

@SpringBootTest
public class SeleniumDemoApplication {
    @Test
    public void run() throws Exception {
        // 웹드라이버 경로 설정 (ChromeDriver 경로)
        String filePath = "D:\\test\\FoodDB_test_t.csv"; // DB파일경로
        System.setProperty("webdriver.chrome.driver", "C:\\backend23\\tool\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), "CP949"))) {
            List<String[]> records = reader.readAll();

            records.remove(0); // 헤더 삭제

            // 웹페이지로 이동
            for (String[] record : records) {
                System.out.println(record[9]);
                if(record[9].equals("밀크티_버블티") ){
                    driver.get("https://www.google.com/imghp");
                    WebElement searchBox = driver.findElement(By.name("q"));
                    System.out.println(record[1] + " " + record[103]);
                    searchBox.sendKeys(record[1] + " " + record[103]);
                    searchBox.submit();

                    Thread.sleep(800);

                    List<WebElement> images = driver.findElements(By.cssSelector("img"));

                    int count = 0;
                    for (WebElement imageElement : images) {
                        if (count >= 2) break; // 2개의 이미지만 저장
                        String imageUrl = imageElement.getAttribute("src");

                        try {
                            BufferedImage image = null;
                            if (imageUrl != null && imageUrl.startsWith("data:image/")) {
                                String base64Image = imageUrl.split(",")[1];
                                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                                try (InputStream is = new ByteArrayInputStream(imageBytes)) {
                                    image = ImageIO.read(is);
                                }
                            } else if (imageUrl != null && imageUrl.startsWith("http")) {
                                image = ImageIO.read(new URL(imageUrl));
                            }

                            if (image != null && image.getWidth() >= 200 && image.getHeight() >= 200) {
                                File outputfile = new File("D:\\test\\category2\\" + record[1] + ".jpg");
                                ImageIO.write(image, "jpg", outputfile);
                                count++;
                                System.out.println("적합한 이미지 URL: ");
                            } else {
                                System.out.println("적합하지 않은 이미지 크기: " );
                            }
                        } catch (IOException e) {
                            System.out.println("이미지 로드 실패: ");
                        }
                    }
                }
            }
        } catch (CsvException e) {
            throw new IOException("CSV 파일을 읽는 중 오류 발생", e);
        } finally {
            driver.quit(); // 드라이버 종료
        }
    }
}

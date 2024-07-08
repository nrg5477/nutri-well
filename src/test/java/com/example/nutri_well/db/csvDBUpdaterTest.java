package com.example.nutri_well.db;

import com.example.nutri_well.entity.Category;
import com.example.nutri_well.entity.Food;
import com.example.nutri_well.entity.FoodNutrient;
import com.example.nutri_well.entity.Nutrient;
import com.example.nutri_well.repository.CategoryRepository;
import com.example.nutri_well.repository.FoodNutrientRepository;
import com.example.nutri_well.repository.FoodRepository;
import com.example.nutri_well.repository.NutrientRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class csvDBUpdaterTest {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    FoodNutrientRepository foodNutrientRepository;
    @Autowired
    NutrientRepository nutrientRepository;

    @Test
    public void updateDatabase() throws IOException {
        String filePath = "D:\\test\\FoodDB_test1.csv"; // DB파일경로

        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), "CP949"))) {
            List<String[]> records = reader.readAll();
//            System.out.println(records.size());
            List<Nutrient> nutrientList = saveNutrients(records.get(0));
//            System.out.println(records.size());
            records.remove(0); // 헤더 삭제

            for (String[] record : records) {
//                System.out.println(record.length);
                saveParentCategory(record);
            }
            for (String[] record : records) {
                saveChildCategory(record);
            }
            for (String[] record : records) {
                Food food = saveFood(record);
                saveFoodNutrients(record, food, nutrientList);
            }
        } catch (CsvException e) {
            throw new IOException("CSV 파일을 읽는 중 오류 발생", e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveParentCategory(String[] record) {
        // 대분류 확인 및 삽입 7-> 대분류, 9-> 소분류
        Category parentCategory = categoryRepository.findByName(record[7]);

        if (parentCategory == null) {
            Category category = new Category(record[7], null);
            categoryRepository.save(category);
        }
    }

    public void saveChildCategory(String[] record) {
        // 소분류 확인 및 삽입
        if (!record[9].equals("해당없음")) {
            // parent를 찾아서 넣는다.
            Category parent = categoryRepository.findByName(record[7]);
            Category isExsistCategory = categoryRepository.findByName(record[9]);

            if (isExsistCategory == null) {
                Category category = new Category(record[9], parent);
                categoryRepository.save(category);
            }
        }
    }

    private List<Nutrient> saveNutrients(String[] header) {
        List<Nutrient> nutrientList = new ArrayList<>();
        for (int i = 17; i < 100; i++) {//18부터 150까지
            String name = header[i].split("_")[0];
            String unit = header[i].split("_")[1];
            Nutrient nutrient = new Nutrient(name, unit);
            nutrientList.add(nutrient);

            nutrientRepository.save(nutrient);
        }
        return nutrientList;
    }

    private Food saveFood(String[] record) throws ParseException {
        Category category = categoryRepository.findByName(record[9]);
        String foodName = Arrays.stream(record[1].split("_"))
                .skip(1)
                .findFirst().orElse(Arrays.stream(record[1].split("_"))
                        .findFirst().orElse(""));
        String product = Arrays.stream(record[5].split("\\("))
                .findFirst().orElse("");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(record[107]);
//        System.out.println("Parsed Date: " + date);
        String manufacturer = record[103];
        int weight = (int)Double.parseDouble(record[102].substring(0, record[102].length() - 1));
        Food food = new Food(foodName, category, record[0], product, manufacturer, "100", weight,date);

        foodRepository.save(food); // Category도 같이 생성
        return food;
    }

    private void saveFoodNutrients(String[] record, Food food, List<Nutrient> nutrientList) {
        String[] nutrients = Arrays.copyOfRange(record, 17, 100);

        for (int i = 0; i < nutrients.length; i++) {
            if (!nutrients[i].isEmpty() && !nutrients[i].equals("0")) {
                String nutrientValueStr = nutrients[i].trim();
                double nutrientValue = Double.parseDouble(nutrientValueStr);
                FoodNutrient foodNutrient = new FoodNutrient(food, nutrientList.get(i), nutrientValue);
                foodNutrientRepository.save(foodNutrient);
            }
        }
    }
}
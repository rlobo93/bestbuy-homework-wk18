package com.bestbuy.product;

import com.bestbuy.studentinfo.ProductSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Locale;

import static org.hamcrest.Matchers.hasValue;


//@RunWith(SerenityRunner.class)
public class ProductCURDTest extends TestBase {

    static String name = "Nvidia" + TestUtils.getRandomValue();
    static String type = "Rtx Gpu" + TestUtils.getRandomValue();
    static int price = 2;
    static int shipping = 4;
    static String upc = "RTX";
    static String description = "Nvidia Rtx 3090ti Gpu";
    static String manufacturer = "Msi";
    static String model = "3090 ti";
    static String url = "www.msi.com";
    static String image = "11111111-12";
    static int productID;

    @Steps
    ProductSteps productSteps;

    @Title("This will create a New product")
    @Test
    public void test001() {
        ValidatableResponse response = productSteps.createProduct(name,type,price,shipping,upc,description,manufacturer,model,url,image);
        response.log().all().statusCode(201);
        productID = response.log().all().extract().path("id");
        System.out.println(productID);
    }

    @Title("Verify if the Product was added to the application")
    @Test
    public void test002() {
        HashMap<String,Object> productMap = productSteps.getProductInformation(name);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);
    }

    @Title("Update the product information")
    @Test
    public void test003() {
        name = name + "_updated";
        productSteps.updateProduct(productID,price,shipping,name,type,upc,description,manufacturer,model,url,image);

        HashMap<String, Object> productMap = productSteps.getProductInformation(name);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);

    }

    @Title("Delete the product by ID")
    @Test
    public void test004() {
        productSteps.deleteProduct(productID).statusCode(200);
        productSteps.getProductById(productID).statusCode(404);
    }





}

package com.bestbuy.studentinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductSteps {

    @Step("Create new product with name: {0}, type: {1}, price: {2}, upc: {3}, shipping: {4}, description: {5}, manufacturer: {6}, model: {7}, url: {8}, image: {9}")
    public ValidatableResponse createProduct(String name, String type, int price, int shipping,String upc, String description, String manufacturer, String model, String url, String image) {

        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setUpc(upc);
        productsPojo.setShipping(shipping);
        productsPojo.setDescription(description);
        productsPojo.setDescription(description);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setModel(model);
        productsPojo.setUrl(url);
        productsPojo.setImage(image);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post(EndPoints.CREATE_PRODUCTS)
                .then();

    }

    @Step ("Verify product with name: {0}")
    public HashMap<String,Object> getProductInformation(String name){
        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .queryParam("name",name)
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then()
                .extract()
                .path(p1+name+p2);


    }


    @Step ("Change product with productID: {0}, name: {1}, type: {2}, price: {3}, upc: {4}, shipping: {5}, description: {6}, manufacturer: {7}, model: {8}, url: {9}, image: {10}")
    public ValidatableResponse updateProduct(int productID, int price, int shipping,String name, String type,String upc, String description, String manufacturer, String model, String url, String image){

        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setUpc(upc);
        productsPojo.setShipping(shipping);
        productsPojo.setDescription(description);
        productsPojo.setDescription(description);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setModel(model);
        productsPojo.setUrl(url);
        productsPojo.setImage(image);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("productID",productID)
                .body(productsPojo)
                .when()
                .patch(EndPoints.UPDATE_SINGLE_PRODUCT_BY_ID)
                .then();

    }

    @Step("delete product with productID: {0} ")
    public ValidatableResponse deleteProduct(int productID){

        return SerenityRest.given()
                .log().all()
                .pathParam("productID",productID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_PRODUCT_BY_ID)
                .then();

    }

    @Step("Verify product has been deleted for productID: {0}")
    public ValidatableResponse getProductById(int productID){
        return SerenityRest.given()
                .log().all()
                .pathParam("productID",productID)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }

}

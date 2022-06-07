package com.bestbuy.studentinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ServicesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;
import java.util.List;

public class ServiceSteps {



    @Step("Creating service with Name : {0}")
    public ValidatableResponse createService(String name) {

        ServicesPojo servicesPojo = new ServicesPojo();
        servicesPojo.setName(name);
                return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(servicesPojo)
                .when()
                .post(EndPoints.CREATE_SERVICES)
                .then();
    }


    @Step("Getting the service  with Name: {0}")
    public HashMap<String, Object> getServiceInfoByFirstName(String firstName) {
        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> serviceMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_SERVICES)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + firstName + p2);
        return serviceMap;
    }


    @Step("Updating service information ")
    public ValidatableResponse updateStudent(int serviceID, String name) {

        ServicesPojo servicesPojo = new ServicesPojo();
        servicesPojo.setName(name);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("serviceID", serviceID)
                .body(servicesPojo)
                .when()
                .patch(EndPoints.UPDATE_SINGLE_SERVICES_BY_ID)
                .then();
    }


    @Step("Deleting service information with serviceId: {0}")
    public ValidatableResponse deleteService(int serviceID) {

        return SerenityRest.given().log().all()
                .pathParam("serviceID",serviceID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_SERVICES_BY_ID)
                .then();
    }

    @Step("Getting service information with serviceId: {0}")
    public ValidatableResponse getServiceById(int serviceID) {
        return SerenityRest.given().log().all()
                .pathParam("serviceID",serviceID)
                .get(EndPoints.GET_SINGLE_SERVICES_BY_ID)
                .then();
    }
}

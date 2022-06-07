package com.bestbuy.services;

import com.bestbuy.studentinfo.ServiceSteps;
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

import static org.hamcrest.Matchers.hasValue;

//@RunWith(SerenityRunner.class)
public class ServicesCURDTest extends TestBase {

    public static String name = "Custom Pc Building" + TestUtils.getRandomValue();
    public static int serviceID;


    @Steps
    ServiceSteps serviceSteps;

    @Title("This will create a New service")
    @Test
    public void test001() {
        ValidatableResponse response = serviceSteps.createService(name);
        response.log().all().statusCode(201);
        serviceID = response.log().all().extract().path("id");
        System.out.println(serviceID);
    }

    @Title("Verify if the service was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> productMap = serviceSteps.getServiceInfoByFirstName(name);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);
    }

    @Title("Update the service information")
    @Test
    public void test003() {
        name = name + "_updated";
        serviceSteps.updateStudent(serviceID, name);

        HashMap<String, Object> productMap = serviceSteps.getServiceInfoByFirstName(name);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);

    }

    @Title("service the product by ID")
    @Test
    public void test004() {
        serviceSteps.deleteService(serviceID).statusCode(200);
        serviceSteps.getServiceById(serviceID).statusCode(404);
    }

}

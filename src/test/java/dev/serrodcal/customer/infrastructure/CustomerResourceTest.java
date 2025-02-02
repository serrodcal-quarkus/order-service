package dev.serrodcal.customer.infrastructure;

import dev.serrodcal.customers.infrastructure.dtos.AddOrderRequest;
import dev.serrodcal.customers.infrastructure.dtos.NewCustomerRequest;
import dev.serrodcal.customers.infrastructure.dtos.UpdateCustomerRequest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
class CustomerResourceTest {

    @Test
    @Order(1)
    void testCreateCustomerEndpoint() {
        NewCustomerRequest body = new NewCustomerRequest(
                "Sergio",
                "sergio@myemail.com"
        );

        given()
            .body(body)
            .when().contentType("application/json")
            .post("/v1/customers")
            .then()
            .statusCode(201)
            .body("name", equalTo("Sergio"));
    }

    @Test
    @Order(2)
    void testGetAllCustomerEndpoint() {
        given()
                .when().contentType("application/json")
                .get("/v1/customers")
                .then()
                .statusCode(200)
                .body("payload[1].name", equalTo("Sergio"));
    }

    @Test
    @Order(3)
    void testGetCustomerByIdEndpoint() {
        given()
                .when().contentType("application/json")
                .get("/v1/customers/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Sergio"));
    }

    @Test
    @Order(4)
    void testUpdateCustomerEndpoint() {
        UpdateCustomerRequest body = new UpdateCustomerRequest(
                "Sergio",
                "serrodcal@myemail.com"
        );

        given()
                .body(body)
                .when().contentType("application/json")
                .put("/v1/customers/1")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    void testAddOrderEndpoint() {
        AddOrderRequest body = new AddOrderRequest("phone", 1);

        given()
                .body(body)
                .when().contentType("application/json")
                .post("/v1/customers/1/orders")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(6)
    void testRemoveOrderEndpoint() {
        given()
                .when().contentType("application/json")
                .delete("/v1/customers/1/orders/1")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(7)
    void testDeleteCustomerEndpoint() {
        given()
                .when().contentType("application/json")
                .delete("/v1/customers/1")
                .then()
                .statusCode(204);
    }

}
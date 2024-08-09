package dev.serrodcal.order.infrastructure;

import dev.serrodcal.orders.infrastructure.dtos.UpdateOrderRequest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderResourceTest {

    @Test
    @Order(1)
    void testUpdateOrderEndpoint() {
        UpdateOrderRequest body = new UpdateOrderRequest(
                "phone",
                2
        );

        given()
                .body(body)
                .when().contentType("application/json")
                .put("/v1/orders/10")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(2)
    void testGetOrderByIdEndpoint() {
        given()
                .when().contentType("application/json")
                .get("/v1/orders/10")
                .then()
                .statusCode(200)
                .body("quantity", equalTo(2));
                //.extract().asString();
    }

    @Test
    @Order(3)
    void testGetAllOrderEndpoint() {
        given()
                .when().contentType("application/json")
                .get("/v1/orders")
                .then()
                .statusCode(200)
                .body("payload[0].quantity", equalTo(2));
    }

}

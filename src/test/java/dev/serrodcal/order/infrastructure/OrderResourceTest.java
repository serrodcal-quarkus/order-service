package dev.serrodcal.order.infrastructure;

import dev.serrodcal.orders.infrastructure.dtos.UpdateOrderRequest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderResourceTest {

    @Test
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
    void testGetOrderByIdEndpoint() {
        given()
                .when().contentType("application/json")
                .get("/v1/orders/10")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetAllOrderEndpoint() {
        given()
                .when().contentType("application/json")
                .get("/v1/orders")
                .then()
                .statusCode(200);
    }

}

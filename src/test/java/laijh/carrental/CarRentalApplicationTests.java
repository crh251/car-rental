package laijh.carrental;

import laijh.carrental.common.ApiResponse;
import laijh.carrental.common.NoLoginException;
import laijh.carrental.common.ParamInvalidException;
import laijh.carrental.controller.CarRentalController;
import laijh.carrental.controller.UserController;
import laijh.carrental.form.CarRentalForm;
import laijh.carrental.form.LoginBack;
import laijh.carrental.form.LoginForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class CarRentalApplicationTests {

    @Autowired
    private UserController userController;

    @Autowired
    private CarRentalController carRentalController;

//    @Test
    void contextLoads() {


        ApiResponse<?> response = userController.login(LoginForm.builder().username("t1").password("123").build());

        Object result = Optional.ofNullable(response).map(ApiResponse::getData).orElse(null);
        Assertions.assertTrue(result instanceof LoginBack);

        String token = ((LoginBack) result).getToken();
        Assertions.assertNotNull(token);

        final int size = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(size);

        for (int i = 0; i < size; i++) {
            executorService.submit(() -> {
                CarRentalForm rentalForm = CarRentalForm.builder()
                        .token(token)
                        .carModel("")
                        .startTime(new Date(System.currentTimeMillis() + 60 * 60 * 1000L))
                        .endTime(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000L))
                        .build();
                try {
                    ApiResponse<?> apiResponse = carRentalController.order(rentalForm);
                    String code = apiResponse.getCode();
                } catch (ParamInvalidException | NoLoginException e) {
                    e.printStackTrace();
                }
            });
        }


    }

}

package functional.api;

import com.framework.service.api.ReqResService;
import functional.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.hc.core5.http.HttpStatus.SC_SUCCESS;
import static org.testng.Assert.assertEquals;

public class ReqResAPITest extends BaseTest {

    ReqResService reqResService = new ReqResService();

    @Test(priority = 0)
    public void verifyGetUsersListApi(){

        Response response = reqResService.getAllUsersList();

        assertEquals(response.getStatusCode(), SC_SUCCESS);
        assertEquals(response.body().path("page").toString(), "2");
    }

}

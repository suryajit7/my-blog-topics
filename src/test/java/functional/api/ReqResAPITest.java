package functional.api;

import com.framework.service.api.ReqResService;
import functional.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class ReqResAPITest extends BaseTest {

    ReqResService reqResService = new ReqResService();


    @Test(priority = 0)
    public void verifyGetUsersListApi(){

        Response response = reqResService.getAllUsersList();
    }





}

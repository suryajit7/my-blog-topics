package functional.api;

import com.framework.data.entity.User;
import com.framework.data.supplier.UserData;
import com.framework.service.api.ReqResService;
import functional.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.framework.util.AssertWebService.assertThat;
import static org.apache.hc.core5.http.HttpStatus.SC_CREATED;

public class ReqResAPITest extends BaseTest {

    ReqResService reqResService = new ReqResService();


    @Test(priority = 0, dataProvider = "getUserInfoToCreateUsers", dataProviderClass = UserData.class)
    public void validateCreateUserFlow(User user){

        Response response = reqResService.createUser(user);

        assertThat(response)
                .hasValidStatusCode(SC_CREATED)
                .hasValidJsonSchema("create_user_response_schema.json")
                .hasResponseTimeWithin(3L)
                .hasValidJsonData(("create_user_response_").concat(user.getName()).concat(".json"));

        //deserialization
        logger.info(response.as(User.class).toString());
    }

}

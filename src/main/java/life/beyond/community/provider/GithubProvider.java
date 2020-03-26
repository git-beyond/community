package life.beyond.community.provider;

import life.beyond.community.dto.AccessTokenDTO;
import okhttp3.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO asscessTokenDTO){
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url("http://github.com/login/oauth/access_token")
                .post(body)
                .build();
                try(Response response = client.newCall(request).execute()){
                    String string = response.body().string();
                    System.out.println(string);
                    return  string;
                }catch (IOException e){
                }
                return null;
    }
}

package life.beyond.community.provider;

import com.alibaba.fastjson.JSON;
import life.beyond.community.dto.AccessTokenDTO;
import life.beyond.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("http://github.com/login/oauth/access_token")
                .post(body)
                .build();
                try(Response response = client.newCall(request).execute()){
                    String string = response.body().string();
                    System.out.println(string);
                    String token = string.split("&")[0].split("=")[1];
                    return  token;
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
    }
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}

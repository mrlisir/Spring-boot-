//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.demo3.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo3.dto.AccessTokenDTO;
import com.example.demo3.dto.GithubUser;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request.Builder;
import org.springframework.stereotype.Component;

@Component
public class GithubProvider {
    public GithubProvider() {
    }

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = (new Builder()).url("https://github.com/login/oauth/access_token").post(body).build();

        try {
            Response response = client.newCall(request).execute();

            String var8;
            try {
                String token = response.body().string().split("&")[0].split("=")[1];
                var8 = token;
            } catch (Throwable var10) {
                if (response != null) {
                    try {
                        response.close();
                    } catch (Throwable var9) {
                        var10.addSuppressed(var9);
                    }
                }

                throw var10;
            }

            if (response != null) {
                response.close();
            }

            return var8;
        } catch (IOException var11) {
            var11.printStackTrace();
            return null;
        }
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = (new Builder()).url("https://api.github.com/user?access_token=" + accessToken).build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = (GithubUser)JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException var7) {
            return null;
        }
    }
}

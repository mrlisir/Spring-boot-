//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.demo3.Controller;

import com.example.demo3.dto.AccessTokenDTO;
import com.example.demo3.dto.GithubUser;
import com.example.demo3.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String secret;
    @Value("${github.redirect.uri}")
    private String uri;

    public AuthorizeController() {
    }

    @GetMapping({"/callback"})
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(this.clientId);
        accessTokenDTO.setRedirect_uri(this.uri);
        accessTokenDTO.setClient_secret(this.secret);
        String accessToken = this.githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = this.githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}

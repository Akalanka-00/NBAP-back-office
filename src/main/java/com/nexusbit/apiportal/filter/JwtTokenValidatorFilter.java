package com.nexusbit.apiportal.filter;

import com.nexusbit.apiportal.constants.consts.SecurityConstants;
import com.nexusbit.apiportal.utils.LoggerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {
    private static final LoggerService logger = new LoggerService();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        HttpServletResponse res = (HttpServletResponse) response;
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
        if (null != jwt) {
            try {
                if(jwt.startsWith(SecurityConstants.JWT_PREFIX)){
                    jwt = jwt.replace(SecurityConstants.JWT_PREFIX, "");
                }
                SecretKey key = Keys.hmacShaKeyFor(
                        SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                logger.error("Invalid Token received! "+e.getMessage()+" JwtTokenValidatorFilter.doFilterInternal()");
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> paths = List.of("/auth/user/login", "/auth/user/register", "/utils/ipCheck");
        for( String path : paths){
            if(request.getServletPath().equals(path)){
                return true;
            }
        }

        return false;
    }
}
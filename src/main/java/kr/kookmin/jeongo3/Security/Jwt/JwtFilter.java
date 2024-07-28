package kr.kookmin.jeongo3.Security.Jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kookmin.jeongo3.Exception.ErrorCode;
import kr.kookmin.jeongo3.Exception.MyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
        try {
            if (token != null && jwtProvider.validateToken(token)) {
                token = jwtProvider.disassembleToken(token);
                Authentication auth = jwtProvider.getUserRole(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (SignatureException | MalformedJwtException e) {
            request.setAttribute("exception", ErrorCode.WRONG_TOKEN.getMessage());
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getMessage());
        } catch (MyException e) {
            request.setAttribute("exception", e.getErrorCode().getMessage());
        } catch (Exception e) {
            log.error("================================================");
            log.error("JwtFilter - doFilterInternal() 오류발생");
            log.error("Exception Message : {}", e.getMessage());
            log.error("Exception StackTrace : {");
            e.printStackTrace();
            log.error("}");
            log.error("================================================");
            request.setAttribute("exception", ErrorCode.UNKNOWN_ERROR.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}

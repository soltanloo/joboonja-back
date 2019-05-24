package ServerConfig;

import Joboonja.Database;
import Joboonja.UserManager;
import Models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter( "/*" )
public class JWTFilter implements Filter {

    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String AUTH_HEADER_VALUE_PREFIX = "JWT ";

    @Override
    public void doFilter( final ServletRequest servletRequest,
                          final ServletResponse servletResponse,
                          final FilterChain filterChain ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String path = httpRequest.getRequestURI();
        System.out.println("-----------------");
        System.out.println(path);
        if (!path.contains("/auth") && !httpRequest.getMethod().equals("OPTIONS")) {

            String jwt = getJWTToken(httpRequest);
            System.out.println(httpRequest.getMethod());
            System.out.println(httpRequest.getHeader("Authorization"));
            Jws<Claims> jws;

            Boolean noJwt = false;

            try {
                if ( jwt != null && !jwt.isEmpty() ) {

                    jws = Jwts.parser()
                            .setSigningKey(PrivateKey.getKey())
                            .parseClaimsJws(jwt);
                    User u = Database.userMapper.find(Integer.parseInt(String.valueOf(jws.getBody().get("userId"))));

                    servletRequest.setAttribute("currentUser", u);
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    noJwt = true;
                    throw new Exception();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                if (noJwt) {
                    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                    httpResponse.setStatus(403);
                } else {
                    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                    httpResponse.setStatus(401);
                }
            }

        } else {
            filterChain.doFilter(httpRequest, servletResponse);
        }
    }

    private String getJWTToken( HttpServletRequest request ) {
        String authHeader = request.getHeader( AUTH_HEADER_KEY );
        if ( authHeader != null && authHeader.startsWith( AUTH_HEADER_VALUE_PREFIX ) ) {
            return authHeader.substring( AUTH_HEADER_VALUE_PREFIX.length() );
        }
        return null;
    }
}
package lk.ijse.fieldguardianbackend.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lk.ijse.fieldguardianbackend.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/**
 * Service implementation for JWT operations.
 * This class provides methods to generate, validate, and extract information from JWT tokens.
 * @author Gayanuka Bulegoda
 */
@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String jwtSecretKey;
    private static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 1 week
    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token
     * @return the username
     */
    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    /**
     * Generates a new JWT token for the given user details.
     *
     * @param userDetails the user details
     * @return the generated JWT token
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    /**
     * Generates a new JWT token for the given user details.
     *
     * @param userDetails the user details
     * @return the generated JWT token
     */
    @Override
    public String refreshToken(UserDetails userDetails) {
        return refreshToken(new HashMap<>(), userDetails);
    }
    /**
     * Validates the JWT token against the user details.
     *
     * @param token the JWT token
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        var username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token the JWT token
     * @param claimResolver the function to resolve the claim
     * @param <T> the type of the claim
     * @return the extracted claim
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }
    private String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return createToken(extractClaims, userDetails);
    }
    private String refreshToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return createToken(extractClaims, userDetails);
    }
    /**
     * Creates a new JWT token with the given claims and user details.
     *
     * @param extractClaims the claims to include in the token
     * @param userDetails the user details
     * @return the created JWT token
     */
    private String createToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        extractClaims.put("role", userDetails.getAuthorities());
        Date now = new Date();
        Date expire = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    /**
     * Checks if the JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    /**
     * Retrieves all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the claims
     */
    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }
    /**
     * Retrieves the signing key for the JWT token.
     *
     * @return the signing key
     */
    private Key getSignKey() {
        byte[] decode = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(decode);
    }
}
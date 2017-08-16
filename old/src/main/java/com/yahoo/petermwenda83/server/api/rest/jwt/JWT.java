package com.yahoo.petermwenda83.server.api.rest.jwt;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;

import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date; 

/**
 * 
 * @author peter
 *
 */
public class JWT {

	//Sample method to construct a JWT
	public static String createJWT(String id, String issuer, String subject, long ttlMillis, String secret) {

		//The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		

		//We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id)
				.setIssuedAt(now)
				.setSubject(subject)
				.setIssuer(issuer)
				.signWith(signatureAlgorithm, signingKey);

		//if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		//Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}


	/**
	 * @param jwt
	 * @param apiKey
	 * @param id
	 * @param issuer
	 * @param subject
	 * @return
	 */
	public static boolean validateJWT(String jwt, String secret, String id, String issuer, String subject) {

		boolean isValid = false;
		
		Claims claims = parseJWT(jwt, secret); 
		
		if(StringUtils.equals(id, claims.getId()) && 
				StringUtils.equals(issuer, claims.getIssuer()) && 
				StringUtils.equals(subject, claims.getSubject()) ){
			
			isValid = true;
			
		}

		return isValid;
	}


	//Sample method to validate and read the JWT
	
	/**
	 * @param jwt
	 * @return
	 */
	public static Claims parseJWT(String jwt, String secret) {
		
		//This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser()         
				.setSigningKey(DatatypeConverter.parseBase64Binary(secret))
				.parseClaimsJws(jwt).getBody();

		/*System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration()); */
		
		return claims;
	}




}

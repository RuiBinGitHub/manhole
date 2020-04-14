package com.springboot.util;

import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springboot.entity.User;

public class JWTHelper {

	private static String secret = "591f4efa-afaf-4abf-8f8c-c752986be386";
	private static Algorithm algorithm = Algorithm.HMAC256(secret);

	public static String sign(User user) {
		Builder create = JWT.create();
		create.withClaim("id", user.getId() + "");
		create.withClaim("name", user.getName());
		create.withClaim("uuid", UUID.randomUUID().toString());
		String token = create.sign(algorithm);
		return token;
	}

	public static boolean verify(String token) {
		try {
			JWTVerifier verifier = JWT.require(algorithm).build();
			verifier.verify(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String getClaim(String token, String name) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(name).asString();
		} catch (Exception e) {
			return null;
		}
	}

}
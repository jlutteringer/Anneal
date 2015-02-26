package org.alloy.metal.utility;

import java.security.MessageDigest;

public class _Hash {
	public static byte[] getMd5Hash(byte[] input) {
		return _Exception.propagate(() -> {
			return MessageDigest.getInstance("MD5").digest(input);
		});
	}
}

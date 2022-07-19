package dev.luanfernandes.votacao.domain.utils;


public class StringUtil {

	private StringUtil() {
	}
	public static String removeMask(String value) {
		return value.replaceAll("[^\\d ]", "");
	}
}
package com.github.kenichiro22.xtend.shared;

class FieldVerifier {
	  /**
   * Verifies that the specified name is valid for our service.
   *
   * In this example, we only require that the name is at least four
   * characters. In your application, you can use more complex checks to ensure
   * that usernames, passwords, email addresses, URLs, and other fields have the
   * proper syntax.
   *
   * @param name the name to validate
   * @return true if valid, false if invalid
   */
  public static boolean isValidName(String name) {
    if (name == null) {
      return false;
    }
    return name.length() > 3;
  }
}
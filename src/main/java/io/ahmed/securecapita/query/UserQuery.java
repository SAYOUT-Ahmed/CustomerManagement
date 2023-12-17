package io.ahmed.securecapita.query;

public class UserQuery {

    //the values that we got are defined in the method getSqlParametersSource defined in UserRepoImpl, the names given there should be written here instead of ?,?,?...
  //  public static final String INSERT_USER_QUERY1 = "INSERT INTO Users (first_name, last_name, email, password) VALUES (:firstName, :lastName, :email, :password)";
    public static final String INSERT_USER_QUERY = "INSERT INTO Users (first_name, last_name, email, password) VALUES (:firstName, :lastName, :email, :password)";

    public static final String COUNT_USER_EMAIL_QUERY = "SELECT COUNT(*) FROM Users WHERE email= :email";
    public static final String INSERT_ACCOUNT_VERIFICATION_URL_QUERY = "INSERT INTO AccountVerifications (user_id, url) VALUES(:userId, :url)";

}

package io.ahmed.securecapita.repository.implementation;

import io.ahmed.securecapita.exception.ApiException;
import io.ahmed.securecapita.model.Role;
import io.ahmed.securecapita.model.User;
import io.ahmed.securecapita.repository.RoleRepository;
import io.ahmed.securecapita.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static io.ahmed.securecapita.enumeration.RoleType.*;
import static io.ahmed.securecapita.enumeration.VerificationType.ACCOUNT;
import static io.ahmed.securecapita.query.UserQuery.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;


    @Override
    public User create(User user) {
        //we are going to check the unicity of the email
        if (getEmailCount(user.getEmail().trim().toLowerCase()) > 0)
            throw new ApiException("Email exists, try a different one");
        //if true , then we will save the user
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());

            //we're adding role to the user
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
            //send verificationURL
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            //save URL in verification table
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, Map.of("userId", user.getId(), "url", verificationUrl));
            //save email to user with verification URL
            // -- emailService.sendVerificationUrl(user.getFirstName(),user.getEmail(), verificationUrl, ACCOUNT);
            user.setEnabled(false);
            user.setNotLocked(true);
            //return the newly created user
            return user;
            //any error, throw exception with proper message
        } catch (EmptyResultDataAccessException e) {
            log.error("EmptyResultDataAccessException occurred: {}", e.getMessage());
            e.printStackTrace(); // Add this line to print the stack trace
            throw new ApiException("User creation failed due to database error");
        } catch (DataAccessException e) {
            log.error("DataAccessException occurred: {}", e.getMessage());
            e.printStackTrace(); // Add this line to print the stack trace
            throw new ApiException("User creation failed due to database error");
        } catch (Exception exception) {
            log.error("Exception occurred: {}", exception.getMessage());
            exception.printStackTrace(); // Add this line to print the stack trace
            throw new ApiException("User creation failed");
        }
    }


    @Override
    public Collection list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }


    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/verify/" + type + "/" + key).toUriString();
    }

}

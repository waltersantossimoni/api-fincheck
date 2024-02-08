package com.fincheck.apifincheck.service.implementation;

import com.fincheck.apifincheck.configuration.security.jwt.JWTService;
import com.fincheck.apifincheck.dto.SigninDTO;
import com.fincheck.apifincheck.dto.SignupDTO;
import com.fincheck.apifincheck.dto.TokenResponseDTO;
import com.fincheck.apifincheck.model.Category;
import com.fincheck.apifincheck.model.TransactionType;
import com.fincheck.apifincheck.model.User;
import com.fincheck.apifincheck.repository.UserRepository;
import com.fincheck.apifincheck.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTService jwtService;

  @Override
  public TokenResponseDTO signup(SignupDTO signUpDTO) {
    boolean emailTaken = userRepository.existsByEmail(signUpDTO.getEmail());

    if (emailTaken) {
      throw new RuntimeException("This email is already in use.");
    }

    String hashedPassword = passwordEncoder.encode(signUpDTO.getPassword());

    User user = new User();
    user.setName(signUpDTO.getName());
    user.setEmail(signUpDTO.getEmail());
    user.setPassword(hashedPassword);

    List<Category> categories = new ArrayList<>();
    // Income categories
    categories.add(new Category("Salário", "salary", TransactionType.INCOME, user));
    categories.add(new Category("Freelance", "freelance", TransactionType.INCOME, user));
    categories.add(new Category("Outro", "other",  TransactionType.INCOME, user));

    // Expense categories
    categories.add(new Category("Casa", "home",  TransactionType.EXPENSE, user));
    categories.add(new Category("Alimentação", "food",  TransactionType.EXPENSE, user));
    categories.add(new Category("Educação", "education",  TransactionType.EXPENSE, user));
    categories.add(new Category("Lazer", "fun",  TransactionType.EXPENSE, user));
    categories.add(new Category("Mercado", "grocery",  TransactionType.EXPENSE, user));
    categories.add(new Category("Roupas", "clothes",  TransactionType.EXPENSE, user));
    categories.add(new Category("Transporte", "transport",  TransactionType.EXPENSE, user));
    categories.add(new Category("Viagem", "travel",  TransactionType.EXPENSE, user));
    categories.add(new Category("Outro", "other",  TransactionType.EXPENSE, user));

    user.setCategories(categories);

    User userSaved = userRepository.save(user);

    String accessToken = jwtService.generateToken(userSaved);

    return new TokenResponseDTO(accessToken);
  }

  @Override
  public TokenResponseDTO signin(SigninDTO signinDTO) {
    User user =
      userRepository
        .findByEmail(signinDTO.getEmail())
        .orElseThrow(() -> new RuntimeException("Invalid credentials."));

    boolean passwordMatches = passwordEncoder.matches(signinDTO.getPassword(), user.getPassword());

    if (!passwordMatches) {
      throw new RuntimeException("Invalid credentials");
    }

    String accessToken = jwtService.generateToken(user);

    return new TokenResponseDTO(accessToken);
  }
}

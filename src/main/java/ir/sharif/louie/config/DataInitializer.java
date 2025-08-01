package ir.sharif.louie.config;

import ir.sharif.louie.models.db.User;
import ir.sharif.louie.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting data initialization...");
        initializeDefaultUsers();
        System.out.println("Data initialization completed!");
    }

    private void initializeDefaultUsers() {
        if (userRepository.count() == 0) {
            System.out.println("No users found. Creating default users...");

            List<User> defaultUsers = Arrays.asList(
                    new User("artist1", passwordEncoder.encode("password123")),
                    new User("artist2", passwordEncoder.encode("password123")),
                    new User("painter", passwordEncoder.encode("password123")),
                    new User("designer", passwordEncoder.encode("password123"))
            );

            userRepository.saveAll(defaultUsers);

            System.out.println("✅ Default users created successfully:");
            System.out.println("   🎨 Username: artist1 | Password: password123");
            System.out.println("   🎨 Username: artist2  | Password: password123");
            System.out.println("   🖌️  Username: painter  | Password: password123");
            System.out.println("   � Username: designer | Password: password123");
            System.out.println("=" .repeat(60));
        } else {
            System.out.println("ℹ️  Users already exist. Skipping user creation.");
            System.out.println("   Current user count: " + userRepository.count());
        }
    }
}

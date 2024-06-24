package com.example.proiect_poo;

import com.example.proiect_poo.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Base64;
import java.util.regex.Pattern;

public class DBUtils {
    //    Initializes an EntityManagerFactory which is used to create EntityManager instances.
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("mySQLPersistence");
    //    manages the interactions between the application and the database

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();

            if (username != null) {
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 908, 600));
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeSceneTraining(ActionEvent event, String fxmlFile, String title, String category) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();

            if (category != null) {
                WorkoutPageController workoutPageController = loader.getController();
                workoutPageController.setCategory(category);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 908, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void signUpUser(ActionEvent event, String username, String password, String gender, String email) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            if (!isValidUsername(username)) {
                showAlert(Alert.AlertType.ERROR, "Invalid username. It must be 5-15 characters long, start with a letter, and can contain letters, digits, and underscores.");
            } else if (userExists(em, username)) {
                showAlert(Alert.AlertType.ERROR, "You cannot use this username.");
            } else if (!isValidEmail(email)) {
                showAlert(Alert.AlertType.ERROR, "Invalid email format!");
            } else if (!isValidPassword(password)) {
                showAlert(Alert.AlertType.ERROR, "Password must contain at least one uppercase letter, one lowercase letter, and one number.");
            } else {
                String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
                User user = new User();
                user.setUsername(username);
                user.setPassword(encodedPassword);
                user.setGender(gender);
                user.setEmail(email);

                em.persist(user);
                em.getTransaction().commit();

                changeScene(event, "logged-in.fxml", "Welcome!", username);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        EntityManager em = emf.createEntityManager();

        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            String decodedPassword = new String(Base64.getDecoder().decode(user.getPassword()));
            if (decodedPassword.equals(password)) {
                changeScene(event, "logged-in.fxml", "Welcome!", username);
            } else {
                showAlert(Alert.AlertType.ERROR, "The provided credentials are incorrect!");
            }
        } catch (NoResultException e) {
            showAlert(Alert.AlertType.ERROR, "Provided credentials are incorrect!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void resetPassword(String email) {
        EntityManager em = emf.createEntityManager();

        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();

            String newPassword = PasswordUtils.generateRandomPassword(6);
            String encodedPassword = Base64.getEncoder().encodeToString(newPassword.getBytes());

            em.getTransaction().begin();
            user.setPassword(encodedPassword);
            em.getTransaction().commit();

            showAlert(Alert.AlertType.INFORMATION, "A new password has been generated. This is your new password: " + newPassword);
        } catch (NoResultException e) {
            showAlert(Alert.AlertType.ERROR, "The provided email is not associated with any account!");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static boolean userExists(EntityManager em, String username) {
        try {
            em.createQuery("SELECT 1 FROM User u WHERE u.username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    private static boolean isValidUsername(String username) {
        String usernamePattern = "^[a-zA-Z][a-zA-Z0-9_]{4,14}$";
        return Pattern.matches(usernamePattern, username);
    }

    private static boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(emailPattern, email);
    }

    private static boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        return Pattern.matches(passwordPattern, password);
    }

    private static void showAlert(Alert.AlertType alertType, String content) {
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.show();
    }
}

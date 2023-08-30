package com.web;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private static User user;

    public static void main(String[] args) {


        List<User> invalidUsers = new ArrayList<>(); // создаем список некорректных пользователей

        UserDAO userDAO = null;

        List<User> userList = userDAO.findAllUsers();

        for (User user : userList) {
            if (!validateUser(user)) {
                invalidUsers.add(user); // добавляем невалидных в список
            }
        }

        if (!invalidUsers.isEmpty()) {
            // Записываем в файл
            File outputFile = new File("invalid_users.txt");
            writeUsersToFile(outputFile, invalidUsers);

            throw new RuntimeException("Найдены не корректные пользователи. Проверьте файл invalid_users.txt для детального уточнения.");
        }
    }

    private static void writeUsersToFile(File file, List<User> users) {
        try (FileWriter writer = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for (User user : users) {
                bufferedWriter.write("Не валидный user " + user.getId());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean validateUser(@NotNull User user) {
        boolean isValid = true;
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isBlank()) {
            isValid = false;
        }
        return isValid;
    }
}

package com.example.estate.model;


import com.example.estate.model.User;

import java.io.*;
import java.util.HashMap;

public class UserDAO {
    private File file;

    public UserDAO(String path) {
        file = new File(path);
    }

    public synchronized HashMap<String, User> loadFile() {
        HashMap<String, User> users = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String login = reader.readLine();
            String password = reader.readLine();
            String role = reader.readLine();

            while (login != null) {
                users.put(login, new User(password,role));
                login = reader.readLine();
                password = reader.readLine();
                role = reader.readLine();

            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public synchronized void saveToFile(HashMap<String, User> users) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (HashMap.Entry<String, User> entry : users.entrySet()) {
                writer.write(entry.getKey() + "\n");
                writer.write(entry.getValue().getPassword() + "\n");
                writer.write(entry.getValue().getRole() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

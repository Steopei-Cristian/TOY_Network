import domain.User;
import repo.file.FriendshipRepo;
import repo.file.UserRepo;
import service.NetworkService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Main {
    private static void printMenu() {
        System.out.println("Menu");
        System.out.println("1. Add User");
        System.out.println("2. Delete User");
        System.out.println("3. Add Friendship");
        System.out.println("4. Delete Friendship");
        System.out.println("5. View group count");
        System.out.println("6. View the biggest group");
        System.out.println("Anything else -> Exit");
        System.out.println("Command: ");
    }

    private static void handleAddUser(NetworkService network) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Insert username: ");
            String username = reader.readLine();
            System.out.println("Insert password: ");
            String password = reader.readLine();
            network.addUser(username, password);
            System.out.println("User successfully added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleDeleteUser(NetworkService network) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Insert userId: ");
            String id = reader.readLine();
            network.removeUser(Long.valueOf(id));
            System.out.println("User successfully deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleAddFriendship(NetworkService network) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Insert id1: ");
            String id1 = reader.readLine();
            System.out.println("Insert id2: ");
            String id2 = reader.readLine();
            network.addFriend(Long.valueOf(id1), Long.valueOf(id2));
            System.out.println("The users are now friends");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleRemoveFriendship(NetworkService network) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Insert id1: ");
            String id1 = reader.readLine();
            System.out.println("Insert id2: ");
            String id2 = reader.readLine();
            network.removeFriend(Long.valueOf(id1), Long.valueOf(id2));
            System.out.println("The users are not friends anymore");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleGroupView(NetworkService network) {
        System.out.println(network.groupCount());
    }

    private static void handleBiggestGroup(NetworkService network) {
        System.out.println("To be continued..");
    }

    public static void main(String[] args) {
        Path userPath = Paths.get("./src/users.json");
        Path frPath = Paths.get("./src/friendships.json");
        try {
            UserRepo userRepo = new UserRepo(userPath);
            FriendshipRepo frRepo = new FriendshipRepo(frPath);
            NetworkService serv = new NetworkService(frRepo, userRepo);

            boolean keepRunning = true;
            while(keepRunning) {
                printMenu();
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                try {
                    String input = reader.readLine();
                    switch (input) {
                        case "1" -> {
                            handleAddUser(serv);
                        }
                        case "2" -> {
                            handleDeleteUser(serv);
                        }
                        case "3" -> {
                            handleAddFriendship(serv);
                        }
                        case "4" -> {
                            handleRemoveFriendship(serv);
                        }
                        case "5" -> {
                            handleGroupView(serv);
                        }
                        case "6" -> {
                            handleBiggestGroup(serv);
                        }
                        default -> {
                            keepRunning = false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
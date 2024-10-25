package service;

import domain.Friendship;
import domain.MyGraph;
import domain.User;
import repo.file.FriendshipRepo;
import repo.file.UserRepo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkService implements Service {
    private FriendshipRepo frRepo;
    private UserRepo userRepo;
    private MyGraph<Long, User> network;

    public NetworkService(FriendshipRepo frRepo, UserRepo userRepo)
            throws IOException {
        this.frRepo = frRepo;
        this.userRepo = userRepo;
        constructNetwork();
        // System.out.println(userRepo);
    }

    private void constructNetwork() {
        List<List<User>> links = new ArrayList<>();
        frRepo.getAll().forEach(fr -> {
            User u1 = userRepo.find(fr.getId1());
            User u2 = userRepo.find(fr.getId2());
            links.add(Arrays.asList(u1, u2));
        });
        network = new MyGraph<>(userRepo.getAll(), links);
    }

    public int groupCount() {
        network.countComps();
        return network.getCompCount();
    }

    public void removeUser(Long userId) throws ClassNotFoundException, IOException {
        User user = userRepo.delete(userId); // delete from user repo
        try { // unlink friends from network
            network.unlinkEntity(user);
            frRepo.removeUserFriendships(userId);
        } catch (NullPointerException e) {
            System.out.println("Deleted use had no friends");
        }
        constructNetwork();
    }

    public void addUser(String username, String password) throws ClassNotFoundException, IOException {
        Long id = userRepo.getLastID() + 1;
        // System.out.println("NewUser ID: " + id);
        User newUser = new User(id, username, password);
        userRepo.add(newUser); // add in user list
        network.setAll(userRepo.getAll()); // update network (vertex with degree 0)
    }

    public void removeFriend(Long idUser, Long idFriend) throws ClassNotFoundException, IOException {
        network.unlink(userRepo.find(idUser), userRepo.find(idFriend));
        frRepo.removeFriendship(idUser, idFriend);
    }

    public void addFriend(Long idUser, Long idFriend) throws ClassNotFoundException, IOException {
        if(!network.link(userRepo.find(idUser), userRepo.find(idFriend)))
            return;
        Long id = frRepo.getLastID() + 1;
        Friendship newFr = new Friendship(id, idUser, idFriend);
        frRepo.add(newFr);
    }

    public List<User> maxComp() {
        return network.maxComp();
    }
}

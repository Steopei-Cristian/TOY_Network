package repo.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.security.jgss.GSSUtil;
import domain.Entity;
import domain.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class UserRepo extends AbstractFileRepo<Long, User> {
    public UserRepo(Path path) throws IOException {
        super(path);
    }

    @Override
    protected void read() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(path.toFile(),
                mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        users.forEach(this::add);
    }

    @Override
    protected void write() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(path.toFile(), getAll());
    }

}

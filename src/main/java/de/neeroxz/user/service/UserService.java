package de.neeroxz.user.service;

import de.neeroxz.user.model.Password;
import de.neeroxz.user.model.User;

import java.util.Optional;

/**
 * Class: UserService
 *
 * @author NeeroxZ
 * @date 06.02.2025
 */
public class UserService implements IUserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username)
    {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, Password password)
    {
       return userRepository.findUserByUsernameAndPassword(username, password);
    }


    @Override
    public void saveUser(User user)
    {
        userRepository.saveUser(user);
    }

    @Override
    public void deleteUser(User user)
    {
        userRepository.deleteUser(user);
        //todo
        //wie starte ich jetzt eine neue Login Anwendung ?
    }

    @Override
    public void updateUser(User user)
    {

    }
}

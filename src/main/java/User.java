import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private String name;
    private String password;
    private Set<String> friends;

    /*
     * Constructor with passed name and password
     * */
    public User(String name, String password){
        this.name = name;
        this.password = password;
        friends = new HashSet<>();
    }

    /*
    * Returns users name
    * */
    public String getName(){
        return name;
    }

    /*
     * Returns users password
     * */
    public String getPassword(){
        return password;
    }

    /*
     * Returns if passed user as potential new friend can be added and adds if possible.
     * Returns false if two are already friends, otherwise adds as a friend and returns true.
     * */
    public boolean addFriend(String friend){
        if(friends.contains(friend))return false;
        friends.add(friend);
        return true;
    }

    /*
     * Returns true, if friend can be removed and removes them.
     * */
    public boolean removeFriend(String friend){
        if(!friends.contains(friend))return false;
        friends.remove(friend);
        return true;
    }
}
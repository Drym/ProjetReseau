package protocol.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;

public class MettreAJour extends Service {
    //TODO
    private static final long serialVersionUID = 41;

    private String name;
    private String newName = null;
    private Set<String> nicknames = null;

    public MettreAJour() {
        super("UPDATE");
        nicknames = new HashSet<String>();
    }

    public MettreAJour(String name, Set<String> nicknames) {
        super("UPDATE");
        this.name = name;
        this.nicknames = nicknames;
    }

    public MettreAJour(String name, String newName) {
        super("UPDATE");
        this.name = name;
        this.newName = newName;
    }

    public MettreAJour(String name, String newName, Set<String> nicknames) {
        super("UPDATE");
        this.name = name;
        this.newName = newName;
        this.nicknames = nicknames;
    }

    @Override
    public HashMap<String, Set<String>> exec(HashMap<String, Set<String>> map) throws InvalidRequestException {
        if(!map.containsKey(name)) throw new InvalidRequestException(
                "Le nom "+name+" n'a pas pu être modifié car n'est pas présent sur le serveur.");


        if(newName == null) {
            map.remove(name);
            map.put(name, nicknames);
        }
        else if (nicknames == null) {
            nicknames = map.get(name);
            map.remove(name);
            map.put(newName, nicknames);
        }
        else {
            map.remove(name);
            map.put(newName, nicknames);
        }

        return map;
    }

    @Override
    public Response createResponse(boolean status, String message, HashMap<String, Set<String>> map) {
        return new Response(status, message);
    }
}

package protocol.services;

import java.util.HashMap;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;

/**
 * Supprimer est une classe permetant de supprimer un nom stocké sur le serveur
 */
public class Supprimer extends Service {
    //TODO serialVersionUID et les accents dans l'exception de exec
    private static final long serialVersionUID = 42;

    private String name;

    /**
     * Constructeur de Supprimer
     *
     * Permet la suppression d'un nom stocké sur le serveur
     *
     * @param name
     *              Le nom à supprimer
     */
    public Supprimer(String name) {
        super("DELETE");
        this.name = name;
    }

    @Override
    public HashMap<String, Set<String>> exec(HashMap<String, Set<String>> map) throws InvalidRequestException {
        if(!map.containsKey(name)) throw new InvalidRequestException(
                "Le nom "+name+" n'a pas pu être supprimer car il n'est pas présent sur le serveur.");

        map.remove(name);

        return map;
    }

    @Override
    public Response createResponse(boolean status, String message, HashMap<String, Set<String>> map) {
        return new Response(status, message);
    }
}

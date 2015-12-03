package protocol.services;

import protocol.InvalidRequestException;
import protocol.Response;

/**
 * Supprimer est une classe qui hérite de Service et permettant de supprimer un nom stocké sur le serveur
 */
public class Supprimer extends Service {
	private static final long serialVersionUID = -4489768322140026265L;
	
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
    public Response exec() throws InvalidRequestException {
        if(!serverData.containsKey(name)) throw new InvalidRequestException(
                "Le nom "+name+" n'a pas pu être supprimé car il n'est pas présent sur le serveur.");

        serverData.remove(name);

        return new Response(true, "Utilisateur supprimé");
    }
}

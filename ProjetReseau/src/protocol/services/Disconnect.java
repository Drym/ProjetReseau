package protocol.services;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;

/**
 * Disconnect est une classe qui hérite de Service et permettant au client de se déconnecter du serveur
 */
public class Disconnect extends Service {
	private static final long serialVersionUID = 4431298312019946475L;

    /**
     * Constructeur de Disconnect
     *
     * Permet la déconnexion du client
     */
	public Disconnect() {
        super("DISCONNECT");
    }


    @Override
    public Hashtable<String, Set<String>> exec(Hashtable<String, Set<String>> map) throws InvalidRequestException {
        return map;
    }

    @Override
    public Response createResponse(boolean status, String message, Hashtable<String, Set<String>> map) {
        return new Response(status, message);
    }
}

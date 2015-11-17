package protocol.services;

import protocol.InvalidRequestException;
import protocol.Response;
import java.util.HashMap;
import java.util.Set;

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
    public HashMap<String, Set<String>> exec(HashMap<String, Set<String>> map) throws InvalidRequestException {
        return map;
    }

    @Override
    public Response createResponse(boolean status, String message, HashMap<String, Set<String>> map) {
        return new Response(status, message);
    }
}

package protocol.services;

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
    public Response exec() throws InvalidRequestException {
        return new Response(true, "Déconnexion réussie");
    }
}

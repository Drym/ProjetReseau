package rmi.protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import protocol.InvalidRequestException;
import rmi.server.ResponseRMI;

public interface Ajouter extends Remote {
	public ResponseRMI execute(String name, Set<String> nicknames) throws RemoteException ;
}

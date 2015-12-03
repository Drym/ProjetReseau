package rmi.protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.server.ResponseRMI;

public interface Lister extends Remote {
	public ResponseRMI execute() throws RemoteException;
}

package rmi.protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.server.ResponseRMI;

public interface Disconnect extends Remote {
	public ResponseRMI execute() throws RemoteException;
}

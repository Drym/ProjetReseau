package rmi.server.implementation;

import java.rmi.RemoteException;

import rmi.protocol.Disconnect;
import rmi.server.ResponseRMI;

public class DisconnectImpl extends ServiceImpl implements Disconnect {
	private static final long serialVersionUID = -1776346449596039228L;

	public DisconnectImpl() throws RemoteException {
		super();
	}

	@Override
	public ResponseRMI execute() throws RemoteException {
		return new ResponseRMI(true, "Déconnexion réussie");
	}

}

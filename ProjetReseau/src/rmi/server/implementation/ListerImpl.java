package rmi.server.implementation;

import java.rmi.RemoteException;

import rmi.protocol.Lister;
import rmi.server.ResponseRMI;

public class ListerImpl extends ServiceImpl implements Lister {
	private static final long serialVersionUID = 2002141695112256113L;

	public ListerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResponseRMI execute() throws RemoteException {
		return new ResponseRMI(true, "Liste des clients", data);
	}

}

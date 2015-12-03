package rmi.server.implementation;

import java.rmi.RemoteException;
import java.util.Set;

import rmi.protocol.Ajouter;
import rmi.server.ResponseRMI;

public class AjouterImpl extends ServiceImpl implements Ajouter {
	private static final long serialVersionUID = -4526918094113412413L;
	
	public AjouterImpl() throws RemoteException {
		super();
	}

	@Override
	public ResponseRMI execute(String name, Set<String> nicknames) throws RemoteException {
		if(name == null || name.equals("")) return new ResponseRMI(false, "Veuillez entrer un nom à ajouter non nul.");
		
		if(data.containsKey(name)) return new ResponseRMI(false,
				"Le nom "+name+" n'a pas pu être ajouté car déjà présent sur le serveur.");
		
		for (String key : data.keySet()) {
			Set<String> tmp = data.get(key);
			for (String nname : tmp) {
				if(nicknames.contains(nname)) return new ResponseRMI(false,
						"Le nom " + name + " n'a pas pu être ajouté car le surnom "+nname+" est déjà présent sur le serveur.");
			}
		}
		
		data.put(name, nicknames);
		
		return new ResponseRMI(true, "Utilisateur ajouté", data);
	}

}

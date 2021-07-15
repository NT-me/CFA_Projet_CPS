package com.services;

import com.data.ConnectionInfo;
import fr.sorbonne_u.components.interfaces.RequiredCI;

import java.util.Set;

public interface ParticipantCI extends RequiredCI {

    public Set<ConnectionInfo> connexion();
}

package edu.cs340.spring2018group3.firebaseplugin.callbacks;

import java.util.List;

import edu.cs340.spring2018group3.pluginsdk.dtos.UserDTO;

/**
 * @author Samuel Nuttall
 */
public interface DTOCallback {
    
    void onCallback(List<Object> DTOList);
}

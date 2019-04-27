package edu.cs340.spring2018group3.firebaseplugin.firebasedtos;

import java.util.ArrayList;
import java.util.List;

import edu.cs340.spring2018group3.pluginsdk.dtos.CommandDTO;

/**
 * @author Samuel Nuttall
 */
public class FirebaseUtil {
    
    public static List<CommandDTO> convertFirebaseCmdList(List<FirebaseCommand> fireCmdList) {
        List<CommandDTO> convertedList = new ArrayList<>();
        for (FirebaseCommand cmd : fireCmdList) {
            convertedList.add(cmd.convertToDTO());
        }
        return convertedList;
    }
}

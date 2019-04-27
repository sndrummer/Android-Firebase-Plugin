package edu.cs340.spring2018group3.firebaseplugin.firebasedtos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs340.spring2018group3.pluginsdk.dtos.CommandDTO;

/**
 * @author Samuel Nuttall
 */
public class FirebaseCommand {
    
    private String mClassName;
    private String mMethodName;
    private List<String> mParamTypes;
    private List<String> mParamValues;

    public FirebaseCommand(CommandDTO commandDTO){
        mClassName = commandDTO.getClassName();
        mMethodName = commandDTO.getMethodName();
        mParamTypes = new ArrayList<>(Arrays.asList(commandDTO.getParamTypes()));
        mParamValues = new ArrayList<>(Arrays.asList(commandDTO.getParamValues()));
    }
    
    public CommandDTO convertToDTO() {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.setClassName(mClassName);
        commandDTO.setMethodName(mMethodName);
        String[] paramTypes = mParamTypes.toArray(new String[mParamTypes.size()]);
        commandDTO.setParamTypes(paramTypes);
        String[] paramValues = mParamValues.toArray(new String[mParamValues.size()]);
        commandDTO.setmParamValues(paramValues);
        
        return commandDTO;
    }
    
    
    public FirebaseCommand(){
    
    }
    
    
    public String getmClassName() {
        return mClassName;
    }
    
    public void setmClassName(String mClassName) {
        this.mClassName = mClassName;
    }
    
    public String getmMethodName() {
        return mMethodName;
    }
    
    public void setmMethodName(String mMethodName) {
        this.mMethodName = mMethodName;
    }
    
    public List<String> getmParamTypes() {
        return mParamTypes;
    }
    
    public void setmParamTypes(List<String> mParamTypes) {
        this.mParamTypes = mParamTypes;
    }
    
    public List<String> getmParamValues() {
        return mParamValues;
    }
    
    public void setmParamValues(List<String> mParamValues) {
        this.mParamValues = mParamValues;
    }
}

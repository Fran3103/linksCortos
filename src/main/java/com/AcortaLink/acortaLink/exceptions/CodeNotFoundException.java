package com.AcortaLink.acortaLink.exceptions;

public class CodeNotFoundException  extends  RuntimeException{

    public CodeNotFoundException(String code){
        super("No existe ningun enlace con ese codigo: "+ code);
    }
}

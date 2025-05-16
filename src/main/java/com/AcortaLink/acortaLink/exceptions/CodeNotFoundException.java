package com.AcortaLink.acortaLink.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CodeNotFoundException  extends  RuntimeException{

    public CodeNotFoundException(String code){
        super("No existe ningun enlace con ese codigo: "+ code);
    }
}

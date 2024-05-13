package org.example.commands.authorization;

import org.example.commands.Command;

public class Register extends Command implements AuthorizationCommand  {
    @Override
    public void execute() {
        responseManager.addToSend("Вы успешно зарегестрированны и авторизованны", this);
        loggerHelper.debug("Команда "+this.getClass().getName()+"от адресса "+responseManager.getResponse(this).getAddress() +" выполнена");
        responseManager.send(this);
    }


    @Override
    public void validate(String arg1) {
    }
}

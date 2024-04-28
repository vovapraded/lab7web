package org.common.commands.authorization;

import org.common.commands.Command;

public class Login extends Command implements AuthorizationCommand {
    @Override
    public void execute() {
        responseManager.addToSend("Вы успешно авторизованы", this);
        responseManager.send(this);
    }

    @Override
    public void validate(String arg1) {
    }
}

package org.example.managers;

import org.common.commands.Command;
import org.common.commands.authorization.Register;
import org.common.managers.Collection;
import org.common.network.Response;
import org.common.utility.InvalidFormatException;
import org.example.authorization.AuthorizationException;
import org.example.authorization.AuthorizationManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * A class for executing commands
 */
public class ExecutorOfCommands {



    private final Collection collection = Collection.getInstance();
    private  final CurrentResponseManager responseManager;
    private static final Logger logger = LoggerFactory.getLogger(ExecutorOfCommands.class);


    public ExecutorOfCommands( CurrentResponseManager responseManager){
        this.responseManager = responseManager;
    }

    public boolean isRegisterCommand(Command command){
        if (command instanceof Register) return true;
        else return false;
    }
    public boolean checkAuthorizationAndGenerateResponse(@NotNull Command command,SocketAddress address){
        var login=command.getAuthorization().getLogin();
        var password=command.getAuthorization().getPassword();
        var result  = AuthorizationManager.checkLoginAndPassword(login,password);
        var loginCorrect = result.getLeft();
        var passwordCorrect = result.getRight();
        responseManager.initResponse(command,Response.builder().loginCorrect(loginCorrect).passwordCorrect(passwordCorrect).address(address).build());
        return loginCorrect & passwordCorrect;
    }
//    public boolean checkAuthorization(Command command)  {
//
//    }
    public void executeCommand(Command command,SocketAddress address) throws InvalidFormatException {
        command.setResponseManager(responseManager);
        try {
            if (isRegisterCommand(command)) {
                checkAuthRegisterCommand(command, address);
            } else {
                checkAuthCommonCommand(command, address);
            }
            logger.debug("Пользователь "+command.getAuthorization().getLogin()+ " авторизован успешно");
            command.execute();
        }catch (AuthorizationException e){
            logger.debug("Пользователь "+command.getAuthorization().getLogin()+ " не авторизован");
            responseManager.addToSend(e.getMessage(),command);
            responseManager.send(command);
        }


    }
    public void checkAuthRegisterCommand(Command command, SocketAddress address) throws AuthorizationException {
        var response=Response.builder()
                .address(address)
                .loginCorrect(false).passwordCorrect(true)
                .build();
        responseManager.initResponse(command,response);
                var login=command.getAuthorization().getLogin();
                var password=command.getAuthorization().getPassword();
                AuthorizationManager.register(login,password);
                response.setLoginCorrect(true);


    }

        public void checkAuthCommonCommand(Command command,SocketAddress address) throws AuthorizationException {
            var isAuthorized=checkAuthorizationAndGenerateResponse(command,address);
            if ( !isAuthorized ){
                logger.debug("Команда "+command.getClass().getName()+" не выполнена, клиент "+responseManager.getResponse(command).getAddress()+" не авторизован");
                if (!responseManager.getResponse(command).isLoginCorrect())
                    throw  new AuthorizationException( "Вы не авторизованы, неверный логин");
                else if (!responseManager.getResponse(command).isPasswordCorrect())
                    throw  new AuthorizationException( "Вы не авторизованы, неверный пароль");

            }

    }



//
//
//



//








}







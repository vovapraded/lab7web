package org.common.utility;

import lombok.Getter;

/**
 * An enum that stores the number of arguments for each command
 */
public enum Commands {
    help(0,false),
    info(0,false),
    show(0,false),
    insert(1,true),
    update(1,true),
    remove_key(1,false),
    clear(0,false),
    save(0,false),
    execute_script(1,false),
    exit(0,false),
    remove_greater(0,true),
    replace_if_greater(1,true),
    remove_greater_key(1,false),
    average_of_price(0,false),
    filter_less_than_venue(1,false),
    print_descending(0,false),
    login(0,false),
    register(0,false),
    whoami(0,false),
    empty(0,false) ;
    @Getter
    private final int countArgs;
    @Getter
    private final boolean ticketArgIsNeeded;

    Commands(int countArgs, boolean ticketArgIsNeeded) {
        this.countArgs = countArgs;
        this.ticketArgIsNeeded = ticketArgIsNeeded;
    }

}

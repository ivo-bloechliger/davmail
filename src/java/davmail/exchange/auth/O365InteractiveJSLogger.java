/*
 * DavMail POP/IMAP/SMTP/CalDav/LDAP Exchange Gateway
 * Copyright (C) 2010  Mickael Guessant
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package davmail.exchange.auth;

import javafx.scene.web.WebEngine;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings({"rawtypes", "unchecked"})
public class O365InteractiveJSLogger {
    private static final Logger LOGGER = Logger.getLogger(O365InteractiveJSLogger.class);
    public void log(String message) {
        LOGGER.info(message);
    }

    public static void register(WebEngine webEngine) {

        try {
            Class jsObjectClass = Class.forName("netscape.javascript.JSObject");
            Method setMemberMethod = jsObjectClass.getDeclaredMethod("setMember", String.class,Object.class);

            Object window = webEngine.executeScript("window");
            setMemberMethod.invoke(window, "davmail", new O365InteractiveJSLogger());

            webEngine.executeScript("console.log = function(message) { davmail.log(message); }");
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.info("netscape.javascript.JSObject not available");
        }

    }
}

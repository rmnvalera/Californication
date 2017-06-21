/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package californication.SetWallpaper;

/**
 *
 * @author Roman
 */
public class UserHomePath {
    public static String getUserHomePath()
      {
//        System.out.println("User Home Path: "+
//        System.getProperty("user.home"));
        return System.getProperty("user.home");
      }
}

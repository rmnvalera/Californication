/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package californication.SetWallpaper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
/**
 *
 * @author Roman
 */
public class VerWindows {
    
    public static String Winversion;
    public static String WinPath;
    public static File source;
    public static File dest, destClear;
    /**
     * @param args the command line arguments
     */
    
    
     public VerWindows() throws IOException{
        // TODO code application logic here
           
        if(isWindows()){
            //System.out.println("Windows " + getOSVerion());
            Winversion = "Windows " + getOSVerion();
            
            if(Winversion.equals("Windows 10.0")){
                Windows10();
            }else{
                if(Winversion.equals("Windows 5.1")){
                    WindowsXP();
                }                
            }
                             
            //System.out.println(WinPath);
            //System.out.println(source);
//          
            try{
                copy(source, dest); //Запись текста
                copy(source, destClear); // для очистки
            }catch(IOException e){
                System.err.println("No copy imege: " + source);
            }
            
        }else{
            System.err.println("Вы используете не WINDOWS XP or WONDOWS 10");
        }
    }
    
    
     public static void Windows10(){
        WinPath = UserHomePath.getUserHomePath() + "\\AppData\\Roaming\\Microsoft\\Windows\\Themes\\CachedFiles\\";
        source = new File(getFileDirectory(WinPath));
        dest = new File(getProjectDir() +"\\" + "Wallp10.jpg");
        destClear = new File(getProjectDir() +"\\" + "Wallp10Clean.jpg");
     }
     
     public static void Windows7(){
         System.err.println("You have Windows 7");
     }
     
     public static void WindowsXP(){
        WinPath = UserHomePath.getUserHomePath() + "\\Local Settings\\Application Data\\Microsoft\\Wallpaper1.bmp";
        source = new File(WinPath);
        dest = new File(getProjectDir() +"\\" + "WallpXP.jpg");
        destClear = new File(getProjectDir() +"\\" + "WallpXPClean.jpg");
     }
     
    
    
    public static String getOSVerion() {
        String os = System.getProperty("os.version");
        return os;
    }
    
    public static boolean isWindows(){

        String os = System.getProperty("os.name").toLowerCase();
        //windows
        return (os.indexOf( "win" ) >= 0); 

    }
    
    
//      public static String getUserHomePath()
//      {
////        System.out.println("User Home Path: "+
////        System.getProperty("user.home"));
//        return System.getProperty("user.home");
//      }
    
      public static String getFileDirectory(String Path){
          
        String list[] = new File(Path).list();
                   
//            for(int i = 0; i < list.length; i++)
//                System.out.println(list[i]);
            return Path + list[0];
      }
      
      
      public static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
      
      public static void delateWallpaper(){
          dest.delete();
          destClear.delete();
      }
      
      private static String getProjectDir() {
		String path=System.getProperty("user.dir");

//                URL url = CreateScreenCapture.class.getProtectionDomain().getCodeSource().getLocation();
//                String path = url.toString()+"createscreencapture";
//                System.out.println(path.substring(6));
                return path;//.substring(6);
                
	}
   
      
     
}

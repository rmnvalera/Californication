/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package californication.socket;


import californication.SetWallpaper.SetImageText;
import californication.SetWallpaper.SetWallpaper;
import californication.SetWallpaper.UserHomePath;
import californication.SetWallpaper.VerWindows;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import java.util.HashMap;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.UINT_PTR;
import com.sun.jna.win32.*;


/**
 *
 * @author Roman
 */
public class Client {
    
        private BufferedReader in;
	private PrintWriter out;
	private Socket socket;

        public String ipText;
        public String userText;
        
        public Resender resend;
        public SetImageText SetImageText;
        
        public boolean stopclientoll = false;
        
        public Client(String ip, String user){
            ipText = ip;
            userText = user;
           
        }
        
        public void StopClientStop(Boolean stop){
            stopclientoll = stop;
        }
        
        public void getIPuser(){
            System.out.println(ipText + " " + userText);
        }
        
        
        public void ClientStarting(){
            Scanner scan = new Scanner(System.in);
            try {
			// Подключаемся в серверу и получаем потоки(in и out) для передачи сообщений
			socket = new Socket(ipText, Const.Port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			//System.out.println("Введите свой ник:");
			out.println(userText);

			// Запускаем вывод всех входящих сообщений в консоль
			resend = new Resender();
			resend.start();

			// Пока пользователь не введёт "exit" отправляем на сервер всё, что
			// введено из консоли
                        //printStr("s");
                        
//			String str = "";
//			while (!str.equals("exit")) {
//				str = scan.nextLine();
//				out.println(str);
//			}
//			resend.setStop();
		} catch (Exception e) {
			e.printStackTrace();
		} //finally {
//			close();
//		}
        }
        
        
        public void printStr(String sendtext){
//            try {
//                Scanner scan = new Scanner(System.in);
//                String str = "";
//                  while (!str.equals("exit")) {
//                          str = sendtext;
//                          out.println(str);
//                  }
//                  resend.setStop();
//            }catch(Exception e){
//                e.printStackTrace();
//            } finally {
//		close();
//            }
            String str = "";
            str = sendtext;
            out.println(str);
        }
        
        
	/**
	 * Запрашивает у пользователя ник и организовывает обмен сообщениями с
	 * сервером
	 */
	public Client() {
		Scanner scan = new Scanner(System.in);

		System.out.println("Введите IP для подключения к серверу.");
		System.out.println("Формат: xxx.xxx.xxx.xxx");

		String ip = scan.nextLine();

		try {
			// Подключаемся в серверу и получаем потоки(in и out) для передачи сообщений
			socket = new Socket(ip, Const.Port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			System.out.println("Введите свой ник:");
			out.println(scan.nextLine());

			// Запускаем вывод всех входящих сообщений в консоль
			Resender resend = new Resender();
			resend.start();

			// Пока пользователь не введёт "exit" отправляем на сервер всё, что
			// введено из консоли
			String str = "";
//			while (!str.equals("exit")) {
//				str = scan.nextLine();
//				out.println(str);
//			}
                        while (stopclientoll == false) {
				str = scan.nextLine();
				out.println(str);
			}
                        //stopclientoll = false;
			resend.setStop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * Закрывает входной и выходной потоки и сокет
	 */
	private void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			System.err.println("Потоки не были закрыты!");
		}
	}

	/**
	 * Класс в отдельной нити пересылает все сообщения от сервера в консоль.
	 * Работает пока не будет вызван метод setStop().
	 *
	 */
	private class Resender extends Thread {

		private boolean stoped;
		
		/**
		 * Прекращает пересылку сообщений
		 */
		public void setStop() {
			stoped = true;
                        
		}

//                public void writeFile(String text){
//                    try(FileWriter writer = new FileWriter("D:\\californication.txt", false))
//                    {
//                       // запись всей строки
//                        //String text = "Мама мыла раму, раму мыла мама";
//                        writer.write(text);
//                        // запись по символам
//                        //writer.append('\n');
//                        //writer.append('E');
//
//                        writer.flush();
//                    }catch(IOException ex){
//
//                        System.out.println(ex.getMessage());
//                    } 
//                }
                
		/**
		 * Считывает все сообщения от сервера и печатает их в консоль.
		 * Останавливается вызовом метода setStop()
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
                    int iterator;
			try {
				while (!stoped) {
					String str = null;
                                        VerWindows ver = new VerWindows();
                                        SetImageText = new SetImageText(VerWindows.dest);
                                        for(int i = 0; i < 600; i+=20){
                                            str = in.readLine();
                                            SetImageText.addTextToImage(str, 50, 0 + i, 350, 1, SetImageText.LEFT_TEXT_MODE, "Arial", Font.BOLD, 21, Color.BLACK);
                                            //img.saveAs(UserHomePath.getUserHomePath() + "\\Pictures\\wallperText.png");
                                            SetImageText.saveAs(VerWindows.dest.getPath());
                                            
                                            SetWallpaper.SPI.INSTANCE.SystemParametersInfo(
                                                new UINT_PTR(SetWallpaper.SPI.SPI_SETDESKWALLPAPER), 
                                                new UINT_PTR(0), 
                                                VerWindows.dest.getPath(), 
                                                new UINT_PTR(SetWallpaper.SPI.SPIF_UPDATEINIFILE | SetWallpaper.SPI.SPIF_SENDWININICHANGE));
                                            
                                        }
//                                        VerWindows.delateWallpaper();
                                        ver.delateWallpaper();

                                        //writeFile(str);
                                        //System.out.println(str + "^ strn
					System.out.println(str);
				}
			} catch (IOException e) {
				System.err.println("Ошибка при получении сообщения.");
				e.printStackTrace();
			}
		}
	}
}

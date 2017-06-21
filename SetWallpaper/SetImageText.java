/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package californication.SetWallpaper;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
/**
 *
 * @author Roman
 */
public class SetImageText {
    public static final String LEFT_TEXT_MODE = "left-text-mode";
public static final String RIGHT_TEXT_MODE = "right-text-mode";
public static final String CENTER_TEXT_MODE = "center-text-mode";
public static final String WIDTH_TEXT_MODE = "width-text-mode";

private BufferedImage bufferedImage;
private String fileName;

public SetImageText(File imageFile) {
try {
bufferedImage = ImageIO.read(imageFile);
fileName = imageFile.getAbsolutePath();
} catch (Exception e) {
e.printStackTrace();
bufferedImage = null;
imageFile = null;
}
}

public SetImageText(String imageFilePath) {
this(new File(imageFilePath));
}

public BufferedImage getAsBufferedImage(){
return bufferedImage;
}

public void saveAs(String fileName){
saveImage(new File(fileName));
this.fileName = fileName;
}


public void save(){
saveImage(new File(fileName));
}


private void saveImage(File file) {
try {
ImageIO.write(bufferedImage, getFileType(file), file);
} catch (IOException e) {
e.printStackTrace();
}
}


private String getFileType(File file) {
String fileName = file.getName();
int idx = fileName.lastIndexOf(".");
if(idx == -1){
throw new RuntimeException("Invalid file name");
}

return fileName.substring(idx+1);
}

public void addTextToImage(String text, 
int topX, int topY, 
int zoneW, float alpha, 
String mode, String font, 
int type, int size, Color color){
Graphics2D g = bufferedImage.createGraphics();
g.setColor(Color.BLACK);
g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
g.setFont(new Font(font, type, size));

final FontMetrics fontMetrics = g.getFontMetrics();
g.dispose();

int lineHeight = fontMetrics.getHeight();

String[] words = text.split(" ");
String line = "";
List lines = new ArrayList();

for (int i = 0; i < words.length; i++) {
if (fontMetrics.stringWidth(line + words[i]) > zoneW) {
lines.add(line);
line = "";
}

line += words[i] + " ";
}

lines.add(line);

for (int i = 0; i < lines.size(); i++) {
addTextLineToImage((String) lines.get(i), 
topX, lineHeight + topY + i * lineHeight, 
zoneW, i == (lines.size() - 1), 
alpha, mode, font, type, size, color);
}
}

private void addTextLineToImage(String text, 
int topX, int topY, 
int zoneW, boolean isLastLine, 
float alpha, String mode, 
String font, int type, 
int size, Color color){
String[] words = text.trim().split(" ");

if (words.length == 0) {
return;
} else if (words.length == 1) {
addTextToImage(text, topX, topY, alpha, font, type, size, color);
} else {
Graphics2D g = bufferedImage.createGraphics();
g.setColor(Color.BLACK);
g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
g.setFont(new Font(font, type, size));

final FontMetrics fontMetrics = g.getFontMetrics();
g.dispose();

if (mode == LEFT_TEXT_MODE) {
addTextToImage(text, topX, topY, alpha, font, type, size, color);
} else if (mode == CENTER_TEXT_MODE) {
topX += (int)(zoneW - fontMetrics.stringWidth(text)) / 2;
addTextToImage(text, topX, topY, alpha, font, type, size, color);
} else if (mode == RIGHT_TEXT_MODE) {
topX += zoneW - fontMetrics.stringWidth(text);
addTextToImage(text, topX, topY, alpha, font, type, size, color);
} else {
int totalWordsWidth = 0;
for (int i = 0; i < words.length; i++) {
totalWordsWidth += fontMetrics.stringWidth(words[i]);
}

int delta = Math.round((zoneW - totalWordsWidth) / (words.length - 1));
int offset = 0;

if (isLastLine) {
delta = Math.min(delta, 10);
}

for (int i = 0; i < words.length; i++) {
addTextToImage(words[i], topX + offset, topY, alpha, font, type, size, color);

offset += fontMetrics.stringWidth(words[i]) + delta;
}
}
}
}

private void addTextToImage(String text, 
int topX, int topY, 
float alpha, 
String font, int type, 
int size, Color color){
Graphics2D g = bufferedImage.createGraphics();
g.setColor(color);
g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // 0.5 = 50% transparency
g.setFont(new Font(font, type, size));

g.drawString(text, topX, topY);
g.dispose();

}
}

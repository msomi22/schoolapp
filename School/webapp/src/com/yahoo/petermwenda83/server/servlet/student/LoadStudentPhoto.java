/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.student;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** 
 * @author peter
 *
 */
public class LoadStudentPhoto {
	
	public static void main (String [] args){
		//String imagePath = "/home/peter/school/logo/photos/999.png";
		
	    //System.out.print("image: " + loadPhoto(imagePath));
		
	}
	
	
	/**
	 * @param path
	 * @return
	 */
	public static BufferedImage loadPhoto(String path){
		int width = 963;    //width of the image
		int height = 640;   //height of the image
		BufferedImage image = null;
		File f = null;
		//read image
		try{
			f = new File(path); //image file path
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			image = ImageIO.read(f);
			return image;
		}catch(IOException e){
			return null;
		}
	}
}

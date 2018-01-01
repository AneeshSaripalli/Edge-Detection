
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageLoad
{
	public static BufferedImage loadImage(String fileName) // Loads the image from the file system based on the fileName location
	{
		try
		{
			return ImageIO.read(ImageLoad.class.getResource(fileName)); // Returns the read Image from the file system
		} catch (Exception e)
		{
			System.err.println("Couldn't find file at location: " + fileName); // Prints an error message if the file is not found
		}

		return null; // Returns a null image
	}

	public static int[][] getPixels(BufferedImage image) // Gets pixels from an image
	{
		int width = image.getWidth(), height = image.getHeight(); // Inits the width and height from the image

		int[][] pixels = new int[height][width]; // Creates a pixel array with dimensions height and width

		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = 0; x < image.getWidth(); x++)
			{
				pixels[y][x] = image.getRGB(x, y); // Sets each value in the array to its RGB value in the image
			}
		}

		return pixels; // Returns the pixel array
	}

	public static void setPixels(BufferedImage image, int[][] pixels) // Sets the pixel value of the image to a given array of pixels
	{
		int h = pixels.length, w = pixels[0].length; // Initializes width and height from the pixel array

		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				//	System.out.println("y: " + y + ", x: " + x);
				image.setRGB(x, y, pixels[y][x]);
			}
		}
	}

	public static void saveFile(BufferedImage img, String fileName, String format) // Saves an image with a fileName and a specific format
	{
		try
		{
			System.out.printf("Saving filename: %s, with format %s\n", fileName, format); // Prints out where the file is being saved to
			System.out.printf("Image width: %d, Image height: %d\n", img.getWidth(), img.getHeight()); // Prints out the dimensions of the image
			File toWrite = new File(new File("").getAbsolutePath() + "\\" + fileName + ".png"); // Creates a file to write the image to

			System.out.println("File location: " + toWrite.getAbsolutePath()); // Prints out the file location

			ImageIO.write(img, format, toWrite); // Writes the image data to file
			System.out.printf("Time taken: %f seconds", (System.currentTimeMillis() - Entry.startTime) / 1000.0); // Prints out the total time taken to process the image

		} catch (Exception e)
		{
			System.err.printf("@%f - Unable to save file. Exiting.\n", Entry.time()); // Prints an error message if it's not possible to save the file
		}
	}

}

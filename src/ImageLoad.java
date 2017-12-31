
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageLoad
{
	public static BufferedImage loadImage(String fileName)
	{
		try
		{
			return ImageIO.read(ImageLoad.class.getResource(fileName));
		} catch (Exception e)
		{
			System.err.println("Couldn't find file at location: " + fileName);
		}

		return null;
	}

	public static int[][] getPixels(BufferedImage image)
	{
		int width = image.getWidth(), height = image.getHeight();

		int[][] pixels = new int[height][width];

		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = 0; x < image.getWidth(); x++)
			{
				pixels[y][x] = image.getRGB(x, y);
			}
		}

		return pixels;
	}

	public static void setPixels(BufferedImage image, int[][] pixels)
	{
		int h = pixels.length, w = pixels[0].length;

		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				//	System.out.println("y: " + y + ", x: " + x);
				image.setRGB(x, y, pixels[y][x]);
			}
		}
	}

	public static void saveFile(BufferedImage img, String fileName, String format)
	{
		try
		{
			System.out.printf("Saving filename: %s, with format %s\n", fileName, format);
			System.out.printf("Image width: %d, Image height: %d\n", img.getWidth(), img.getHeight());
			File toWrite = new File(new File("").getAbsolutePath() + "\\" + fileName + ".png");

			System.out.println("File location: " + toWrite.getAbsolutePath());

			ImageIO.write(img, format, toWrite);
			System.out.printf("Time taken: %f seconds", (System.currentTimeMillis() - Entry.startTime) / 1000.0);

		} catch (Exception e)
		{
			System.err.println("unable to save file");
		}
	}

}

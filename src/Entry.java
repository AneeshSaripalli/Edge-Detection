import java.awt.Color;
import java.awt.image.BufferedImage;

public class Entry
{
	protected int[][] pixels; // Creates a matrix of pixels

	protected BufferedImage img; // Loads the images for processing from the file system
	protected int imgW, imgH; // Variables for the image width and height

	protected String fileName;

	public static long startTime; // Creates a variable to time the total process

	public Entry(String fileName, String... operations)
	{
		startTime = System.currentTimeMillis(); // Initializes start time

		BufferedImage img = ImageLoad.loadImage(fileName); // Loads the image from the fileLoad command

		if (img == null)
		{
			System.out.printf("@%f - Image not found. Exiting.\n", time());
			return;
		}

		this.img = img;

		this.fileName = fileName;

		pixels = ImageLoad.getPixels(img); // Initializes pixels to the pixel array from the image

		imgW = img.getWidth(); // Initializes image width
		imgH = img.getHeight(); // Initializes image height

		if (!operations[0].equals("Gray"))
		{
			pixels = Filter.grayScale(pixels, imgW, imgH);
		}

		System.out.printf("Image found and loaded - width: %d, height: %d\n", imgW, imgH);

		for (int i = 1; i < operations.length; i++) // loops through all operators in the command line input
		{
			String operation = operations[i];

			System.out.printf("@%f - ", time());

			switch (operation) // Deals with various command line strings
			{
			case "Edge":
				System.out.println("Edge Processing");
				int threshold = 0;

				try
				{
					threshold = Integer.parseInt(operations[i + 1]);
					i++;
				} catch (Exception e)
				{
					System.err.println("A threshold must be entered after \"Edge\" operator.");
					System.exit(0);
				}

				pixels = Filter.applyEdgeDetection(pixels, threshold, imgW, imgH);
				break;
			case "Gray":
				System.out.println("Graying Image");
				pixels = Filter.grayScale(pixels, imgW, imgH);
				break;
			case "Blur":
				System.out.println("Blurring Image");
				pixels = Filter.applyGaussianBlur(pixels);
				break;
			case "Test":
				System.out.println("Test Matrix Image");
				pixels = Filter.applyTestBlur(pixels);
				break;
			case "Sharpen":
				System.out.println("Sharpen Image");
				pixels = Filter.applyGeneralBlur(pixels, MatrixOpt.sharpen);
				break;
			default:
			}
		}

		if (!operations[operations.length - 2].equals("Edge")) // Edge remaps the pixel values to true RGB values, so it needs to be remapped in the case its not called
		{
			reMap();
		}

		apply(); // Applies the new pixels to the original image
		save(); // Saves the image to the file system
	}

	public static double time() // Function to get time from starttime
	{
		return (System.currentTimeMillis() - startTime) / 1000.0;
	}

	public int[][] getGrayValues(int[][] pixels, int imgW, int imgH)
	{
		int[][] newArray = new int[imgH][imgW];

		for (int y = 0; y < imgH; y++)
		{
			for (int x = 0; x < imgW; x++)
			{
				newArray[y][x] &= 0xff;
			}
		}

		return newArray;
	}

	public void reMap() // Gets the RGB storage value based on gray values
	{
		for (int y = 0; y < imgH; y++)
		{
			for (int x = 0; x < imgW; x++)
			{
				int val = Math.min(Math.max(pixels[y][x], 0), 255);
				//System.out.println(val);
				pixels[y][x] = new Color(val, val, val).getRGB();
			}
		}
	}

	public void apply() // Updates buffered image to new pixel values
	{
		ImageLoad.setPixels(img, pixels);
	}

	public void save() // Stores file with adjusted name
	{
		String[] strings = fileName.split("\\.");
		String prefix = strings[0], extension = strings[1];
		ImageLoad.saveFile(img, prefix + "_adj", extension);
	}

	public static void main(String[] args)
	{
		new Entry(args[0], args);
	}
}

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Entry
{
	protected int[][] pixels;
	protected BufferedImage img;
	protected int imgW, imgH;
	protected String fileName;

	public static long startTime;

	public Entry(String fileName, String... operations)
	{
		startTime = System.currentTimeMillis();

		BufferedImage img = ImageLoad.loadImage(fileName);
		this.img = img;

		this.fileName = fileName;

		pixels = ImageLoad.getPixels(img);
		
		imgW = img.getWidth();
		imgH = img.getHeight();

		if(!operations[0].equals("Gray"))
		{
			pixels = Filter.grayScale(pixels, imgW, imgH);
		}

		
		System.out.printf("Image found and loaded - width: %d, height: %d\n", imgW, imgH);

		for (int i = 1; i < operations.length; i++)
		{
			String operation = operations[i];

			System.out.printf("@%f - ", time());

			switch (operation)
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

		if (!operations[operations.length - 2].equals("Edge"))
		{
			reMap();
		}

		apply();
		save();
	}

	public double time()
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

	public void reMap()
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

	public void apply()
	{
		ImageLoad.setPixels(img, pixels);
	}

	public void save()
	{
		String[] strings = fileName.split("\\.");
		String prefix = strings[0], extension = strings[1];
		ImageLoad.saveFile(img, prefix + "converted", extension);
	}

	public static void main(String[] args)
	{
		new Entry(args[0], args);
	}
}

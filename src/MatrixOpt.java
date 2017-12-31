
public class MatrixOpt
{
	public static MatrixOpt gaussian = new MatrixOpt(false, true, 1, 3, 1, 1, 9, 1, 1, 3, 1);
	public static MatrixOpt sharpen = new MatrixOpt(false, false, 0, -1, 0, -1, 5, -1, 0, -1, 0);
	public static MatrixOpt test = new MatrixOpt(false, true, -4, 1, 11, 0, 2, 4, 7, -2, 6);
	public static MatrixOpt cX = new MatrixOpt(false, false, -1, 0, 1, -2, 0, 2, -1, 0, 1);
	public static MatrixOpt cY = new MatrixOpt(false, false, -1, -2, -1, 0, 0, 0, 1, 2, 1);

	private final boolean debugmode, average;

	private int[][] matrix;
	private final int matrixWidth = 3, matrixHeight = 3;

	public MatrixOpt(boolean debug, boolean average, int... numbers)
	{
		debugmode = debug;
		this.average = average;

		matrix = new int[matrixHeight][matrixWidth];

		for (int y = 0; y < matrixHeight; y++)
		{
			for (int x = 0; x < matrixWidth; x++)
			{
				matrix[y][x] = numbers[y * matrixWidth + x];
			}
		}
	}

	public String toString()
	{
		String total = "";
		for (int[] array : matrix)
		{
			total += "{";
			for (int num : array)
			{
				total += num + ",";
			}
			total += "}\n";
		}

		return total;
	}

	public int[][] applyMatrix(int[][] pixels)
	{
		int h = pixels.length, w = pixels[0].length;

		// Creates an array to return
		int[][] result = new int[h][w];

		// Loops through all pixels in y direction
		for (int centerY = 0; centerY < h; centerY++)
		{
			// Loops through all pixels in x direction
			for (int centerX = 0; centerX < w; centerX++)
			{
				if (debugmode)
				{
					System.out.println(String.format("\nStart Postion:  (%d, %d)", centerX, centerY));
				}

				// Sets a center point
				int matrixCenterX = 1, matrixCenterY = 1;

				// Creates an average sum
				int weightedSum = 0;

				int matrixSum = 0;

				// Loops from -1 to 1 position in y direction
				for (int yOffset = -1; yOffset <= 1; yOffset++)
				{
					// Loops from -1 to 1 position in x direction
					for (int xOffset = -1; xOffset <= 1; xOffset++)
					{
						int matrixVal = matrix[matrixCenterY + yOffset][matrixCenterX + xOffset];

						try
						{
							int pixelVal = pixels[centerY + yOffset][centerX + xOffset];
							weightedSum += pixelVal * matrixVal;
							matrixSum += matrixVal;
						} catch (Exception e)
						{

						}
					}
				}

				if (debugmode)
					System.out.printf(String.format("Delta grayscale value: %d\n\n", pixels[centerY][centerX] - weightedSum));

				result[centerY][centerX] = (average) ? weightedSum / matrixSum : weightedSum;
			}
		}

		return result;
	}

	public int[][] applyMatrixBeta(int[][] pixels)
	{
		int h = pixels.length, w = pixels[0].length;

		// Creates an array to return
		int[][] result = new int[h][w];

		// Loops through all pixels in y direction
		for (int centerY = 0; centerY < h; centerY++)
		{
			// Loops through all pixels in x direction
			for (int centerX = 0; centerX < w; centerX++)
			{
				if (debugmode)
				{
					System.out.println(String.format("\nStart Postion:  (%d, %d)", centerX, centerY));
				}

				result[centerY][centerX] = convolution(pixels, centerX, centerY);
			}
		}

		return result;
	}

	public int convolution(int[][] pixels, int centerX, int centerY)
	{

		// Sets a center point
		int matrixCenterX = 1, matrixCenterY = 1;

		// Creates an average sum
		int weightedSum = 0;

		int matrixSum = 0;

		// Loops from -1 to 1 position in y direction
		for (int yOffset = -1; yOffset <= 1; yOffset++)
		{
			// Loops from -1 to 1 position in x direction

			for (int xOffset = -1; xOffset <= 1; xOffset++)
			{
				int matrixVal = matrix[matrixCenterY + yOffset][matrixCenterX + xOffset];

				try
				{
					int pixelVal = pixels[centerY + yOffset][centerX + xOffset];
					matrixSum += matrixVal;
					weightedSum += pixelVal * matrixVal;
				} catch (Exception e)
				{
					// Value of 0
					matrixSum -= matrixVal;
				}
			}
		}

		return (average) ? weightedSum / matrixSum : weightedSum;
	}
}

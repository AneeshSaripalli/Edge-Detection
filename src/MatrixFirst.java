
/*
 * Antiquated Matrix multiplication algorithm
 */

public class MatrixFirst
{
	public static MatrixFirst gaussian = new MatrixFirst(false, true, 3, 3, 1, 2, 1, 1, 4, 1, 1, 2, 1);
	public static MatrixFirst blend = new MatrixFirst(false, true, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1);
	public static MatrixFirst test = new MatrixFirst(false, true, 3, 3, -4, 1, 11, 0, 2, 4, 7, -2, 6);
	public static MatrixFirst cX = new MatrixFirst(false, false, 3, 3, -1, 0, 1, -2, 0, 2, -1, 0, 1);
	public static MatrixFirst cY = new MatrixFirst(false, false, 3, 3, -1, -2, -1, 0, 0, 0, 1, 2, 1);

	private final boolean debugmode, average;

	private double[][] matrix;
	private int matrixWidth, matrixHeight;

	public MatrixFirst(boolean debug, boolean average, int width, int height, double... numbers)
	{
		debugmode = debug;
		this.average = average;

		this.matrixWidth = width;
		this.matrixHeight = height;

		matrix = new double[height][width];

		for (int y = 0; y < matrix.length; y++)
		{
			for (int x = 0; x < matrix[0].length; x++)
			{
				matrix[y][x] = numbers[y * width + x];
			}
		}
	}

	public String toString()
	{
		String total = "";
		for (double[] array : matrix)
		{
			total += "{";
			for (double num : array)
			{
				total += num + ",";
			}
			total += "}\n";
		}

		return total;
	}

	public int matrixSum()
	{
		int sum = 0;
		for (double[] array : matrix)
		{
			for (double num : array)
			{
				sum += num;
			}
		}

		return sum;
	}

	public int[][] applyMatrix(int[][] pixels)
	{
		int h = pixels.length, w = pixels[0].length;

		int[][] result = new int[h][w];

		for (int centerY = 0; centerY < h; centerY++)
		{
			for (int centerX = 0; centerX < w; centerX++)
			{
				if (debugmode)
				{
					System.out.println(String.format("\nStart Postion:  (%d, %d)", centerX, centerY));
				}
				int[][] subMatrix = generateMatrix(centerX, centerY, matrixWidth, matrixHeight, pixels);

				int sum = multiplyOverlay(matrix, subMatrix, centerX - matrixWidth / 2, centerY - matrixHeight / 2);

				if (debugmode)
					System.out.printf(String.format("Delta grayscale value: %d\n\n", pixels[centerY][centerX] - sum));

				result[centerY][centerX] = sum;
			}
		}

		return result;
	}

	// Assumes equal dimensions
	public int multiplyOverlay(double[][] matrix, int[][] subMatrix, int startX, int startY)
	{
		int sum = 0;
		int matrixSum = 0;
		for (int y = 0; y < matrixHeight; y++)
		{
			for (int x = 0; x < matrixWidth; x++)
			{
				int pixelVal = subMatrix[y][x];
				double matrixVal = matrix[y][x];
				if (debugmode)
				{
					System.out.println(String.format("Pixel value: (%d, %d)=%d", x + startX, y + startY, pixelVal));
				}
				if (pixelVal != 420)
				{
					if (debugmode)
						System.out.printf("Doing PixelValue: %d * MatrixValue:%f = Added Value: %f\n\n", pixelVal, matrixVal, pixelVal * matrixVal);
					sum += matrixVal * pixelVal;
					matrixSum += matrixVal;
				}
			}
		}

		int returnValue = sum;

		if (average)
		{
			int copy = returnValue;

			try
			{
				returnValue = (sum / matrixSum);

				if (debugmode)
					System.out.println("Average of total has changed to: " + returnValue + "\n");
			} catch (Exception e)
			{
				returnValue = subMatrix[matrixHeight / 2][matrixWidth / 2];
				if (debugmode)
					System.out.println("Average of total is default: " + returnValue);
			}

			if (debugmode)
				System.out.println("Used matrixsum: " + matrixSum);
		}

		if (debugmode)
			System.out.println("Return value: " + returnValue);

		return returnValue;
	}

	public int[][] generateMatrix(int centerX, int centerY, int width, int height, int[][] parent)
	{
		int[][] result = new int[height][width];
		int yStart = centerY - height / 2, yEnd = centerY + height / 2;
		int xStart = centerX - width / 2, xEnd = centerX + width / 2;
		for (int y = yStart; y <= yEnd; y++)
		{
			for (int x = xStart; x <= xEnd; x++)
			{
				try
				{
					result[y - yStart][x - xStart] = parent[y][x];
				} catch (Exception e)
				{
					result[y - yStart][x - xStart] = 420;
				}
			}
		}

		return result;
	}

	public double[][] getMatrix()
	{
		return matrix;
	}
}

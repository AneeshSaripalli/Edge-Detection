import java.awt.Color;

public class Filter
{

	public static int[][] grayScale(int[][] pixels, int imgW, int imgH)
	{
		for (int y = 0; y < imgH; y++)
		{
			for (int x = 0; x < imgW; x++)
			{
				int color = pixels[y][x];

				Color c = new Color(color);

				int r = c.getRed(), g = c.getGreen(), b = c.getBlue();

				int gray = (int) (r * .2126 + g * .7152 + b * .07222);

				pixels[y][x] = gray;
			}
		}

		return pixels;
	}

	public static int[][] applyTestBlur(int[][] pixels)
	{
		return MatrixOpt.test.applyMatrixBeta(pixels);
	}

	public static int[][] applyGaussianBlur(int[][] pixels)
	{
		return MatrixOpt.gaussian.applyMatrixBeta(pixels);
	}

	public static int[][] applyGeneralBlur(int[][] pixels, MatrixOpt filter)
	{
		return filter.applyMatrixBeta(pixels);
	}

	public static int[][] applyEdgeDetection(int[][] pixels, final int threshold, int imgW, int imgH)
	{
		MatrixOpt xFilter = MatrixOpt.cX, yFilter = MatrixOpt.cY;

		int[][] cx = xFilter.applyMatrixBeta(pixels);
		int[][] cy = yFilter.applyMatrixBeta(pixels);

		int[][] combined = new int[imgH][imgW];

		for (int y = 0; y < imgH; y++)
		{
			for (int x = 0; x < imgW; x++)
			{
				int hyp = (int) Math.hypot(cx[y][x], cy[y][x]);

				//System.out.println(hyp);

				//		int val = (hyp >= threshold) ? 0 : 255;

				//				combined[y][x] = new Color(val, val, val).getRGB();

				if (hyp >= threshold)
				{

					double angle = Math.atan2(cy[y][x], cx[y][x]) * 180 / Math.PI;

					int val = -(int) ((1 << 16) * (angle / 360.0) + (1 << 24));

					combined[y][x] = val;
				}
				else
				{
					combined[y][x] = new Color(255, 255, 255).getRGB();
				}
			}
		}

		return combined;
	}
}

import java.util.Random;


public class Globals {
	public static int R[][] = new int [100][100];
	public static void fillMatrix(int M, double f, double e)
	{
		e = e + f;
		Random generator = new Random();
		for(int i =0;i<M;i++)
		{
			for(int j=i+1;j<M;j++)
			{
				double rand = generator.nextDouble();				
				if(rand >= 0 && rand <= f)
				{
					R[i][j] = 1;
				}
				else if(rand > f && rand <= e)
				{
					R[i][j] = -1;
				}
				else
				{
					R[i][j] = 0;
				}
			}
		}		
	}
}
